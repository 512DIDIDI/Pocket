package com.dididi.pocket.ec.test;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.dididi.pocket.core.delegates.PocketDelegate;
import com.dididi.pocket.ec.R;
import com.dididi.pocket.ec.R2;
import com.dididi.pocket.ec.test.adapter.ShopCartAdapter;
import com.dididi.pocket.ec.test.entity.Goods;
import com.dididi.pocket.ec.test.entity.Shop;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author dididi
 * @describe 测试布局
 * @since 25/09/2018
 */

public class TestDelegate extends PocketDelegate {

    @BindView(R2.id.delegate_test_recyclerView)
    RecyclerView mRecyclerView = null;

    private List<Shop> shopList = new ArrayList<>();

    @Override
    public Object setLayout() {
        return R.layout.delegate_test;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        initData();
        ShopCartAdapter mAdapter =
                new ShopCartAdapter(R.layout.test_goods_card, R.layout.test_shop_card, shopList);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void setSwipeBackEnable(boolean enable) {
        super.setSwipeBackEnable(true);
    }

    private void initData() {
        Goods cat = new Goods()
                .setShopId(1)
                .setShopName("我的店铺名很长价格很贵")
                .setGoodsImg(String.valueOf(R.drawable.cat))
                .setGoodsName("我是一只大猫")
                .setGoodsStyle("橘喵")
                .setGoodsPrice(233)
                .setGoodsCount(1);
        Goods cat2 = new Goods()
                .setShopId(1)
                .setShopName("我的店铺名很长价格很贵")
                .setGoodsImg(String.valueOf(R.drawable.cat))
                .setGoodsName("我是一只假黑猫")
                .setGoodsStyle("黑猫")
                .setGoodsPrice(100)
                .setGoodsCount(1);
        Goods guitar = new Goods()
                .setShopId(2)
                .setShopName("我也是一家店铺")
                .setGoodsImg(String.valueOf(R.drawable.guitar))
                .setGoodsName("我是一把小吉他")
                .setGoodsStyle("大吉它")
                .setGoodsPrice(666)
                .setGoodsCount(2);
        Goods guitar2 = new Goods()
                .setShopId(2)
                .setShopName("我也是一家店铺")
                .setGoodsImg(String.valueOf(R.drawable.guitar))
                .setGoodsName("我是一把大吉他")
                .setGoodsStyle("真的大吉它")
                .setGoodsPrice(555)
                .setGoodsCount(2);
        shopList.add(new Shop(true, "我的店铺名很长价格很贵"));
        shopList.add(new Shop(cat));
        shopList.add(new Shop(cat2));
        shopList.add(new Shop(true, "我也是一家店铺"));
        shopList.add(new Shop(guitar));
        shopList.add(new Shop(guitar2));
    }
}
