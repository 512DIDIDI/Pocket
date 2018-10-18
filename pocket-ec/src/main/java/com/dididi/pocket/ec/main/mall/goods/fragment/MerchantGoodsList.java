package com.dididi.pocket.ec.main.mall.goods.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.dididi.pocket.core.delegates.PocketDelegate;
import com.dididi.pocket.core.entity.Shop;
import com.dididi.pocket.ec.R;
import com.dididi.pocket.ec.R2;
import com.dididi.pocket.ec.main.mall.goods.adapter.MerchantAdapter;
import com.dididi.pocket.ec.main.mall.goods.details.MerchantPageDelegate;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


/**
 * @author dididi
 * @describe 商家商品页面
 * @since 17/10/2018
 */

public class MerchantGoodsList extends PocketDelegate
        implements BaseQuickAdapter.OnItemClickListener {

    private List<Shop> mShopList = new ArrayList<>();
    private MerchantAdapter mAdapter = null;

    @BindView(R2.id.item_mall_goods_merchant_recycler_view)
    RecyclerView mRecyclerView = null;

    @Override
    public Object setLayout() {
        return R.layout.item_mall_goods_merchant;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        initShop();
        LinearLayoutManager manager = new LinearLayoutManager(this.getContext());
        mRecyclerView.setLayoutManager(manager);
        mAdapter = new MerchantAdapter(R.layout.item_mall_shop_list, mShopList);
        mAdapter.setOnItemClickListener(this);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        Shop shop = (Shop) adapter.getItem(position);
        if (shop == null) {
            throw new RuntimeException("shop can not be null");
        }
        getParentDelegate().getSupportDelegate().start(MerchantPageDelegate.getStartShop(shop.getShopId()));
    }

    private void initShop() {
        Shop shop1 = new Shop(true, "我是一家店铺")
                .setShopId(1)
                .setShopImg(String.valueOf(R.mipmap.banner_01))
                .setEvaluate(98f)
                .setSales(999)
                .setMinPrice(233)
                .setMaxPrice(666);
        Shop shop2 = new Shop(true, "试试左侧滑返回")
                .setShopId(2)
                .setShopImg(String.valueOf(R.mipmap.banner_02))
                .setEvaluate(96f)
                .setSales(777)
                .setMinPrice(111)
                .setMaxPrice(555);
        Shop shop3 = new Shop(true, "我还是一家店铺")
                .setShopId(3)
                .setShopImg(String.valueOf(R.mipmap.banner_03))
                .setEvaluate(97f)
                .setSales(1001)
                .setMinPrice(1)
                .setMaxPrice(1024);
        for (int i = 0; i < 3; i++) {
            mShopList.add(shop1);
            mShopList.add(shop2);
            mShopList.add(shop3);
        }
    }
}
