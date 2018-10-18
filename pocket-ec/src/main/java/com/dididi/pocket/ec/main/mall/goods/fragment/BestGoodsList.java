package com.dididi.pocket.ec.main.mall.goods.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.dididi.pocket.core.delegates.PocketDelegate;
import com.dididi.pocket.core.entity.Goods;
import com.dididi.pocket.ec.R;
import com.dididi.pocket.ec.R2;
import com.dididi.pocket.ec.main.mall.goods.adapter.GoodsAdapter;
import com.dididi.pocket.ec.main.mall.goods.details.GoodsPageDelegate;
import com.dididi.pocket.ec.main.mall.goods.details.MerchantPageDelegate;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


/**
 * @author dididi
 * @describe 精选商品页面
 * @since 17/10/2018
 */

public class BestGoodsList extends PocketDelegate
        implements BaseQuickAdapter.OnItemChildClickListener, BaseQuickAdapter.OnItemClickListener {

    @BindView(R2.id.item_mall_goods_best_recycler_view)
    RecyclerView mRecyclerView = null;

    private List<Goods> mGoodsList = new ArrayList<>();
    @SuppressWarnings("FieldCanBeLocal")
    private GoodsAdapter mAdapter = null;

    @Override
    public Object setLayout() {
        return R.layout.item_mall_goods_best;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        initGoods();
        LinearLayoutManager manager = new LinearLayoutManager(this.getContext());
        mRecyclerView.setLayoutManager(manager);
        mAdapter = new GoodsAdapter(R.layout.item_mall_goods_list, mGoodsList);
        mAdapter.setOnItemChildClickListener(this);
        mAdapter.setOnItemClickListener(this);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        Goods goods = (Goods) adapter.getItem(position);
        if (goods == null) {
            throw new RuntimeException("goods can not be null!");
        }
        if (view.getId() == R.id.item_mall_goods_list_enter) {
            getParentDelegate().getSupportDelegate().start(MerchantPageDelegate.getStartShop(goods.getShopId()));
        }
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        Goods goods = (Goods) adapter.getItem(position);
        if (goods == null) {
            throw new RuntimeException("goods can not be null");
        }
        getParentDelegate().getSupportDelegate().start(GoodsPageDelegate.getStartGoods(goods));
        Toast.makeText(getContext(), "功能尚未开发完成，请侧滑返回", Toast.LENGTH_SHORT).show();
    }

    private void initGoods() {
        for (int i = 0; i < 5; i++) {
            Goods cat = new Goods()
                    .setShopId(1)
                    .setShopName("我是一家店铺")
                    .setGoodsImg(String.valueOf(R.mipmap.banner_04))
                    .setGoodsName("我是精选的商品")
                    .setSales(20)
                    .setGoodsStyle("橘喵")
                    .setGoodsPrice(233)
                    .setGoodsCount(1);
            Goods guitar = new Goods()
                    .setShopId(2)
                    .setShopName("我也是一家店铺也是一家店铺也是一家店铺也是一家店铺")
                    .setGoodsImg(String.valueOf(R.mipmap.banner_05))
                    .setGoodsName("试试左右滑动")
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
}
