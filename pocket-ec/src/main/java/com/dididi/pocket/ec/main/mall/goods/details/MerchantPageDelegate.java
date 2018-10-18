package com.dididi.pocket.ec.main.mall.goods.details;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.dididi.pocket.core.delegates.PocketDelegate;
import com.dididi.pocket.core.entity.Goods;
import com.dididi.pocket.core.ui.item.SearchBarItem;
import com.dididi.pocket.ec.R;
import com.dididi.pocket.ec.R2;
import com.dididi.pocket.ec.main.mall.goods.details.adapter.MerchantPageAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;


/**
 * @author dididi
 * @describe 商家详情页面
 * @since 17/10/2018
 */

public class MerchantPageDelegate extends PocketDelegate {

    @BindView(R2.id.delegate_mall_goods_details_merchant_recycler_view)
    RecyclerView mRecyclerView = null;
    @BindView(R2.id.delegate_mall_goods_details_merchant_avatar)
    CircleImageView mAvatar = null;
    @BindView(R2.id.delegate_mall_goods_details_merchant_name)
    AppCompatTextView mName = null;
    @BindView(R2.id.delegate_mall_goods_details_merchant_fans_num)
    AppCompatTextView mFans = null;
    @BindView(R2.id.delegate_mall_goods_details_merchant_attention_num)
    AppCompatTextView mAttention = null;
    @BindView(R2.id.delegate_mall_goods_details_merchant_product_num)
    AppCompatTextView mProduct = null;
    @BindView(R2.id.delegate_mall_goods_details_merchant_searchbar)
    SearchBarItem mSearchBar = null;

    private MerchantPageAdapter mAdapter = null;
    private List<Goods> goodsList = new ArrayList<>();

    /**
     * 私有化构造函数，防止从外部实例化而导致没有传入应该传入的参数
     */
    @SuppressLint("ValidFragment")
    private MerchantPageDelegate() {
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_mall_goods_details_merchant;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        initGoods();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        mAvatar.setImageResource(R.mipmap.avatarman01);
        mName.setText("张三");
        mAttention.setText(String.valueOf(233));
        mFans.setText(String.valueOf(666));
        mProduct.setText(String.valueOf(6));
        mSearchBar.setLeftIcon("{faw-chevron-left}");
        mSearchBar.setLeftIconListener(v -> getSupportDelegate().pop());
        mRecyclerView.setLayoutManager(gridLayoutManager);
        mAdapter = new MerchantPageAdapter(R.layout.item_mall_goods_details_merchant, goodsList);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void setSwipeBackEnable(boolean enable) {
        super.setSwipeBackEnable(true);
    }

    /**
     * 外部通过此方法来获取实例，
     *
     * @param shopId 传入的shopId信息
     * @return 包装后的MerchantPageDelegate
     */
    public static MerchantPageDelegate getStartShop(long shopId) {
        MerchantPageDelegate merchantPageDelegate = new MerchantPageDelegate();
        Bundle bundle = new Bundle();
        bundle.putLong("shopId", shopId);
        merchantPageDelegate.setArguments(bundle);
        return merchantPageDelegate;
    }

    private void initGoods() {
        Goods cat = new Goods()
                .setGoodsImg(String.valueOf(R.mipmap.banner_04))
                .setGoodsName("试试点击全选按钮");
        Goods cat2 = new Goods()
                .setGoodsImg(String.valueOf(R.mipmap.banner_02))
                .setGoodsName("试试向左滑动");
        Goods guitar = new Goods()
                .setGoodsImg(String.valueOf(R.mipmap.banner_01))
                .setGoodsName("试试点击删除商品");
        Goods guitar2 = new Goods()
                .setGoodsImg(String.valueOf(R.mipmap.banner_02))
                .setGoodsName("试试减少增加商品");
        Goods flower = new Goods()
                .setGoodsImg(String.valueOf(R.mipmap.banner_05))
                .setGoodsName("试试点击与店主聊天icon");
        Goods flower2 = new Goods()
                .setGoodsImg(String.valueOf(R.mipmap.banner_03))
                .setGoodsName("试试点击toolbar清空购物车");
        goodsList.add(cat);
        goodsList.add(cat2);
        goodsList.add(guitar);
        goodsList.add(guitar2);
        goodsList.add(flower);
        goodsList.add(flower2);
    }
}
