package com.dididi.pocket.ec.main.message;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.dididi.pocket.ec.R;
import com.dididi.pocket.ec.R2;
import com.dididi.pocket.ec.item.SearchBarItem;
import com.dididi.pocket.ec.main.message.Adapter.MessageListAdapter;
import com.dididi.pocket.ec.main.message.Entity.Message;
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
    ListView mMessageList = null;

    private List<Message> messages = new ArrayList<>();

    @Override
    public Object setLayout() {
        return R.layout.delegate_msg_message;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        mSearchBar.setLeftIconGone();
        initMessage();
        //TODO:这里getContext()可能为空.
        MessageListAdapter adapter = new MessageListAdapter(getContext(),
                R.layout.item_message_list, messages);
        mMessageList.setAdapter(adapter);
        mMessageList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Message message = messages.get(i);
                Toast.makeText(getContext(), "向" + message.getUserName() + "发起对话",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

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
            messages.add(msg1);
            messages.add(msg2);
            messages.add(msg3);
            messages.add(msg4);
            messages.add(msg5);
        }
    }
}
