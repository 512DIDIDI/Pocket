package com.dididi.pocket.ec.main.shoppingcart.adapter;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseSectionQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.dididi.pocket.core.entity.Shop;
import com.dididi.pocket.core.ui.GlideApp;
import com.dididi.pocket.ec.R;

import java.util.List;

/**
 * @author dididi
 * @describe 商品购物车适配器
 * @since 11/10/2018
 */

public class ShopCartAdapter extends BaseSectionQuickAdapter<Shop,BaseViewHolder> {

    public ShopCartAdapter(int layoutResId, int sectionHeadResId, List<Shop> data) {
        super(layoutResId, sectionHeadResId, data);
    }

    @Override
    protected void convertHead(BaseViewHolder helper, Shop item) {
        helper.setText(R.id.item_shop_cart_shop_name,item.header)
                .addOnClickListener(R.id.item_shop_cart_shop_name)
                .addOnClickListener(R.id.item_shop_cart_shop_chat);
    }

    @Override
    protected void convert(BaseViewHolder helper, Shop item) {
        helper.setText(R.id.item_goods_cart_goods_name,item.t.getShopName())
                .setText(R.id.item_goods_cart_goods_price,String.valueOf(item.t.getGoodsPrice()))
                .setText(R.id.item_goods_cart_goods_style,item.t.getGoodsStyle())
                .setText(R.id.item_goods_cart_goods_number,String.valueOf(item.t.getGoodsCount()));
        GlideApp.with(mContext)
                .load(Integer.parseInt(item.t.getGoodsImg()))
                .into((ImageView) helper.getView(R.id.item_goods_cart_goods_img));
    }
}
