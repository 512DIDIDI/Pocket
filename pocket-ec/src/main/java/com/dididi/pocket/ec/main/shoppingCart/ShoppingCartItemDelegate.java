package com.dididi.pocket.ec.main.shoppingCart;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.dididi.pocket.ec.R;
import com.dididi.pocket.ec.R2;
import com.dididi.pocket.ec.main.shoppingCart.Listener.OnGoodsPriceListener;
import com.dididi.pocket.ec.main.shoppingCart.adapter.ShopCartAdapter;
import com.dididi.pocket.core.entity.Goods;
import com.dididi.pocket.core.delegates.bottom.BottomItemDelegate;
import com.mikepenz.iconics.view.IconicsTextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created by dididi
 * on 24/07/2018 .
 */

public class ShoppingCartItemDelegate extends BottomItemDelegate implements View.OnClickListener {

    @BindView(R2.id.shop_cart_item_recycler_view)
    RecyclerView mRecyclerView = null;
    @BindView(R2.id.shop_cart_item_all_selected)
    IconicsTextView mAllSelected = null;
    @BindView(R2.id.shop_cart_item_all_selected_text)
    AppCompatTextView mAllSelectedText = null;
    @BindView(R2.id.shop_cart_item_all_price)
    AppCompatTextView mAllPrice = null;
    @BindView(R2.id.shop_cart_item_compute_price)
    AppCompatButton mComputePrice = null;

    @OnClick({R2.id.shop_cart_item_compute_price,R2.id.shop_cart_item_compute_price_ripple})
    void clickComputePrice(){
        //结算
        Toast.makeText(getContext(), "共计" + mTotalPrice + "元",
                Toast.LENGTH_SHORT).show();
    }

    private ShopCartAdapter mAdapter = null;
    private List<Goods> mGoodsList = new ArrayList<>();
    private float mTotalPrice = 0;
    private boolean isAllSelected = false;

    @Override
    public Object setLayout() {
        return R.layout.delegate_shoppingcart_shopcart;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        initGoods();
        //设置RecyclerView布局方式
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext());
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
                    computeAllPrice();
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
                    if (--count == 0) {
                        //如果当前count为1时,再减少应该取消勾选
                        mGoodsList.get(position).setGoodsSelected(false);
                        mGoodsList.get(position).setShopSelected(false);
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
        //全选按钮
        mAllSelected.setOnClickListener(this);
        mAllSelectedText.setOnClickListener(this);

        mRecyclerView.setAdapter(mAdapter);
    }

    private void initGoods() {
        for (int i = 0; i < 3; i++) {
            Goods cat = new Goods()
                    .setShopId(1)
                    .setShopName("我的店铺名很长价格很贵")
                    .setGoodsImg(String.valueOf(R.drawable.cat))
                    .setGoodsName("我是一只大猫")
                    .setGoodsStyle("橘喵")
                    .setGoodsPrice(233)
                    .setGoodsCount(1);
            Goods guitar = new Goods()
                    .setShopId(2)
                    .setShopName("我也是一家店铺")
                    .setGoodsImg(String.valueOf(R.drawable.guitar))
                    .setGoodsName("我是一把小吉他")
                    .setGoodsStyle("大吉它")
                    .setGoodsPrice(666)
                    .setGoodsCount(2);
            if (i != 0) {
                cat.setFirst(false);
                guitar.setFirst(false);
            }
            mGoodsList.add(cat);
            mGoodsList.add(guitar);
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
        //改变文字
        mAllPrice.setText(R.string.yuan);
        mAllPrice.append(String.valueOf(mTotalPrice));
    }

    @Override
    public void onClick(View view) {
        if (view == mAllSelected || view == mAllSelectedText) {
            //全选按钮
            //TODO:这里计算总价逻辑可能不是最优化的...
            for (int i = 0; i < mGoodsList.size(); i++) {
                if (mGoodsList.get(i).isFirst()) {
                    //如果是第一个商品,则商店商品勾选状态与全选按钮一致
                    mGoodsList.get(i).setShopSelected(!isAllSelected);
                    mGoodsList.get(i).setGoodsSelected(!isAllSelected);
                    for (int j = i; j < mGoodsList.size(); j++) {
                        if (mGoodsList.get(i).getShopId() == mGoodsList.get(j).getShopId()) {
                            //同一家店铺的商品也需勾选状态
                            mGoodsList.get(j).setGoodsSelected(!isAllSelected);
                        }
                    }
                }
            }
            //将全选状态更改
            isAllSelected = !isAllSelected;
            if (isAllSelected) {
                //更改图标样式
                mAllSelected.setText(R.string.faw_check_circle);
            } else {
                mAllSelected.setText(R.string.faw_circle);
            }
            mAdapter.notifyDataSetChanged();
            //重新计算价格
            computeAllPrice();
        }
    }
}
