package com.dididi.pocket.ec.main.message.chat;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.inputmethod.EditorInfo;
import android.widget.Toast;

import com.chad.library.adapter.base.util.TouchEventUtil;
import com.dididi.pocket.core.delegates.PocketDelegate;
import com.dididi.pocket.core.entity.Message;
import com.dididi.pocket.core.ui.animation.PocketAnimation;
import com.dididi.pocket.ec.R;
import com.dididi.pocket.ec.R2;
import com.dididi.pocket.ec.main.message.chat.adapter.ChatAdapter;
import com.mikepenz.iconics.view.IconicsTextView;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import me.yokeyword.fragmentation.SwipeBackLayout;


/**
 * @author dididi
 * @describe 聊天页面
 * @since 07/09/2018
 */

public class ChatDelegate extends PocketDelegate {

    private List<Message> mMessageList = new ArrayList<>();
    private ChatAdapter mAdapter = null;
    private Message mMessage = null;

    @BindView(R2.id.delegate_msg_chat_recyclerView)
    RecyclerView mRecyclerView = null;
    @BindView(R2.id.delegate_msg_chat_toolbar)
    Toolbar mToolbar = null;
    @BindView(R2.id.delegate_msg_chat_back_btn)
    IconicsTextView mBack = null;
    @BindView(R2.id.delegate_msg_chat_personal)
    IconicsTextView mPersonal = null;
    @BindView(R2.id.delegate_msg_chat_voice)
    IconicsTextView mVoice = null;
    @BindView(R2.id.delegate_msg_chat_more)
    IconicsTextView mMore = null;
    @BindView(R2.id.delegate_msg_chat_edit)
    MaterialEditText mEdit = null;
    @BindView(R2.id.delegate_msg_chat_name)
    AppCompatTextView mName = null;

    @OnClick(R2.id.delegate_msg_chat_back_btn)
    public void onBack() {
        getSupportDelegate().pop();
    }

    @OnClick(R2.id.delegate_msg_chat_personal)
    public void onPersonal() {
        Toast.makeText(getContext(), "点击个人", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R2.id.delegate_msg_chat_voice)
    public void onVoice() {
        Toast.makeText(getContext(), "点击声音", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R2.id.delegate_msg_chat_more)
    public void onMore() {
        Toast.makeText(getContext(), "点击更多", Toast.LENGTH_SHORT).show();
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_msg_chat;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        //获取从bundle传入的数据
        Bundle bundle = getArguments();
        if (null == bundle) {
            throw new RuntimeException("获取消息失败 bundle为空");
        }
        Message message = (Message) bundle.get("message");
        if (null == message) {
            throw new RuntimeException("获取消息失败");
        }
        mName.setText(message.getReceivedUserName());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mMessageList.add(0, message);
        mAdapter = new ChatAdapter(R.layout.item_message_chat, mMessageList);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);
        //输入框发送消息
        mEdit.setOnEditorActionListener((textView, i, keyEvent) -> {
            if (i == EditorInfo.IME_ACTION_SEND) {
                if (mEdit.getText() == null) {
                    Toast.makeText(getContext(), "发送消息不能为空", Toast.LENGTH_SHORT).show();
                } else {
                    mMessage = new Message(mEdit.getText().toString(), Message.TYPE_SENT,
                            1, message.getReceivedUserId(), "我的名字",
                            message.getReceivedUserName(), String.valueOf(R.mipmap.avatarman01),
                            String.valueOf(message.getReceivedUserAvatar()), "21/9/2018");
                    mMessageList.add(mMessage);
                    //插入数据源
                    mAdapter.notifyItemInserted(mMessageList.size());
                    mRecyclerView.scrollToPosition(mMessageList.size() - 1);
                }
                mEdit.setText("");
            }
            return false;
        });
    }

    @Override
    public void setSwipeBackEnable(boolean enable) {
        super.setSwipeBackEnable(true);
    }


}
