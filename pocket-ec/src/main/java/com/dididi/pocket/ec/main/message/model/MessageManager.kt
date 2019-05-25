package com.dididi.pocket.ec.main.message.model

import android.text.TextUtils
import com.dididi.pocket.core.im.IIMCallback
import com.dididi.pocket.core.util.LogUtil
import com.dididi.pocket.core.util.MessageUtil
import com.tencent.imsdk.TIMConversation
import com.tencent.imsdk.TIMConversationType
import com.tencent.imsdk.ext.message.TIMConversationExt
import com.tencent.imsdk.ext.message.TIMManagerExt
import java.util.ArrayList


/**
 * @author dididi(叶超)
 * @email yc512yc@163.com
 * @since 25/05/2019
 * @describe
 */

class MessageManager private constructor() {

    companion object {
        @Volatile
        private var instance: MessageManager? = null

        fun getInstance() = instance ?: synchronized(this) {
            instance ?: MessageManager().apply {
                instance = this
            }
        }
    }

    /**
     * 加载会话信息
     *
     * @param callBack
     */
    fun loadConversation(callBack: IIMCallback?) {
        val conversationList = TIMManagerExt.getInstance().conversationList
        val infos = ArrayList<MessageInfo>()
        for (i in 0 until conversationList.size) {
            val conversation = conversationList[i]
            val messageInfo = TIMConversation2MessageInfo(conversation)
            infos.add(messageInfo!!)
        }
        callBack?.onSuccess(infos)
    }

    /**
     * TIMConversation转换为MessageInfo
     *
     * @param conversation
     * @return
     */
    private fun TIMConversation2MessageInfo(conversation: TIMConversation?): MessageInfo? {
        if (conversation != null) {
            if (TextUtils.isEmpty(conversation.peer)) { // 没有peer的会话，点击进去会有异常，这里做拦截
                return null
            }
        }
        val ext = TIMConversationExt(conversation!!)
        val message = ext.lastMsg ?: return null
        val info = MessageInfo()
        val type = conversation.type
//        if (type == TIMConversationType.System) {
//            if (message.elementCount > 0) {
//                val ele = message.getElement(0)
//                val eleType = ele.type
//                if (eleType == TIMElemType.GroupSystem) {
//                    val groupSysEle = ele as TIMGroupSystemElem
//                    groupSystMsgHandle(groupSysEle)
//                }
//            }
//            return null
//        }

        val isGroup = type == TIMConversationType.Group
        info.lastMessageTime = message.timestamp() * 1000
        val msg = MessageUtil.TIMMessage2Message(message, isGroup)
        info.lastMessage = msg
        if (isGroup)
            info.title = conversation.groupName
        else
            info.title = conversation.peer
        info.peer = conversation.peer
        info.isGroup = conversation.type == TIMConversationType.Group
        if (ext.unreadMessageNum > 0)
            info.unRead = ext.unreadMessageNum.toInt()
        LogUtil.d("chatMessage", "peer:${info.peer} | title:${info.title} | lastMessage:${info.lastMessage} | dataUri:${info.lastMessage?.dataUri} | dataPath:${info.lastMessage?.dataPath}")
        return info
    }
}