package com.dididi.pocket.ec.main.shoppingcart.adapter;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseSectionQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.dididi.pocket.core.entity.Shop;
import com.dididi.pocket.core.ui.GlideApp;
import com.dididi.pocket.ec.R;

import java.math.BigDecimal;
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
        helper.setText(R.id.item_shopcart_shop_name,item.header)
                .addOnClickListener(R.id.item_shopcart_shop_select)
                .addOnClickListener(R.id.item_shopcart_shop_name)
                .addOnClickListener(R.id.item_shopcart_shop_chat);
        //根据店铺选择状态的不同来决定显示什么图标
        if (item.isShopSelected()){
            helper.setText(R.id.item_shopcart_shop_select,R.string.faw_check_circle);
        }else{
            helper.setText(R.id.item_shopcart_shop_select,R.string.faw_circle);
        }
    }

    @Override
    protected void convert(BaseViewHolder helper, Shop item) {
        helper.setText(R.id.item_shopcart_goods_name,item.t.getGoodsName())
                .setText(R.id.item_shopcart_goods_price,
                        new BigDecimal(item.t.getGoodsPrice()).stripTrailingZeros().toPlainString())
                .setText(R.id.item_shopcart_goods_style,item.t.getGoodsStyle())
                .setText(R.id.item_shopcart_goods_number,String.valueOf(item.t.getGoodsCount()))
                .addOnClickListener(R.id.item_shopcart_goods_decrease)
                .addOnClickListener(R.id.item_shopcart_goods_increase)
                .addOnClickListener(R.id.item_shopcart_goods_select)
                .addOnClickListener(R.id.item_shopcart_goods_style)
                .addOnClickListener(R.id.item_shopcart_goods_delete)
                .addOnClickListener(R.id.item_shopcart_goods_collect)
                .addOnClickListener(R.id.item_shopcart_goods_number);
        //根据商品选择状态的不同来决定图标
        if (item.t.isGoodsSelected()){
            helper.setText(R.id.item_shopcart_goods_select,R.string.faw_check_circle);
        }else {
            helper.setText(R.id.item_shopcart_goods_select,R.string.faw_circle);
        }
        GlideApp.with(mContext)
                .load(Integer.parseInt(item.t.getGoodsImg()))
                .into((ImageView) helper.getView(R.id.item_shopcart_goods_img));
    }
}
