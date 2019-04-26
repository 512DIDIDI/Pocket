package com.dididi.pocket.ec.main.message

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.Toast
import com.chad.library.adapter.base.BaseQuickAdapter
import com.dididi.pocket.core.delegates.PocketDelegate
import com.dididi.pocket.core.delegates.bottom.BottomItemDelegate
import com.dididi.pocket.core.entity.Message
import com.dididi.pocket.core.fakedata.FakeUser
import com.dididi.pocket.core.ui.dialog.PocketDialog
import com.dididi.pocket.ec.R
import com.dididi.pocket.ec.main.message.adapter.MessageAdapter
import com.dididi.pocket.ec.main.message.chat.ChatDelegate
import kotlinx.android.synthetic.main.delegate_msg_message.*
import java.util.*


/**
 * Created by dididi
 * on 24/07/2018 .
 */

class MessageItemDelegate : BottomItemDelegate(), BaseQuickAdapter.OnItemChildClickListener {

    private val mMsgList = ArrayList<Message>()
    private var layoutManager: LinearLayoutManager? = null

    override fun setLayout(): Any {
        return R.layout.delegate_msg_message
    }

    override fun onBindChildView(savedInstanceState: Bundle?, rootView: View?) {
    }

    override fun onBindView(savedInstanceState: Bundle?, rootView: View?) {
        delegate_msg_message_searchBar!!.setLeftIcon(context?.getString(R.string.faw_plus))
        initFakeMessage()
        //设置布局方式
        layoutManager = LinearLayoutManager(context,
                LinearLayoutManager.VERTICAL, false)
        delegate_msg_message_list_view!!.layoutManager = layoutManager
        val mAdapter = MessageAdapter(R.layout.item_message_list, mMsgList)
        mAdapter.onItemChildClickListener = this
        delegate_msg_message_list_view!!.adapter = mAdapter
    }

    override fun onItemChildClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
        val message = adapter.getItem(position) as Message?
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
            getParentDelegate<PocketDelegate>().supportDelegate.start(ChatDelegate.getStartChat(message))
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
        val sendUser = FakeUser.getUser("1")
        val msg1 = Message(sendUser.name + " 试试上下滑动",
                Message.TYPE_RECEIVED, sendUser, FakeUser.getUser("2"),
                "27/7/2018")
        val msg2 = Message(sendUser.name + " 试试向左滑动",
                Message.TYPE_RECEIVED, sendUser, FakeUser.getUser("3"),
                "27/7/2018")
        val msg3 = Message(sendUser.name + " 试试消息置顶",
                Message.TYPE_RECEIVED, sendUser, FakeUser.getUser("4"),
                "27/7/2018")
        val msg4 = Message(sendUser.name + " 试试删除消息",
                Message.TYPE_RECEIVED, sendUser, FakeUser.getUser("5"),
                "27/7/2018")
        val msg5 = Message(sendUser.name + "试试发送一条消息",
                Message.TYPE_RECEIVED, sendUser, FakeUser.getUser("6"),
                "27/7/2018")
        val msg6 = Message(sendUser.name + " 试试侧滑返回",
                Message.TYPE_RECEIVED, sendUser, FakeUser.getUser("7"),
                "27/7/2018")
        val msg7 = Message(sendUser.name + " 祝您阖家欢乐",
                Message.TYPE_RECEIVED, sendUser, FakeUser.getUser("8"),
                "27/7/2018")
        val msg8 = Message(sendUser.name + " 祝您六六大顺",
                Message.TYPE_RECEIVED, sendUser, FakeUser.getUser("9"),
                "27/7/2018")
        val msg9 = Message(sendUser.name + " 祝您福星高照",
                Message.TYPE_RECEIVED, sendUser, FakeUser.getUser("10"),
                "27/7/2018")
        val msg10 = Message(sendUser.name + " 祝您福如东海",
                Message.TYPE_RECEIVED, sendUser, FakeUser.getUser("11"),
                "27/7/2018")
        val msg11 = Message(sendUser.name + " 祝您寿比南山",
                Message.TYPE_RECEIVED, sendUser, FakeUser.getUser("12"),
                "27/7/2018")
        mMsgList.add(msg1)
        mMsgList.add(msg2)
        mMsgList.add(msg3)
        mMsgList.add(msg4)
        mMsgList.add(msg5)
        mMsgList.add(msg6)
        mMsgList.add(msg7)
        mMsgList.add(msg8)
        mMsgList.add(msg9)
        mMsgList.add(msg10)
        mMsgList.add(msg11)
    }

}
