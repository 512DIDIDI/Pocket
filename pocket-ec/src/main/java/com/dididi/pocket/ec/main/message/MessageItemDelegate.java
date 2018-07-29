package com.dididi.pocket.ec.main.message;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ListView;

import com.dididi.pocket.ec.R;
import com.dididi.pocket.ec.R2;
import com.dididi.pocket.ec.item.SearchBarItem;
import com.dididi.pocket.ec.main.message.adapter.MessageAdapter;
import com.dididi.pocket.ec.main.message.entity.Message;
import com.dididi.pocket.ec.main.message.listener.PocketOnSwipeListener;
import com.dididi.pocket_core.delegates.bottom.BottomItemDelegate;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


/**
 * Created by dididi
 * on 24/07/2018 .
 */

public class MessageItemDelegate extends BottomItemDelegate {

    @BindView(R2.id.msg_item_searchBar)
    SearchBarItem mSearchBar = null;
    @BindView(R2.id.msg_item_list_view)
    RecyclerView mMsgRecyclerView = null;

    private List<Message> mMsgList = new ArrayList<>();

    @Override
    public Object setLayout() {
        return R.layout.delegate_msg_message;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        mSearchBar.setLeftIcon("{faw-plus}");
        initMessage();
        final LinearLayoutManager layoutManager =
                new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mMsgRecyclerView.setLayoutManager(layoutManager);
        final MessageAdapter adapter = new MessageAdapter(mMsgList);
        //监听adapter点击删除置顶按钮的事件
        adapter.setOnTopDelListener(new PocketOnSwipeListener() {
            @Override
            public void onDelete(int position) {
                if (position > 0 && position < mMsgList.size()) {
                    //移除列表中对应的消息对象
                    mMsgList.remove(position);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onTop(int position) {
                if (position > 0 && position < mMsgList.size()) {
                    //存储要置顶的消息
                    Message msg = mMsgList.get(position);
                    //移出该位置的消息
                    mMsgList.remove(position);
                    //插入一条位置为0的消息
                    adapter.notifyItemInserted(0);
                    mMsgList.add(0, msg);
                    adapter.notifyItemRemoved(position + 1);
                    if (layoutManager.findFirstVisibleItemPosition() == 0){
                        mMsgRecyclerView.scrollToPosition(0);
                    }
                }
            }
        });
        mMsgRecyclerView.setAdapter(adapter);

    }

    //初始化消息列表
    private void initMessage() {
        for (int i = 0; i < 3; i++) {
            Message msg1 = new Message(R.drawable.cat, "大野猫",
                    "我是一只大大大大大大大大大大大大大大大大大大大大大大大大大大大大大大大大大野猫",
                    "27/7/2018");
            Message msg2 = new Message(R.drawable.cat2, "小野喵",
                    "我是小野喵", "27/7/2018");
            Message msg3 = new Message(R.drawable.flower, "大菊花",
                    "我是一朵大菊花", "27/7/2018");
            Message msg4 = new Message(R.drawable.guitar, "大吉他",
                    "我是一把小吉他", "27/7/2018");
            Message msg5 = new Message(R.drawable.penguin, "笨企鹅",
                    "我是一只聪明的企鹅", "27/7/2018");
            mMsgList.add(msg1);
            mMsgList.add(msg2);
            mMsgList.add(msg3);
            mMsgList.add(msg4);
            mMsgList.add(msg5);
        }
    }
}
