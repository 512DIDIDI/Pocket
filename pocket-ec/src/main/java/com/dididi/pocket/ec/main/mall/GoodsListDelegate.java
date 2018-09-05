package com.dididi.pocket.ec.main.mall;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.dididi.pocket.ec.R;
import com.dididi.pocket.ec.R2;
import com.dididi.pocket.ec.item.SearchBarItem;
import com.dididi.pocket.ec.main.mall.adapter.GoodsAdapter;
import com.dididi.pocket_core.Entity.Goods;
import com.dididi.pocket_core.delegates.PocketDelegate;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created by dididi
 * on 05/08/2018 .
 */

public class GoodsListDelegate extends PocketDelegate implements View.OnClickListener {

    @BindView(R2.id.goods_delegate_goodsList)
    RecyclerView mRecyclerView = null;
    @BindView(R2.id.goods_list_searchBar)
    SearchBarItem mSearchBar = null;

    private List<Goods> mGoodsList = new ArrayList<>();
    private GoodsAdapter mAdapter = null;

    @Override
    public Object setLayout() {
        return R.layout.delegate_mall_goods_list;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        initGoods();
        LinearLayoutManager manager = new LinearLayoutManager(this.getContext());
        mRecyclerView.setLayoutManager(manager);
        mAdapter = new GoodsAdapter(mGoodsList);
        mRecyclerView.setAdapter(mAdapter);
        mSearchBar.setLeftIcon("{faw-chevron-left}");
        mSearchBar.setLeftIconListener(this);
    }

    private void initGoods() {
        for (int i = 0; i < 5; i++) {
            Goods cat = new Goods()
                    .setShopId(1)
                    .setShopName("我是一家店铺")
                    .setGoodsImg(R.drawable.cat)
                    .setGoodsName("我的名字很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长")
                    .setSales(20)
                    .setGoodsStyle("橘喵")
                    .setGoodsPrice(233)
                    .setGoodsCount(1);
            Goods guitar = new Goods()
                    .setShopId(2)
                    .setShopName("我也是一家店铺也是一家店铺也是一家店铺也是一家店铺")
                    .setGoodsImg(R.drawable.guitar)
                    .setGoodsName("我的销量很高价格很贵店铺名很长")
                    .setGoodsStyle("大吉它")
                    .setSales(1111111111)
                    .setGoodsPrice(564564)
                    .setGoodsCount(2);
            if (i != 0) {
                cat.setFirst(false);
                guitar.setFirst(false);
            }
            mGoodsList.add(cat);
            mGoodsList.add(guitar);
        }
    }

    @Override
    public void setSwipeBackEnable(boolean enable) {
        super.setSwipeBackEnable(true);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == mSearchBar.getLeftIconId()) {
            getSupportDelegate().pop();
        }
    }
}
