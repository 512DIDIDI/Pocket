package com.dididi.pocket.ec.main.message.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.dididi.pocket.core.entity.Message;
import com.dididi.pocket.core.ui.GlideApp;
import com.dididi.pocket.ec.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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
        GlideApp.with(mContext)
                .load(Integer.parseInt(item.getTargetUser().getAvatar()))
                .into((ImageView) helper.getView(R.id.item_message_avatar));
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String date = sdf.format(new Date(item.getDate()));
        helper.setText(R.id.item_message_name, item.getTargetUser().getName())
                //.setText(R.id.item_message_content,item.getContent())
                .setText(R.id.item_message_date, date)
                .addOnClickListener(R.id.item_message_main_layout)
                .addOnClickListener(R.id.item_message_delete)
                .addOnClickListener(R.id.item_message_top);
    }
}
