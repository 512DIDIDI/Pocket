package com.dididi.pocket.ec.main.shoppingCart;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.dididi.pocket.ec.R;
import com.dididi.pocket.ec.R2;
import com.dididi.pocket.ec.item.SearchBarItem;
import com.dididi.pocket.ec.main.shoppingCart.Listener.OnGoodsPriceListener;
import com.dididi.pocket.ec.main.shoppingCart.adapter.ShopCartAdapter;
import com.dididi.pocket.ec.main.shoppingCart.entity.Goods;
import com.dididi.pocket_core.delegates.bottom.BottomItemDelegate;
import com.mikepenz.iconics.view.IconicsTextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


/**
 * Created by dididi
 * on 24/07/2018 .
 */

public class ShoppingCartItemDelegate extends BottomItemDelegate {

    @BindView(R2.id.shop_cart_item_searchBar)
    SearchBarItem mSearchBar = null;
    @BindView(R2.id.shop_cart_item_recycler_view)
    RecyclerView mRecyclerView = null;
    @BindView(R2.id.shop_cart_item_all_selected)
    IconicsTextView mAllSelected = null;
    @BindView(R2.id.shop_cart_item_all_price)
    AppCompatTextView mAllPrice = null;

    private ShopCartAdapter mAdapter;
    private List<Goods> mGoodsList = new ArrayList<>();
    private float mTotalPrice = 0;

    @Override
    public Object setLayout() {
        return R.layout.delegate_shoppingcart_shopcart;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        mSearchBar.setLeftIconGone();
        initGoods();
        //设置RecyclerView布局方式
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(layoutManager);
        //设置适配器
        mAdapter = new ShopCartAdapter(mGoodsList);
        mAdapter.setOnGoodsPriceListener(new OnGoodsPriceListener() {
            @Override
            public void onDelete(int position) {
                if (position >= 0 && position < mGoodsList.size()) {
                    //删除对应位置的商品
                    mGoodsList.remove(position);
                    mAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onIncrease(int position) {
                //点击增加商品数量
                int count = mGoodsList.get(position).getGoodsCount();
                mGoodsList.get(position).setGoodsCount(++count);
                mAdapter.notifyDataSetChanged();
                computeAllPrice();
            }

            @Override
            public void onDecrease(int position) {
                //点击减少商品数量
                int count = mGoodsList.get(position).getGoodsCount();
                if (count != 0) {
                    if(--count == 0){
                        //如果当前count为1时,再减少应该取消勾选
                        mGoodsList.get(position).setGoodsSelected(false);
                    }
                    mGoodsList.get(position).setGoodsCount(count);
                }
                mAdapter.notifyDataSetChanged();
                computeAllPrice();
            }

            @Override
            public void onGoodsSelected(int position) {
                //重新计算价格
                computeAllPrice();
            }

            @Override
            public void onShopSelected(int position) {
                //重新计算价格
                computeAllPrice();
            }

            @Override
            public void onCollect(int position) {

            }

            @Override
            public void onChangeStyle(int position) {

            }
        });
        mRecyclerView.setAdapter(mAdapter);
    }

    private void initGoods() {
        for (int i = 0; i < 3; i++) {
            Goods cat = new Goods()
                    .setShopId(1)
                    .setShopName("我是一家店铺")
                    .setGoodsImg(R.drawable.cat)
                    .setGoodsName("我是一只大猫")
                    .setGoodsStyle("橘喵")
                    .setGoodsPrice(233)
                    .setGoodsCount(1);
            Goods guitar = new Goods()
                    .setShopId(2)
                    .setShopName("我也是一家店铺")
                    .setGoodsImg(R.drawable.guitar)
                    .setGoodsName("我是一把小吉他")
                    .setGoodsStyle("大吉它")
                    .setGoodsPrice(666)
                    .setGoodsCount(2);
            mGoodsList.add(cat);
            mGoodsList.add(guitar);
            if (i != 0) {
                mGoodsList.get(i).setFirst(false);
            }
        }
    }

    private void computeAllPrice() {
        mTotalPrice = 0;
        //计算价格
        for (int i = 0; i < mGoodsList.size(); i++) {
            if (mGoodsList.get(i).isGoodsSelected()) {
                float price = mGoodsList.get(i).getGoodsPrice();
                int count = mGoodsList.get(i).getGoodsCount();
                mTotalPrice += (price * count);
            }
        }
        mAllPrice.setText(String.valueOf(mTotalPrice));
    }
}
