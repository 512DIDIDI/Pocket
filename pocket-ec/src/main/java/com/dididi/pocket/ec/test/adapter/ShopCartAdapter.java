package com.dididi.pocket.ec.test.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseSectionQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.dididi.pocket.core.ui.GlideApp;
import com.dididi.pocket.ec.R;
import com.dididi.pocket.ec.test.entity.Shop;

import java.util.List;

/**
 * @author dididi
 * @describe
 * @since 27/09/2018
 */

public class ShopCartAdapter extends BaseSectionQuickAdapter<Shop,BaseViewHolder> {

    public ShopCartAdapter(int layoutResId, int sectionHeadResId, List<Shop> data) {
        super(layoutResId, sectionHeadResId, data);
    }

    @Override
    protected void convertHead(BaseViewHolder helper, Shop item) {
        helper.setText(R.id.test_shop_card_shop_name,item.header);
    }

    @Override
    protected void convert(BaseViewHolder helper, Shop item) {
        helper.setText(R.id.test_goods_card_goods_name,item.t.getGoodsName())
                .setText(R.id.test_goods_card_goods_price,String.valueOf(item.t.getGoodsPrice()))
                .setText(R.id.test_goods_card_goods_style,item.t.getGoodsStyle())
                .setText(R.id.test_goods_card_goods_number,String.valueOf(item.t.getGoodsCount()));
        GlideApp.with(mContext)
                .load(Integer.parseInt(item.t.getGoodsImg()))
                .into((ImageView) helper.getView(R.id.test_goods_card_goods_img));
    }
}
