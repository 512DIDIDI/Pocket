package com.dididi.pocket.ec.main.message.chat.model

import com.dididi.pocket.core.entity.Message
import com.dididi.pocket.core.fakedata.FakeUser
import com.dididi.pocket.core.im.IIMCallback
import com.dididi.pocket.core.util.LogUtil
import com.dididi.pocket.core.util.MessageUtil
import com.tencent.imsdk.*
import com.tencent.imsdk.ext.message.TIMConversationExt
import com.tencent.imsdk.ext.message.TIMMessageExt
import kotlin.collections.HashMap


/**
 * @author dididi(叶超)
 * @email yc512yc@163.com
 * @since 23/05/2019
 * @describe 单独聊天管理类
 */

class C2CChatManager private constructor() : TIMMessageListener {

    /**
     * 当前聊天信息
     */
    private var mCurrentConversation: TIMConversation? = null
    private var mCurrentConversationExt: TIMConversationExt? = null
    private var mCurrentChatInfo: C2CChatInfo? = null
    private val mChatMap = HashMap<String, C2CChatInfo>(16)

    /**
     * 是否有更多消息
     */
    private var isMore: Boolean = true
    /**
     * 是否正在加载
     */
    private var isLoading: Boolean = false

    companion object {

        const val TAG = "C2CChatManager"
        const val MESSAGE_PAGE_COUNT = 10

        @Volatile
        private var instance: C2CChatManager? = null

        fun getInstance() = instance
                ?: synchronized(this) {
                    instance
                            ?: C2CChatManager().apply {
                                instance = this
                            }
                }
    }

    fun getUserByName(name: String) = FakeUser.getUserByName(name)

    /**
     * 初始化
     */
    fun init() {
        destroyC2CChat()
        TIMManager.getInstance().addMessageListener(instance)
    }

    /**
     * 设置当前聊天信息
     * @param info 聊天信息
     */
    fun setCurrentChatInfo(info: C2CChatInfo) {
        mCurrentChatInfo = info
        mCurrentConversation = TIMManager.getInstance().getConversation(info.type, info.peer)
        mCurrentConversationExt = TIMConversationExt(mCurrentConversation!!)
    }

    /**
     * 添加聊天信息与用户的对应
     */
    fun addChatInfo(info: C2CChatInfo) {
        mChatMap[info.peer!!] = info
    }

    /**
     * 根据用户获取聊天信息
     */
    fun getChatInfo(peer: String) = mChatMap[peer] ?: C2CChatInfo().apply {
        this.peer = peer
        chatName = peer
        mChatMap[peer] = this
    }

    /**
     * 加载消息
     * @param lastMessage 最近的一条消息
     * @param callback 回调
     */
    fun loadChatMessages(lastMessage: Message?, callback: IIMCallback) {
        if (isLoading || mCurrentConversationExt == null) return
        isLoading = true
        if (!isMore) {
            //callback.onSuccess(null)
            isLoading = false
            return
        }
        val lastTimMessage: TIMMessage? = lastMessage?.timMessage
        val unread = mCurrentConversationExt!!.unreadMessageNum
        mCurrentConversationExt?.getMessage(if (unread > MESSAGE_PAGE_COUNT) {
            unread.toInt()
        } else {
            MESSAGE_PAGE_COUNT
        }, lastTimMessage, object : TIMValueCallBack<List<TIMMessage>> {
            override fun onSuccess(p0: List<TIMMessage>?) {
                isLoading = false
                if (unread > 0) {
                    mCurrentConversationExt!!.setReadMessage(null, object : TIMCallBack {
                        override fun onSuccess() {
                            LogUtil.d(TAG, "loadChatMessages() success")
                        }

                        override fun onError(p0: Int, p1: String?) {
                            LogUtil.d(TAG, "loadChatMessages() failed code:$p0 | errMsg:$p1")
                        }
                    })
                }
                if (p0!!.size < MESSAGE_PAGE_COUNT) {
                    isMore = false
                }
                val timMessage = ArrayList<TIMMessage>(p0)
                timMessage.reverse()
                val messages = MessageUtil.TIMMessages2Messages(timMessage, false)
                callback.onSuccess(messages)
                messages?.forEach {
                    LogUtil.d("chatMessage", "load content:${it.extra} | targetUser:${it.targetUser.name} | fromUser:${it.fromUser.name}")
                    if (it.status == Message.MSG_STATUS_SENDING) {
                        sendMessage(it, true, null)
                    }
                }
            }

            override fun onError(p0: Int, p1: String?) {
                isLoading = false
                callback.onError(TAG, p0, p1)
            }
        })
    }

