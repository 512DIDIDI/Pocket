package com.dididi.pocket.ec.main.mall.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.dididi.pocket.ec.R;
import com.dididi.pocket.core.entity.News;
import com.dididi.pocket.core.ui.GlideApp;

import java.util.List;

/**
 * @author dididi
 * @since 20/09/2018
 * @describe 首页发现页面RecyclerView适配器
 */

public class NewsAdapter extends BaseQuickAdapter<News,BaseViewHolder> {

    public NewsAdapter(int layoutResId, @Nullable List<News> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, News item) {
        //todo:先写死load内传入的参数 id
        GlideApp.with(mContext)
                .load(Integer.parseInt(item.getAvatar()))
                .into((ImageView) helper.getView(R.id.item_home_news_head));
        helper.setText(R.id.item_home_news_name,item.getUserName())
                .setText(R.id.item_home_news_content,item.getContent())
                .setText(R.id.item_home_news_date,item.getDate())
                .addOnClickListener(R.id.item_home_news_head)
                .addOnClickListener(R.id.item_home_news_name)
                .addOnClickListener(R.id.item_home_news_comment);
        initImageGroup(helper,item);
    }

    /**
     * 初始化imageGroup
     */
    private void initImageGroup(BaseViewHolder helper,News item){
        GlideApp.with(mContext)
                .load(item.getImageGroup1Img1())
                .into((ImageView) helper.getView(R.id.item_home_news_image_group1_1));
        GlideApp.with(mContext)
                .load(item.getImageGroup1Img2())
                .into((ImageView) helper.getView(R.id.item_home_news_image_group1_2));
        GlideApp.with(mContext)
                .load(item.getImageGroup1Img3())
                .into((ImageView) helper.getView(R.id.item_home_news_image_group1_3));
        GlideApp.with(mContext)
                .load(item.getImageGroup2Img1())
                .into((ImageView) helper.getView(R.id.item_home_news_image_group2_1));
        GlideApp.with(mContext)
                .load(item.getImageGroup2Img2())
                .into((ImageView) helper.getView(R.id.item_home_news_image_group2_2));
        GlideApp.with(mContext)
                .load(item.getImageGroup2Img3())
                .into((ImageView) helper.getView(R.id.item_home_news_image_group2_3));
        GlideApp.with(mContext)
                .load(item.getImageGroup3Img1())
                .into((ImageView) helper.getView(R.id.item_home_news_image_group3_1));
        GlideApp.with(mContext)
                .load(item.getImageGroup3Img2())
                .into((ImageView) helper.getView(R.id.item_home_news_image_group3_2));
        GlideApp.with(mContext)
                .load(item.getImageGroup3Img3())
                .into((ImageView) helper.getView(R.id.item_home_news_image_group3_3));
    }
}
