package com.dididi.pocket.ec.main.mall.goods.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.dididi.pocket.core.entity.Shop;
import com.dididi.pocket.core.ui.GlideApp;
import com.dididi.pocket.core.ui.item.RoundRectImageView;
import com.dididi.pocket.ec.R;

import java.util.List;

/**
 * @author dididi
 * @describe 店铺页面适配器
 * @since 17/10/2018
 */

public class MerchantAdapter extends BaseQuickAdapter<Shop,BaseViewHolder> {

    public MerchantAdapter(int layoutResId, @Nullable List<Shop> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Shop item) {
        //todo:先写死load内传入的参数 id 以后再改
        GlideApp.with(mContext)
                .load(Integer.parseInt(item.getShopImg()))
                .into((ImageView) helper.getView(R.id.item_mall_shop_list_img));
        helper.setText(R.id.item_mall_shop_list_name,item.header)
                .setText(R.id.item_mall_shop_list_price_min,String.valueOf(item.getMinPrice()))
                .setText(R.id.item_mall_shop_list_price_max,String.valueOf(item.getMaxPrice()))
                .setText(R.id.item_mall_shop_list_sales,String.valueOf(item.getSales()))
                .setText(R.id.item_mall_shop_list_evaluate,String.valueOf(item.getEvaluate()));
    }
}
