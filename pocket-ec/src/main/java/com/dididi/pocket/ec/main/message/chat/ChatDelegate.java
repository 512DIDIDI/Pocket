package com.dididi.pocket.ec.main.message.chat;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;
import android.widget.Toast;

import com.dididi.pocket.core.delegates.PocketDelegate;
import com.dididi.pocket.core.entity.Message;
import com.dididi.pocket.ec.R;
import com.dididi.pocket.ec.R2;
import com.dididi.pocket.ec.main.message.chat.adapter.ChatAdapter;
import com.dididi.pocket.ec.main.message.chat.adapter.MorePagerAdapter;
import com.mikepenz.iconics.view.IconicsTextView;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * @author dididi
 * @describe 聊天页面
 * @since 07/09/2018
 */

public class ChatDelegate extends PocketDelegate
        implements TextView.OnEditorActionListener {

    private List<Message> mMessageList = new ArrayList<>();
    private ChatAdapter mAdapter = null;
    private Message mMessage = null;
    private Message getMessage = null;
    private List<View> mViewList = new ArrayList<>();
    private MorePagerAdapter mMoreAdapter = null;

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
    @BindView(R2.id.delegate_msg_chat_more_page)
    ViewPager mMorePager = null;

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
        mMorePager.setVisibility(View.VISIBLE);
    }

    /**
     * 构造函数私有化
     * 防止外部实例化ChatDelegate而导致没有传入message bundle出现错误
     * 只能通过ChatDelegate的getStartChat()方法获取ChatDelegate
     */
    @SuppressLint("ValidFragment")
    private ChatDelegate() { }

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
        getMessage = (Message) bundle.get("message");
        if (getMessage == null) {
            throw new RuntimeException("message为空 获取失败");
        }
        if (getMessage.getContent() != null) {
            //如果传入的消息带有消息内容，则添加到头部list中
            mMessageList.add(0, getMessage);
        }
        //设置chat页面标题
        mName.setText(getMessage.getReceivedUserName());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mAdapter = new ChatAdapter(R.layout.item_message_chat, mMessageList);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);
        //输入框发送消息
        mEdit.setOnEditorActionListener(this);
        ViewGroup viewGroup = null;
        mViewList.add(getLayoutInflater().inflate(R.layout.item_msg_chat_more,viewGroup,false));
        mMoreAdapter = new MorePagerAdapter(getContext(),mViewList);
        mMorePager.setAdapter(mMoreAdapter);
    }

    @Override
    public void setSwipeBackEnable(boolean enable) {
        super.setSwipeBackEnable(true);
    }

    /**
     * 需要传入ChatDelegate的数据
     * 外界仅能通过此方法获取ChatDelegate实例
     *
     * @param message 传入的消息
     * @return 返回一个包装后的chatDelegate
     */
    public static ChatDelegate getStartChat(Message message) {
        ChatDelegate chatDelegate = new ChatDelegate();
        Bundle bundle = new Bundle();
        bundle.putParcelable("message", message);
        chatDelegate.setArguments(bundle);
        return chatDelegate;
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEND) {
            if (mEdit.getText() == null) {
                Toast.makeText(getContext(), "发送消息不能为空", Toast.LENGTH_SHORT).show();
            } else {
                mMessage = new Message(mEdit.getText().toString(), Message.TYPE_SENT,
                        getMessage.getSendUser(), getMessage.getReceivedUser(), "21/9/2018");
                mMessageList.add(mMessage);
                //插入数据源
                mAdapter.notifyItemInserted(mMessageList.size());
                mRecyclerView.scrollToPosition(mMessageList.size() - 1);
            }
            //发送完成清空输入框
            mEdit.setText("");
        }
        return false;
    }

}
