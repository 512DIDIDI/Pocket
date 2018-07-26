package com.dididi.pocket.ec.main.message.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.dididi.pocket.ec.R;
import com.dididi.pocket.ec.main.message.Entity.Message;

import java.util.List;


/**
 * Created by dididi
 * on 27/07/2018 .
 */

public class MessageListAdapter extends ArrayAdapter<Message> {

    private int mResourceId;

    public MessageListAdapter(@NonNull Context context,
                              int textViewResourceId, @NonNull List<Message> objects) {
        super(context, textViewResourceId, objects);
        mResourceId = textViewResourceId;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Message msg = getItem(position);
        View view;
        ViewHolder viewHolder;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(mResourceId, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.headImage = view.findViewById(R.id.item_message_head_img);
            viewHolder.userName = view.findViewById(R.id.item_message_user_name);
            viewHolder.msgContent = view.findViewById(R.id.item_message_content);
            viewHolder.msgDate = view.findViewById(R.id.item_message_date);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        if (msg != null){
            viewHolder.headImage.setImageResource(msg.getHeadImg());
            viewHolder.userName.setText(msg.getUserName());
            viewHolder.msgContent.setText(msg.getMsgContent());
            viewHolder.msgDate.setText(msg.getDate());
        }
        return view;
    }

    private static class ViewHolder {
        private AppCompatImageView headImage;
        private AppCompatTextView userName;
        private AppCompatTextView msgContent;
        private AppCompatTextView msgDate;
    }
}
