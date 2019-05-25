package com.dididi.pocket.ec.main.message.chat

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.KeyEvent
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import android.widget.Toast
import com.dididi.pocket.core.BackgroundTasks
import com.dididi.pocket.core.delegates.PocketDelegate
import com.dididi.pocket.core.entity.Message
import com.dididi.pocket.core.fakedata.FakeUser
import com.dididi.pocket.core.im.IIMCallback
import com.dididi.pocket.core.im.IIMEventListener
import com.dididi.pocket.core.ui.animation.PocketAnimation
import com.dididi.pocket.core.ui.item.MoreButtonItem
import com.dididi.pocket.core.util.MessageUtil
import com.dididi.pocket.ec.R
import com.dididi.pocket.ec.main.message.chat.adapter.ChatAdapter
import com.dididi.pocket.ec.main.message.chat.adapter.MorePagerAdapter
import com.dididi.pocket.ec.main.message.chat.model.C2CChatManager
import com.gyf.immersionbar.ktx.immersionBar
import com.tencent.imsdk.TIMMessage
import kotlinx.android.synthetic.main.delegate_msg_chat.*
import me.yokeyword.fragmentation.ISupportFragment
import java.io.FileNotFoundException


/**
 * @author dididi
 * @describe 聊天页面
 * @since 07/09/2018
 */


/**
 * 构造函数私有化
 * 防止外部实例化ChatDelegate而导致没有传入message bundle出现错误
 * 只能通过ChatDelegate的getStartChat()方法获取ChatDelegate
 */
class ChatDelegate : PocketDelegate(), TextView.OnEditorActionListener, IIMEventListener {

    private var mMessageList = ArrayList<Message>()
    private var mAdapter: ChatAdapter? = null
    private var getName: String? = null
    private val mViewList = ArrayList<View>()
    private var morePagerView: View? = null
    private var mRecyclerView: RecyclerView? = null


    override fun setLayout(): Any {
        return R.layout.delegate_msg_chat
    }

    override fun onBindChildView(savedInstanceState: Bundle?, rootView: View?) {
        //获取从bundle传入的数据
        getName = arguments!!.getString("peer")!!
        mAdapter = ChatAdapter(R.layout.item_message_chat, mMessageList)
    }

