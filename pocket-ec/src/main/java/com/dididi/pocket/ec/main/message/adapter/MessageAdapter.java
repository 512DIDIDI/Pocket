package com.dididi.pocket.ec.main.message.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.dididi.pocket.core.entity.Message;
import com.dididi.pocket.core.fakedata.FakeUser;
import com.dididi.pocket.core.ui.GlideApp;
import com.dididi.pocket.ec.R;
import com.dididi.pocket.ec.main.message.model.MessageInfo;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author dididi
 * @describe
 * @since 21/09/2018
 */

public class MessageAdapter extends BaseQuickAdapter<MessageInfo, BaseViewHolder> {

    private List<MessageInfo> conversationList;

    public MessageAdapter(int layoutResId, @Nullable List<MessageInfo> data) {
        super(layoutResId, data);
        conversationList = data;
    }

    @Override
    protected void convert(BaseViewHolder helper, MessageInfo item) {
        //todo:这里需要考虑一下load() 先写死传入id 后期再改
        GlideApp.with(mContext)
                .load(Integer.parseInt(FakeUser.getUserByName(item.getPeer()).getAvatar()))
                .into((ImageView) helper.getView(R.id.item_message_avatar));
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String date = sdf.format(new Date(item.getLastMessageTime()));
        helper.setText(R.id.item_message_name, item.getTitle())
                .setText(R.id.item_message_date, date)
                .addOnClickListener(R.id.item_message_main_layout)
                .addOnClickListener(R.id.item_message_delete)
                .addOnClickListener(R.id.item_message_top);
        if (item.getLastMessage() != null && item.getLastMessage().getExtra() != null) {
            helper.setText(R.id.item_message_content, item.getLastMessage().getExtra().toString());
        }else{
            helper.setText(R.id.item_message_content," ");
        }
    }

    public void addConversations(List<MessageInfo> conversations){
        conversationList.addAll(0,conversations);
        notifyDataSetChanged();
    }

    public void updateConversations(List<MessageInfo> conversations){
        conversationList.clear();
        conversationList.addAll(0,conversations);
        notifyDataSetChanged();
    }

}