    /**
     * 发送消息
     * @param message 需要发送的消息
     * @param reSend 是否需要重发
     * @param callback 回调
     */
    fun sendMessage(message: Message, reSend: Boolean, callback: IIMCallback?) {
        if (mCurrentChatInfo == null || mCurrentConversation == null)
            return
        message.apply {
            isSelf = true
            isRead = true
            targetUser = getUserByName(mCurrentConversation!!.peer)
        }
        //如果是提示信息以下的数据，则加入到消息List中
        //发送消息时本地先显示，再进行网络发送
        if (message.type < Message.MSG_TYPE_TIPS) {
            message.status = Message.MSG_STATUS_SENDING
        }
        //开启子线程发送消息
        Thread(Runnable {
            val currentDate = System.currentTimeMillis()
            //通过TIMConversation.sendMessage()发送消息
            mCurrentConversation!!.sendMessage(message.timMessage, object : TIMValueCallBack<TIMMessage> {
                override fun onSuccess(p0: TIMMessage?) {
                    message.apply {
                        status = Message.MSG_STATUS_SEND_SUCCESS
                        msgId = timMessage.msgId
                        date = currentDate
                    }
                    callback?.onSuccess(true)
                }

                override fun onError(p0: Int, p1: String?) {
                    LogUtil.d(TAG, "sendC2CMessageFailed{ code:$p0 | desc:$p1")
                    message.status = Message.MSG_STATUS_SEND_FAIL
                    callback?.onError(TAG, p0, p1)
                }
            })
        }).start()
    }

    /**
     * 本地删除消息
     * @param position 消息列表position
     * @param message 需要删除的消息
     */
    fun deleteMessage(position: Int, message: Message) {
        val ext = TIMMessageExt(message.timMessage)
        if (ext.remove()) {
        }
    }

    /**
     * 接收消息
     * @param p0 消息列表
     */
    override fun onNewMessages(p0: MutableList<TIMMessage>?): Boolean {
        p0?.forEach {
            val conversation = it.conversation
            val type = conversation.type
            if (type == TIMConversationType.C2C) {
                val elem = it.getElement(0)
                when (elem.type) {
                    // 用户资料修改通知，不需要在聊天界面展示，可以通过 TIMUserConfig 中的 setFriendshipListener 处理
                    TIMElemType.ProfileTips -> {
                        LogUtil.d(TAG, "onNewMessage() elemType is ProfileTips")
                        return false
                    }
                    // 关系链变更通知，不需要在聊天界面展示，可以通过 TIMUserConfig 中的 setFriendshipListener 处理
                    TIMElemType.SNSTips -> {
                        LogUtil.d(TAG, "onNewMessage() elemType is SNSTips")
                        return false
                    }
                    else -> {
                        LogUtil.d(TAG, "onNewMessage() message:$it")
                        onReceiveMessage(conversation, it)
                    }
                }
            }
        }
        return false
    }

    /**
     * 接收消息
     * @param conversation 消息管理类
     * @param timMessage 消息内容
     */
    private fun onReceiveMessage(conversation: TIMConversation, timMessage: TIMMessage) {
        if (conversation.peer == null) {
            return
        }
        executeMessage(conversation, timMessage)
    }

    private fun executeMessage(conversation: TIMConversation, timMessage: TIMMessage) {
        val message = MessageUtil.TIMMessage2Message(timMessage, false)
        message?.isRead = true
        mCurrentConversationExt?.setReadMessage(null, object : TIMCallBack {
            override fun onSuccess() {
                LogUtil.d(TAG, "executeMessage setReadMessage is Success")
            }

            override fun onError(p0: Int, p1: String?) {
                LogUtil.d(TAG, "executeMessage setReadMessage is Failed{ code:$p0 | desc:$p1 }")
            }
        })
    }

    /**
     * 销毁资源
     */
    fun destroyC2CChat() {
        mCurrentChatInfo = null
        mCurrentConversation = null
        mCurrentConversationExt = null
        isMore = true
    }
}