    override fun onBindView(savedInstanceState: Bundle?, rootView: View?) {
        //设置chat页面标题
        delegate_msg_chat_name?.text = getName
        //输入框发送消息
        delegate_msg_chat_edit?.setOnEditorActionListener(this)
        //更多页面viewpager 添加view到list中
        val viewGroup: ViewGroup? = null
        morePagerView = layoutInflater.inflate(R.layout.item_msg_chat_more, viewGroup, false)
        mViewList.add(morePagerView!!)
        val moreAdapter = MorePagerAdapter(context!!, mViewList)
        delegate_msg_chat_more_page?.adapter = moreAdapter
        //点击事件处理
        onClickEvent()
        mRecyclerView = rootView!!.findViewById(R.id.delegate_msg_chat_recyclerView)
        mRecyclerView!!.layoutManager = LinearLayoutManager(context)
        mRecyclerView!!.adapter = mAdapter

        C2CChatManager.getInstance().loadChatMessages(null, object : IIMCallback {
            override fun onSuccess(data: Any?) {
                BackgroundTasks.instance?.runOnUiThread(Runnable {
                    mAdapter?.updateMessage(data as List<Message>)
                })
                Toast.makeText(context, "加载成功", Toast.LENGTH_SHORT).show()
            }

            override fun onError(module: String, errCode: Int, errMsg: String?) {
                Toast.makeText(context, "加载失败", Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun getTitleBarId() = R.id.delegate_msg_chat_toolbar

    override fun initImmersionBar() {
        immersionBar {
            flymeOSStatusBarFontColor(R.color.textColorWhite)
            keyboardEnable(true)
        }
    }

    private fun onClickEvent() {
        delegate_msg_chat_more?.setOnClickListener {
            showMorePager()
        }
        delegate_msg_chat_back_btn?.setOnClickListener {
            supportDelegate.pop()
        }
        delegate_msg_chat_personal?.setOnClickListener {
            Toast.makeText(context, "点击个人", Toast.LENGTH_SHORT).show()
        }
        delegate_msg_chat_voice?.setOnClickListener {
            Toast.makeText(context, "点击语音", Toast.LENGTH_SHORT).show()
        }
        //上拉页面的按钮
        //打开相机
        val moreCamera = morePagerView?.findViewById<MoreButtonItem>(R.id.item_msg_chat_more_camera)
        moreCamera?.setOnClickListener {
            applyCameraPermission()
        }
        //打开相册
        val moreOpenAlbum = morePagerView?.findViewById<MoreButtonItem>(R.id.item_msg_chat_more_album)
        moreOpenAlbum?.setOnClickListener {
            applyOpenAlbumPermission()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            OPEN_CAMERA -> {
                if (resultCode == ISupportFragment.RESULT_OK) {
                    insertBitmapToList(cameraPhotoUri)
                }
            }
            OPEN_ALBUM -> {
                if (resultCode == ISupportFragment.RESULT_OK) {
                    insertBitmapToList(getAlbumPhotoUri(data!!))
                }
            }
        }
    }

    private fun insertBitmapToList(uri: Uri) {
        try {
            //加载图片到消息列表中
            val message = MessageUtil.buildImageMessage(uri, true, true)
            //todo:这里有bug
            mMessageList.add(message!!)
            mAdapter?.notifyItemInserted(mMessageList.size)
            delegate_msg_chat_recyclerView?.scrollToPosition(mMessageList.size - 1)
            isMoreVisible = true
            showMorePager()
            C2CChatManager.getInstance().sendMessage(message, false, object : IIMCallback {
                override fun onSuccess(data: Any?) {
                    if (data is Boolean) {
                        Toast.makeText(context!!, "发送成功", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onError(module: String, errCode: Int, errMsg: String?) {
                }
            })
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        }
    }

    /**
     * 显示更多页面拓展栏的逻辑
     */
    private fun showMorePager() {
        //如果隐藏则显示，如果显示则隐藏
        delegate_msg_chat_more_page?.visibility =
                if (isMoreVisible) {
                    PocketAnimation
                            .setRotateAnimation(delegate_msg_chat_more,
                                    PocketAnimation.ROTATION_X, 180F, 0F,
                                    0, 50L, false)
                    isMoreVisible = false
                    View.GONE
                } else {
                    PocketAnimation
                            .setRotateAnimation(delegate_msg_chat_more,
                                    PocketAnimation.ROTATION_X, 0F, 180F,
                                    0, 50L, false)
                    isMoreVisible = true
                    View.VISIBLE
                }
    }


    override fun setSwipeBackEnable(enable: Boolean) {
        super.setSwipeBackEnable(true)
    }

    override fun onEditorAction(v: TextView, actionId: Int, event: KeyEvent?): Boolean {
        if (actionId == EditorInfo.IME_ACTION_SEND) {
            if (delegate_msg_chat_edit?.text == null) {
                Toast.makeText(context, "发送消息不能为空", Toast.LENGTH_SHORT).show()
            } else {
                //新建文本消息
                val message = MessageUtil.buildTextMessage(delegate_msg_chat_edit.text.toString())
                mMessageList.add(message)
                //插入数据源
                mAdapter?.notifyItemInserted(mMessageList.size)
                delegate_msg_chat_recyclerView?.scrollToPosition(mMessageList.size - 1)
                C2CChatManager.getInstance().sendMessage(message, false, object : IIMCallback {
                    override fun onSuccess(data: Any?) {
                        if (data as Boolean)
                            Toast.makeText(context, "发送成功", Toast.LENGTH_SHORT).show()
                    }

                    override fun onError(module: String, errCode: Int, errMsg: String?) {
                        Toast.makeText(context, "发送失败", Toast.LENGTH_SHORT).show()
                    }
                })
            }
            //发送完成清空输入框
            delegate_msg_chat_edit?.setText("")
        }
        return false
    }


    companion object {

        private var isMoreVisible: Boolean = false

        /**
         * 需要传入ChatDelegate的数据
         * 外界仅能通过此方法获取ChatDelegate实例
         *
         * @param targetUserName 目标聊天对象
         * @return 返回一个包装后的chatDelegate
         */
        fun getStartChat(targetUserName: String): ChatDelegate {
            val chatDelegate = ChatDelegate()
            val bundle = Bundle()
            bundle.putString("peer", targetUserName)
            chatDelegate.arguments = bundle
            return chatDelegate
        }
    }

    override fun onNewMessage(messages: List<TIMMessage>) {
        val msgs = MessageUtil.TIMMessages2Messages(messages, false)
        if (msgs != null) {
            mMessageList.addAll(msgs)
        }
        Toast.makeText(context, "new Message", Toast.LENGTH_SHORT).show()
        mAdapter?.notifyDataSetChanged()
    }
}
