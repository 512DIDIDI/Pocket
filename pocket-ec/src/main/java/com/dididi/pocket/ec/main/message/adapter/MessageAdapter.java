package com.dididi.pocket.ec.main.message.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.dididi.pocket.ec.R;
import com.dididi.pocket.ec.main.message.entity.Message;
import com.dididi.pocket.ec.main.message.listener.PocketOnSwipeListener;
import com.dididi.pocket_core.ui.GlideApp;

import java.util.List;



/**
 * Created by dididi
 * on 27/07/2018 .
 */

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.Holder> {

    private Context mContext;
    private List<Message> mMessageList;
    private PocketOnSwipeListener mOnSwipeListener;

    public MessageAdapter(List<Message> messageList) {
        this.mMessageList = messageList;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (mContext == null) {
            mContext = viewGroup.getContext();
        }
        final View itemView = LayoutInflater.from(mContext)
                .inflate(R.layout.item_message_list, viewGroup, false);
        final Holder holder = new Holder(itemView);
        //点击主布局
        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                Toast.makeText(mContext, "向" + mMessageList.get(position).getUserName()
                        + "发起消息", Toast.LENGTH_SHORT).show();
            }
        });
        //点击置顶按钮
        holder.top.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //交由外界处理置顶
                int position = holder.getAdapterPosition();
                mOnSwipeListener.onTop(position);
            }
        });
        //点击删除按钮
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //交由外界处理删除
                int position = holder.getAdapterPosition();
                mOnSwipeListener.onDelete(position);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int i) {
        Message message = mMessageList.get(i);
        loadImgWithGlide(mContext, message.getHeadImg(), holder.headImage);
        holder.userName.setText(message.getUserName());
        holder.msgContent.setText(message.getMsgContent());
        holder.msgDate.setText(message.getDate());
    }

    @Override
    public int getItemCount() {
        return mMessageList.size();
    }

    static class Holder extends RecyclerView.ViewHolder {
        private RelativeLayout mainLayout;
        private AppCompatImageView headImage;
        private AppCompatTextView userName;
        private AppCompatTextView msgContent;
        private AppCompatTextView msgDate;
        private AppCompatButton top;
        private AppCompatButton delete;

        Holder(@NonNull View itemView) {
            super(itemView);
            headImage = itemView.findViewById(R.id.item_message_head_img);
            userName = itemView.findViewById(R.id.item_message_user_name);
            msgContent = itemView.findViewById(R.id.item_message_content);
            msgDate = itemView.findViewById(R.id.item_message_date);
            mainLayout = itemView.findViewById(R.id.item_message_main_layout);
            top = itemView.findViewById(R.id.item_message_top);
            delete = itemView.findViewById(R.id.item_message_delete);
        }
    }

    //使用glide加载图片
    private void loadImgWithGlide(Context context, Integer id, ImageView view) {
        if (id != 0) {
            GlideApp.with(context)
                    .load(id)
                    .into(view);
        }
    }

    public PocketOnSwipeListener getOnSwipeListener() {
        return mOnSwipeListener;
    }
    //监听删除置顶按钮
    public void setOnTopDelListener(PocketOnSwipeListener mOnSwipeListener) {
        this.mOnSwipeListener = mOnSwipeListener;
    }
}
