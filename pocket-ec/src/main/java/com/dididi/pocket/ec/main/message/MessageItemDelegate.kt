package com.dididi.pocket.ec.main.message

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import com.chad.library.adapter.base.BaseQuickAdapter
import com.dididi.pocket.core.delegates.PocketDelegate
import com.dididi.pocket.core.delegates.bottom.BottomItemDelegate
import com.dididi.pocket.core.im.IIMCallback
import com.dididi.pocket.core.ui.dialog.PocketDialog
import com.dididi.pocket.ec.R
import com.dididi.pocket.ec.main.message.adapter.MessageAdapter
import com.dididi.pocket.ec.main.message.chat.ChatDelegate
import com.dididi.pocket.ec.main.message.chat.model.C2CChatInfo
import com.dididi.pocket.ec.main.message.chat.model.C2CChatManager
import com.dididi.pocket.ec.main.message.model.MessageInfo
import com.dididi.pocket.ec.main.message.model.MessageManager
import com.gyf.immersionbar.ktx.immersionBar
import com.tencent.imsdk.TIMConversationType
import kotlinx.android.synthetic.main.delegate_msg_message.*


/**
 * Created by dididi
 * on 24/07/2018 .
 */

class MessageItemDelegate : BottomItemDelegate(), BaseQuickAdapter.OnItemChildClickListener {

    private var mMsgList = ArrayList<MessageInfo>()
    private var layoutManager: LinearLayoutManager? = null
    private var mAdapter: MessageAdapter? = null

    override fun setLayout(): Any {
        return R.layout.delegate_msg_message
    }

    override fun onBindChildView(savedInstanceState: Bundle?, rootView: View?) {
    }

    override fun onBindView(savedInstanceState: Bundle?, rootView: View?) {

        //设置布局方式
        layoutManager = LinearLayoutManager(context,
                LinearLayoutManager.VERTICAL, false)
        delegate_msg_message_list_view!!.layoutManager = layoutManager
        mAdapter = MessageAdapter(R.layout.item_message_list, mMsgList)
        mAdapter!!.onItemChildClickListener = this
        delegate_msg_message_list_view!!.adapter = mAdapter
        delegate_msg_message_refresh.setColorSchemeColors(ContextCompat.getColor(context!!, R.color.textColorDark))
        delegate_msg_message_refresh.setOnRefreshListener {
            MessageManager.getInstance().loadConversation(object :IIMCallback{
                override fun onSuccess(data: Any?) {
                    delegate_msg_message_refresh.isRefreshing = false
                    mAdapter!!.updateConversations(data as ArrayList<MessageInfo>)
                    Toast.makeText(context, "刷新成功", Toast.LENGTH_SHORT).show()
                }

                override fun onError(module: String, errCode: Int, errMsg: String?) {
                }
            })
        }
        initFakeMessage()
        delegate_msg_message_layout_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val users = resources.getStringArray(R.array.spinner_tim_account)
                val info = MessageInfo().apply {
                    peer = users[position]
                    title = peer
                    lastMessageTime = System.currentTimeMillis()
                }
                mMsgList.add(info)
                mAdapter!!.notifyDataSetChanged()
            }
        }
    }

    override fun getTitleBarId() = R.id.delegate_msg_message_toolbar

    override fun initImmersionBar() {
        immersionBar {
            flymeOSStatusBarFontColor(R.color.textColorWhite)
            keyboardEnable(true)
        }
    }

    override fun onItemChildClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
        val message = adapter.getItem(position) as MessageInfo?
                ?: throw RuntimeException("message can not be null")
        //删除消息
        if (view.id == R.id.item_message_delete) {
            val dialog = PocketDialog.getInstance(context)
            dialog.setTitle("删除聊天")
                    .setContent("确认删除这条聊天记录吗？")
                    .isCancelableOnTouchOutside(true)
                    .setConfirmClickListener {
                        mMsgList.remove(message)
                        adapter.notifyDataSetChanged()
                        dialog.dismiss()
                    }
                    .setCancelClickListener { dialog.dismiss() }
                    .show()
        }
        //置顶消息
        if (view.id == R.id.item_message_top) {
            if (position > 0 && position < mMsgList.size) {
                //先移除该条消息
                mMsgList.remove(message)
                //插入一条位置0的消息
                adapter.notifyItemInserted(0)
                mMsgList.add(0, message)
                //移除position+1的消息（即原来的消息）
                adapter.notifyItemRemoved(position + 1)
                if (0 == layoutManager!!.findFirstVisibleItemPosition()) {
                    delegate_msg_message_list_view!!.scrollToPosition(0)
                }
            } else {
                Toast.makeText(context, "消息已置顶", Toast.LENGTH_SHORT).show()
            }
        }
        if (view.id == R.id.item_message_main_layout) {
            //点击跳转Chat页面
            C2CChatManager.getInstance().setCurrentChatInfo(C2CChatInfo().apply { peer = message.peer;chatName = message.peer;type = TIMConversationType.C2C })
            getParentDelegate<PocketDelegate>().supportDelegate.start(ChatDelegate.getStartChat(message.peer!!))
        }
    }

    /**
     * 重写父类的onScrollToTop()方法，RecyclerView返回顶部
     */
    override fun onScrollToTop() {
        //设置toolbar的内容自动滑出来
        delegate_msg_message_appbar!!.setExpanded(true)
        delegate_msg_message_list_view!!.smoothScrollToPosition(0)
    }

    /**
     * 重写父类的onRefresh()方法，刷新页面
     */
    override fun onRefresh() {

    }

    /**
     * @return 是否位于顶部
     */
    override fun isTop(): Boolean {
        return false
    }

    /**
     * 初始化消息列表 不用管它什么意思
     */
    private fun initFakeMessage() {
        MessageManager.getInstance().loadConversation(object : IIMCallback {
            override fun onSuccess(data: Any?) {
                mAdapter!!.addConversations(data as ArrayList<MessageInfo>)
                Toast.makeText(context, "加载成功", Toast.LENGTH_SHORT).show()
            }

            override fun onError(module: String, errCode: Int, errMsg: String?) {
                Toast.makeText(context, "加载失败", Toast.LENGTH_SHORT).show()
            }
        })
    }

}
