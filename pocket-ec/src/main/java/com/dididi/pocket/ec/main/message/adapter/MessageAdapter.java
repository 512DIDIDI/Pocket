package com.dididi.pocket.ec.main.message.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.dididi.pocket.core.entity.Message;
import com.dididi.pocket.ec.R;

import java.util.List;

import androidx.annotation.Nullable;

/**
 * @author dididi
 * @describe
 * @since 21/09/2018
 */

public class MessageAdapter extends BaseQuickAdapter<Message, BaseViewHolder> {

    public MessageAdapter(int layoutResId, @Nullable List<Message> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Message item) {
        //todo:这里需要考虑一下load() 先写死传入id 后期再改
        Glide.with(mContext)
                .load(Integer.parseInt(item.getReceivedUserAvatar()))
                .into((ImageView) helper.getView(R.id.item_message_avatar));
        helper.setText(R.id.item_message_name, item.getReceivedUserName())
                .setText(R.id.item_message_content, item.getContent())
                .setText(R.id.item_message_date, item.getDate())
                .addOnClickListener(R.id.item_message_main_layout)
                .addOnClickListener(R.id.item_message_delete)
                .addOnClickListener(R.id.item_message_top);
    }
}
