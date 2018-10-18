package com.dididi.pocket.ec.main.mall.goods.details.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.dididi.pocket.core.entity.Goods;
import com.dididi.pocket.core.entity.Shop;
import com.dididi.pocket.core.ui.GlideApp;
import com.dididi.pocket.ec.R;

import java.util.List;

/**
 * @author dididi(叶超)
 * @email yc512yc@163.com
 * @describe
 * @since 18/10/2018
 */
public class MerchantPageAdapter extends BaseQuickAdapter<Goods, BaseViewHolder> {

    public MerchantPageAdapter(int layoutResId, @Nullable List<Goods> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Goods item) {
        GlideApp.with(mContext)
                .load(Integer.parseInt(item.getGoodsImg()))
                .into((ImageView) helper.getView(R.id.item_mall_goods_details_merchant_img));
        helper.setText(R.id.item_mall_goods_details_merchant_content, item.getGoodsName());
    }
}
