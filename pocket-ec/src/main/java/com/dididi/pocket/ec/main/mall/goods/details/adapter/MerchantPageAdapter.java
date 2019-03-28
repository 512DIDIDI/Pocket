package com.dididi.pocket.ec.main.mall.goods.details.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.dididi.pocket.core.entity.Goods;
import com.dididi.pocket.ec.R;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * @author dididi(叶超)
 * @email yc512yc@163.com
 * @describe
 * @since 18/10/2018
 */
public class MerchantPageAdapter extends BaseQuickAdapter<Goods, BaseViewHolder> {

    public MerchantPageAdapter(int layoutResId, @NotNull List<Goods> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Goods item) {
        Glide.with(mContext)
                .load(Integer.parseInt(item.getGoodsImg()))
                .into((ImageView) helper.getView(R.id.item_mall_goods_details_merchant_img));
        helper.setText(R.id.item_mall_goods_details_merchant_content, item.getGoodsName());
    }
}
