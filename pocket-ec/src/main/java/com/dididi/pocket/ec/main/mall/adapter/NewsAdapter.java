package com.dididi.pocket.ec.main.mall.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.dididi.pocket.ec.R;
import com.dididi.pocket.ec.main.mall.entity.News;
import com.dididi.pocket_core.ui.GlideApp;
import com.mikepenz.iconics.view.IconicsTextView;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * Created by dididi
 * on 28/07/2018 .
 */

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    private Context mContext;
    private List<News> mNews;

    public NewsAdapter(List<News> news) {
        this.mNews = news;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (mContext == null) {
            //为空重新获取context对象
            mContext = viewGroup.getContext();
        }
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.item_home_news, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
        News news = mNews.get(i);
        viewHolder.mHead.setImageResource(news.getHeadId());
        viewHolder.mName.setText(news.getUserName());
        viewHolder.mContent.setText(news.getContent());
        loadImgWithGlide(mContext, news.getImageGroup1_1(), viewHolder.mImageGroup1_image1);
        loadImgWithGlide(mContext, news.getImageGroup2_1(), viewHolder.mImageGroup2_image1);
        loadImgWithGlide(mContext, news.getImageGroup3_1(), viewHolder.mImageGroup3_image1);
        loadImgWithGlide(mContext, news.getImageGroup1_2(), viewHolder.mImageGroup1_image2);
        loadImgWithGlide(mContext, news.getImageGroup2_2(), viewHolder.mImageGroup2_image2);
        loadImgWithGlide(mContext, news.getImageGroup3_2(), viewHolder.mImageGroup3_image2);
        loadImgWithGlide(mContext, news.getImageGroup1_3(), viewHolder.mImageGroup1_image3);
        loadImgWithGlide(mContext, news.getImageGroup2_3(), viewHolder.mImageGroup2_image3);
        loadImgWithGlide(mContext, news.getImageGroup3_3(), viewHolder.mImageGroup3_image3);
        viewHolder.mDate.setText(news.getDate());
        //点击头像
        viewHolder.mHead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = viewHolder.getAdapterPosition();
                News news = mNews.get(position);
                Toast.makeText(mContext, "你点击了" + news.getUserName() + "头像",
                        Toast.LENGTH_SHORT).show();
            }
        });
        //点击名字
        viewHolder.mName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = viewHolder.getAdapterPosition();
                News news = mNews.get(position);
                Toast.makeText(mContext, "你点击了" + news.getUserName() + "名字",
                        Toast.LENGTH_SHORT).show();
            }
        });
        //点击评论
        viewHolder.mComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, "点击评论", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mNews.size();
    }

    private void loadImgWithGlide(Context context, Integer id, ImageView view) {
        if (id != 0) {
            GlideApp.with(context)
                    .load(id)
                    .into(view);
        }
    }

    //获取控件实例
    static class ViewHolder extends RecyclerView.ViewHolder {

        CircleImageView mHead;
        AppCompatTextView mName;
        AppCompatTextView mContent;
        RelativeLayout mImageGroup;
        LinearLayoutCompat mImageGroup1;
        AppCompatImageView mImageGroup1_image1;
        AppCompatImageView mImageGroup1_image2;
        AppCompatImageView mImageGroup1_image3;
        LinearLayoutCompat mImageGroup2;
        AppCompatImageView mImageGroup2_image1;
        AppCompatImageView mImageGroup2_image2;
        AppCompatImageView mImageGroup2_image3;
        LinearLayoutCompat mImageGroup3;
        AppCompatImageView mImageGroup3_image1;
        AppCompatImageView mImageGroup3_image2;
        AppCompatImageView mImageGroup3_image3;
        AppCompatTextView mDate;
        IconicsTextView mComment;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            mHead = itemView.findViewById(R.id.item_home_news_head);
            mName = itemView.findViewById(R.id.item_home_news_name);
            mContent = itemView.findViewById(R.id.item_home_news_content);
            mImageGroup = itemView.findViewById(R.id.item_home_news_image_group);
            mImageGroup1 = itemView.findViewById(R.id.item_home_news_image_group1);
            mImageGroup1_image1 = itemView.findViewById(R.id.item_home_news_image_group1_1);
            mImageGroup1_image2 = itemView.findViewById(R.id.item_home_news_image_group1_2);
            mImageGroup1_image3 = itemView.findViewById(R.id.item_home_news_image_group1_3);
            mImageGroup2 = itemView.findViewById(R.id.item_home_news_image_group2);
            mImageGroup2_image1 = itemView.findViewById(R.id.item_home_news_image_group2_1);
            mImageGroup2_image2 = itemView.findViewById(R.id.item_home_news_image_group2_2);
            mImageGroup2_image3 = itemView.findViewById(R.id.item_home_news_image_group2_3);
            mImageGroup3 = itemView.findViewById(R.id.item_home_news_image_group3);
            mImageGroup3_image1 = itemView.findViewById(R.id.item_home_news_image_group3_1);
            mImageGroup3_image2 = itemView.findViewById(R.id.item_home_news_image_group3_2);
            mImageGroup3_image3 = itemView.findViewById(R.id.item_home_news_image_group3_3);
            mDate = itemView.findViewById(R.id.item_home_news_date);
            mComment = itemView.findViewById(R.id.item_home_news_comment);
        }
    }
}
