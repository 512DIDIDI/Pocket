package com.dididi.pocket.ec.main.mall.goods.details;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.dididi.pocket.core.delegates.PocketDelegate;
import com.dididi.pocket.ec.R;


/**
 * @author dididi
 * @describe 商家详情页面
 * @since 17/10/2018
 */

public class MerchantPageDelegate extends PocketDelegate {

    /**
     * 私有化构造函数，防止从外部实例化而导致没有传入应该传入的参数
     */
    @SuppressLint("ValidFragment")
    private MerchantPageDelegate(){ }

    @Override
    public Object setLayout() {
        return R.layout.delegate_mall_goods_details_merchant;
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
     * @param shopId 传入的shopId信息
     * @return 包装后的MerchantPageDelegate
     */
    public static MerchantPageDelegate getStartShop(long shopId){
        MerchantPageDelegate merchantPageDelegate = new MerchantPageDelegate();
        Bundle bundle = new Bundle();
        bundle.putLong("shopId", shopId);
        merchantPageDelegate.setArguments(bundle);
        return merchantPageDelegate;
    }
}