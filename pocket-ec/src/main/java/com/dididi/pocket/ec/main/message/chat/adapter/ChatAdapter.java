package com.dididi.pocket.ec.main.message.chat.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.dididi.pocket.core.entity.Message;
import com.dididi.pocket.core.ui.GlideApp;
import com.dididi.pocket.ec.R;

import java.util.List;

/**
 * @author dididi
 * @describe 聊天页面adapter
 * @since 21/09/2018
 */

public class ChatAdapter extends BaseQuickAdapter<Message, BaseViewHolder> {

    public ChatAdapter(int layoutResId, @Nullable List<Message> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Message item) {
        //根据消息类型的不同决定隐藏显示哪边布局
        if (item.getType() == Message.TYPE_RECEIVED) {
            //接收消息
            helper.getView(R.id.item_message_chat_received_layout)
                    .setVisibility(View.VISIBLE);
            helper.getView(R.id.item_message_chat_send_layout)
                    .setVisibility(View.GONE);
            GlideApp.with(mContext)
                    .load(Integer.parseInt(item.getReceivedUserAvatar()))
                    .into((ImageView) helper.getView(R.id.item_message_chat_received_avatar));
            if (item.getPicture() != null) {
                helper.getView(R.id.item_message_chat_received_text_layout).setVisibility(View.GONE);
                helper.getView(R.id.item_message_chat_received_picture).setVisibility(View.VISIBLE);
                helper.addOnClickListener(R.id.item_message_chat_received_picture);
                GlideApp.with(mContext)
                        .load(item.getPicture())
                        .override(400,400)
                        .fitCenter()
                        .into((ImageView) helper.getView(R.id.item_message_chat_received_picture));
            }else{
                helper.getView(R.id.item_message_chat_received_text_layout).setVisibility(View.VISIBLE);
                helper.getView(R.id.item_message_chat_received_picture).setVisibility(View.GONE);
                helper.setText(R.id.item_message_chat_received_msg, item.getContent())
                        .addOnClickListener(R.id.item_message_chat_received_avatar)
                        .addOnLongClickListener(R.id.item_message_chat_received_layout);
            }
        } else if (item.getType() == Message.TYPE_SENT) {
            //发送消息
            helper.getView(R.id.item_message_chat_send_layout)
                    .setVisibility(View.VISIBLE);
            helper.getView(R.id.item_message_chat_received_layout)
                    .setVisibility(View.GONE);
            GlideApp.with(mContext)
                    .load(Integer.parseInt(item.getSendUserAvatar()))
                    .into((ImageView) helper.getView(R.id.item_message_chat_send_avatar));
            //如果有发送图片，则显示imageView隐藏TextView
            if (item.getPicture() != null) {
                helper.getView(R.id.item_message_chat_send_text_layout).setVisibility(View.GONE);
                helper.getView(R.id.item_message_chat_send_picture).setVisibility(View.VISIBLE);
                helper.addOnClickListener(R.id.item_message_chat_send_picture);
                GlideApp.with(mContext)
                        .load(item.getPicture())
                        .override(400,400)
                        .fitCenter()
                        .into((ImageView) helper.getView(R.id.item_message_chat_send_picture));
            }else{
                helper.getView(R.id.item_message_chat_send_text_layout).setVisibility(View.VISIBLE);
                helper.getView(R.id.item_message_chat_send_picture).setVisibility(View.GONE);
                helper.setText(R.id.item_message_chat_send_msg, item.getContent())
                        .addOnClickListener(R.id.item_message_chat_send_avatar)
                        .addOnLongClickListener(R.id.item_message_chat_send_layout);
            }
        }
    }
}
