package com.dididi.pocket.ec.main.mall.goods.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.dididi.pocket.core.entity.Goods;
import com.dididi.pocket.ec.R;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * @author dididi
 * @describe 商品列表页面商品adapter
 * @since 21/09/2018
 */

public class GoodsAdapter extends BaseQuickAdapter<Goods,BaseViewHolder> {

    public GoodsAdapter(int layoutResId, @NotNull List<Goods> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Goods item) {
        //todo:先写死load内传入的参数 id 以后再改
        Glide.with(mContext)
                .load(Integer.parseInt(item.getGoodsImg()))
                .into((ImageView) helper.getView(R.id.item_mall_goods_list_img));
        helper.setText(R.id.item_mall_goods_list_name,item.getGoodsName())
                .setText(R.id.item_mall_goods_list_price,String.valueOf(item.getGoodsPrice()))
                .setText(R.id.item_mall_goods_list_sales,String.valueOf(item.getSales()))
                .setText(R.id.item_mall_goods_list_evaluate,String.valueOf(item.getEvaluate()))
                .setText(R.id.item_mall_goods_list_shop,item.getShopName())
                .addOnClickListener(R.id.item_mall_goods_list_enter);
    }
}
