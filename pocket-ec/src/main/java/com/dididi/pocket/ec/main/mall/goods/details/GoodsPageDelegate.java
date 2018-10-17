package com.dididi.pocket.ec.main.mall.goods.details;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.dididi.pocket.core.delegates.PocketDelegate;
import com.dididi.pocket.core.entity.Goods;
import com.dididi.pocket.ec.R;

/**
 * @author dididi
 * @describe 商品详情页面
 * @since 17/10/2018
 */

public class GoodsPageDelegate extends PocketDelegate {

    /**
     * 私有化构造函数，防止从外部实例化而导致没有传入应该传入的参数
     */
    @SuppressLint("ValidFragment")
    private GoodsPageDelegate(){ }

    @Override
    public Object setLayout() {
        return R.layout.delegate_mall_goods_details_goods;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {

    }

    @Override
    public void setSwipeBackEnable(boolean enable) {
        super.setSwipeBackEnable(true);
    }

    /**
     * 外部通过此方法来获取实例，
     * @param goods 传入的goods信息
     * @return 包装后的GoodsPageDelegate
     */
    public static GoodsPageDelegate getStartGoods(Goods goods){
        GoodsPageDelegate goodsPageDelegate = new GoodsPageDelegate();
        Bundle bundle = new Bundle();
        bundle.putParcelable("goods",goods);
        goodsPageDelegate.setArguments(bundle);
        return goodsPageDelegate;
    }
}
