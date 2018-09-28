package com.dididi.pocket.ec.main.message;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.dididi.pocket.ec.R;
import com.dididi.pocket.ec.R2;
import com.dididi.pocket.ec.item.SearchBarItem;
import com.dididi.pocket.core.entity.Message;
import com.dididi.pocket.core.delegates.bottom.BottomItemDelegate;
import com.dididi.pocket.ec.main.message.adapter.MessageAdapter;

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


    /**
     * 初始化消息列表
     */
    private void initFakeMessage() {
        for (int i = 0; i < 3; i++) {
            Message msg1 =
                    new Message("我是一只大大大大大大大大大大大大大大大大大大大大大大大大大大大大大大大大大野猫",
                            Message.TYPE_RECEIVED, 1, 10 + i,
                            "我的名字", "大野猫",
                            String.valueOf(R.mipmap.avatarman01), String.valueOf(R.mipmap.avatarwoman06),
                            "27/7/2018");
            Message msg2 = new Message("我是小野喵",
                    Message.TYPE_RECEIVED, 1, 20 + i,
                    "我的名字", "小野喵",
                    String.valueOf(R.mipmap.avatarman01), String.valueOf(R.mipmap.avatarwoman04),
                    "27/7/2018");
            Message msg3 = new Message("我是一朵大菊花",
                    Message.TYPE_RECEIVED, 1, 30 + i,
                    "我的名字", "大菊花",
                    String.valueOf(R.mipmap.avatarman01), String.valueOf(R.mipmap.avatarwoman03),
                    "27/7/2018");
            Message msg4 = new Message("我是一把小吉他",
                    Message.TYPE_RECEIVED, 1, 40 + i,
                    "我的名字", "大吉他",
                    String.valueOf(R.mipmap.avatarman01), String.valueOf(R.mipmap.avatarman05),
                    "27/7/2018");
            Message msg5 = new Message("我是一只聪明的企鹅",
                    Message.TYPE_RECEIVED, 1, 10 + i,
                    "我的名字", "笨企鹅",
                    String.valueOf(R.mipmap.avatarman01), String.valueOf(R.mipmap.avatarman03),
                    "27/7/2018");
            mMsgList.add(msg1);
            mMsgList.add(msg2);
            mMsgList.add(msg3);
            mMsgList.add(msg4);
            mMsgList.add(msg5);
        }
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        Message message = (Message) adapter.getItem(position);
        if (null == message) {
            throw new RuntimeException("message can not be null");
        }
        //删除消息
        if (view.getId() == R.id.item_message_delete) {
            mMsgList.remove(message);
            adapter.notifyDataSetChanged();
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
            //利用bundle向chatDelegate传输数据
            ChatDelegate chatDelegate = new ChatDelegate();
            Bundle bundle = new Bundle();
            bundle.putParcelable("message", message);
            chatDelegate.setArguments(bundle);
            getParentDelegate().getSupportDelegate().start(chatDelegate);
        }
    }
}
