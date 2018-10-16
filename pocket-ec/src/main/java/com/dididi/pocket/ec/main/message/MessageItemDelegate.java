package com.dididi.pocket.ec.main.message;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.dididi.pocket.core.entity.User;
import com.dididi.pocket.core.fakedata.FakeUser;
import com.dididi.pocket.core.ui.dialog.PocketDialog;
import com.dididi.pocket.ec.R;
import com.dididi.pocket.ec.R2;
import com.dididi.pocket.core.ui.item.SearchBarItem;
import com.dididi.pocket.core.entity.Message;
import com.dididi.pocket.core.delegates.bottom.BottomItemDelegate;
import com.dididi.pocket.ec.main.message.adapter.MessageAdapter;
import com.dididi.pocket.ec.main.message.chat.ChatDelegate;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


/**
 * Created by dididi
 * on 24/07/2018 .
 */

public class MessageItemDelegate extends BottomItemDelegate
        implements BaseQuickAdapter.OnItemChildClickListener {

    @BindView(R2.id.delegate_msg_message_searchBar)
    SearchBarItem mSearchBar = null;
    @BindView(R2.id.delegate_msg_message_list_view)
    RecyclerView mMsgRecyclerView = null;

    private List<Message> mMsgList = new ArrayList<>();
    private LinearLayoutManager layoutManager = null;

    @Override
    public Object setLayout() {
        return R.layout.delegate_msg_message;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        mSearchBar.setLeftIcon("{faw-plus}");
        initFakeMessage();
        //设置布局方式
        layoutManager = new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false);
        mMsgRecyclerView.setLayoutManager(layoutManager);
        MessageAdapter mAdapter = new MessageAdapter(R.layout.item_message_list, mMsgList);
        mAdapter.setOnItemChildClickListener(this);
        mMsgRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        Message message = (Message) adapter.getItem(position);
        if (null == message) {
            throw new RuntimeException("message can not be null");
        }
        //删除消息
        if (view.getId() == R.id.item_message_delete) {
            PocketDialog dialog = PocketDialog.getInstance(getContext());
            dialog.setTitle("删除聊天")
                    .setContent("确认删除这条聊天记录吗？")
                    .isCancelableOnTouchOutside(true)
                    .setConfirmClickListener(v -> {
                        mMsgList.remove(message);
                        adapter.notifyDataSetChanged();
                        dialog.dismiss();
                    })
                    .setCancelClickListener(v -> dialog.dismiss())
                    .show();
        }
        //置顶消息
        if (view.getId() == R.id.item_message_top) {
            if (position > 0 && position < mMsgList.size()) {
                //先移除该条消息
                mMsgList.remove(message);
                //插入一条位置0的消息
                adapter.notifyItemInserted(0);
                mMsgList.add(0, message);
                //移除position+1的消息（即原来的消息）
                adapter.notifyItemRemoved(position + 1);
                if (0 == layoutManager.findFirstVisibleItemPosition()) {
                    mMsgRecyclerView.scrollToPosition(0);
                }
            } else {
                Toast.makeText(getContext(), "消息已置顶", Toast.LENGTH_SHORT).show();
            }
        }
        if (view.getId() == R.id.item_message_main_layout) {
            //点击跳转Chat页面
            getParentDelegate().getSupportDelegate().start(ChatDelegate.getStartChat(message));
        }
    }

    /**
     * 初始化消息列表 不用管它什么意思
     */
    private void initFakeMessage() {
        User sendUser = FakeUser.getUser("1");
        Message msg1 =
                new Message(sendUser.getName() + " 祝您长命百岁",
                        Message.TYPE_RECEIVED, sendUser, FakeUser.getUser("2"),
                        "27/7/2018");
        Message msg2 = new Message(sendUser.getName() + " 祝您万事如意",
                Message.TYPE_RECEIVED, sendUser, FakeUser.getUser("3"),
                "27/7/2018");
        Message msg3 = new Message(sendUser.getName() + " 祝您心想事成",
                Message.TYPE_RECEIVED, sendUser, FakeUser.getUser("4"),
                "27/7/2018");
        Message msg4 = new Message(sendUser.getName() + " 祝您吉祥如意",
                Message.TYPE_RECEIVED, sendUser, FakeUser.getUser("5"),
                "27/7/2018");
        Message msg5 = new Message(sendUser.getName() + " 祝您事事顺心",
                Message.TYPE_RECEIVED, sendUser, FakeUser.getUser("6"),
                "27/7/2018");
        Message msg6 =
                new Message(sendUser.getName() + " 祝您财源滚滚",
                        Message.TYPE_RECEIVED, sendUser, FakeUser.getUser("7"),
                        "27/7/2018");
        Message msg7 = new Message(sendUser.getName() + " 祝您阖家欢乐",
                Message.TYPE_RECEIVED, sendUser, FakeUser.getUser("8"),
                "27/7/2018");
        Message msg8 = new Message(sendUser.getName() + " 祝您六六大顺",
                Message.TYPE_RECEIVED, sendUser, FakeUser.getUser("9"),
                "27/7/2018");
        Message msg9 = new Message(sendUser.getName() + " 祝您福星高照",
                Message.TYPE_RECEIVED, sendUser, FakeUser.getUser("10"),
                "27/7/2018");
        Message msg10 = new Message(sendUser.getName() + " 祝您福如东海",
                Message.TYPE_RECEIVED, sendUser, FakeUser.getUser("11"),
                "27/7/2018");
        Message msg11 = new Message(sendUser.getName() + " 祝您寿比南山",
                Message.TYPE_RECEIVED, sendUser, FakeUser.getUser("12"),
                "27/7/2018");
        mMsgList.add(msg1);
        mMsgList.add(msg2);
        mMsgList.add(msg3);
        mMsgList.add(msg4);
        mMsgList.add(msg5);
        mMsgList.add(msg6);
        mMsgList.add(msg7);
        mMsgList.add(msg8);
        mMsgList.add(msg9);
        mMsgList.add(msg10);
        mMsgList.add(msg11);
    }
}
