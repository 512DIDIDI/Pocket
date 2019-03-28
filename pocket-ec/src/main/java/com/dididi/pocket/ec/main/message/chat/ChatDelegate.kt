package com.dididi.pocket.ec.main.message.chat

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.dididi.pocket.core.delegates.PocketDelegate
import com.dididi.pocket.core.entity.Message
import com.dididi.pocket.core.ui.animation.PocketAnimation
import com.dididi.pocket.core.ui.item.MoreButtonItem
import com.dididi.pocket.ec.R
import com.dididi.pocket.ec.main.message.chat.adapter.ChatAdapter
import com.dididi.pocket.ec.main.message.chat.adapter.MorePagerAdapter
import kotlinx.android.synthetic.main.delegate_msg_chat.*
import java.io.FileNotFoundException
import java.util.*


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
@SuppressLint("ValidFragment")
class ChatDelegate private constructor() : PocketDelegate(), TextView.OnEditorActionListener {

    private val mMessageList = ArrayList<Message>()
    private var mAdapter: ChatAdapter? = null
    private var getMessage: Message? = null
    private val mViewList = ArrayList<View>()
    private var morePagerView: View? = null

    override fun setLayout(): Any {
        return R.layout.delegate_msg_chat
    }

    override fun onBindView(savedInstanceState: Bundle?, rootView: View) {
        //获取从bundle传入的数据
        getMessage = arguments!!.get("message") as Message
        if (getMessage!!.content != null) {
            //如果传入的消息带有消息内容，则添加到头部list中
            mMessageList.add(0, getMessage!!)
        }
        mAdapter = ChatAdapter(R.layout.item_message_chat, mMessageList)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //设置chat页面标题
        delegate_msg_chat_name?.text = getMessage!!.receivedUserName
        val layoutManager = LinearLayoutManager(context)
        //初始化消息页面
        delegate_msg_chat_recyclerView?.layoutManager = layoutManager
        delegate_msg_chat_recyclerView?.adapter = mAdapter
        //输入框发送消息
        delegate_msg_chat_edit?.setOnEditorActionListener(this)
        //更多页面viewpager 添加view到list中
        val viewGroup: ViewGroup? = null
        morePagerView = layoutInflater.inflate(R.layout.item_msg_chat_more, viewGroup, false)
        mViewList.add(morePagerView!!)
        val moreAdapter = MorePagerAdapter(context!!, mViewList)
        delegate_msg_chat_more_page?.adapter = moreAdapter
        onClickEvent()
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
        val moreCamera = morePagerView!!
                .findViewById<MoreButtonItem>(R.id.item_msg_chat_more_camera)
        moreCamera.setOnClickListener {
            applyPermission(context!!)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<out String>,
                                            grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == WRITE_EXTERNAL_STORAGE &&
                grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            openCamera(context!!)
        } else {
            Toast.makeText(context, "没有读写权限", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            OPEN_CAMERA -> {
                try {
                    val bitmap = BitmapFactory
                            .decodeStream(context!!.contentResolver.openInputStream(photoUri))
                    val message = Message(bitmap, Message.TYPE_SENT, getMessage?.sendUser,
                            getMessage?.receivedUser, "27/3/2019")
                    mMessageList.add(message)
                    mAdapter?.notifyItemInserted(mMessageList.size)
                    delegate_msg_chat_recyclerView?.scrollToPosition(mMessageList.size - 1)
                } catch (e: FileNotFoundException) {
                    e.printStackTrace()
                }
            }
            OPEN_ALBUM -> {
            }
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
                val message = Message(delegate_msg_chat_edit?.text.toString(), Message.TYPE_SENT,
                        getMessage?.sendUser, getMessage?.receivedUser, "21/9/2018")
                mMessageList.add(message)
                //插入数据源
                mAdapter?.notifyItemInserted(mMessageList.size)
                delegate_msg_chat_recyclerView?.scrollToPosition(mMessageList.size - 1)
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
         * @param message 传入的消息
         * @return 返回一个包装后的chatDelegate
         */
        fun getStartChat(message: Message): ChatDelegate {
            val chatDelegate = ChatDelegate()
            val bundle = Bundle()
            bundle.putParcelable("message", message)
            chatDelegate.arguments = bundle
            return chatDelegate
        }
    }

}
