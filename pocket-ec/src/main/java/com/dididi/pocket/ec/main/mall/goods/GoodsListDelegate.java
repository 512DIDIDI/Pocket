package com.dididi.pocket.ec.main.mall.goods;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.dididi.pocket.ec.R;
import com.dididi.pocket.ec.R2;
import com.dididi.pocket.ec.item.SearchBarItem;
import com.dididi.pocket.ec.main.mall.goods.adapter.GoodsAdapter;
import com.dididi.pocket.core.entity.Goods;
import com.dididi.pocket.core.delegates.PocketDelegate;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created by dididi
 * on 05/08/2018 .
 */

public class GoodsListDelegate extends PocketDelegate
        implements View.OnClickListener, BaseQuickAdapter.OnItemChildClickListener,
        BaseQuickAdapter.OnItemClickListener {

    @BindView(R2.id.delegate_mall_goods_goodsList)
    RecyclerView mRecyclerView = null;
    @BindView(R2.id.delegate_mall_goods_searchBar)
    SearchBarItem mSearchBar = null;


    private List<Goods> mGoodsList = new ArrayList<>();
    @SuppressWarnings("FieldCanBeLocal")
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
        mAdapter = new GoodsAdapter(R.layout.item_mall_goods_list, mGoodsList);
        mAdapter.setOnItemChildClickListener(this);
        mAdapter.setOnItemClickListener(this);
        mRecyclerView.setAdapter(mAdapter);
        mSearchBar.setLeftIcon("{faw-chevron-left}");
        mSearchBar.setLeftIconListener(this);
    }

    /**
     * 设置侧滑返回
     *
     * @param enable true即为可以侧滑返回
     */
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

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        StringBuilder content = new StringBuilder();
        Goods goods = (Goods) adapter.getItem(position);
        if (goods == null) {
            throw new RuntimeException("goods can not be null!");
        }
        if (view.getId() == R.id.item_mall_goods_list_enter) {
            content.append("点击进入")
                    .append(goods.getShopName())
                    .append("商铺");
        }
        Toast.makeText(getContext(), content.toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        Goods goods = (Goods) adapter.getItem(position);
        if (goods == null) {
            throw new RuntimeException("goods can not be null");
        }
        Toast.makeText(getContext(), "点击进入" + goods.getGoodsName() + "商品页面",
                Toast.LENGTH_SHORT).show();
    }

    private void initGoods() {
        for (int i = 0; i < 5; i++) {
            Goods cat = new Goods()
                    .setShopId(1)
                    .setShopName("我是一家店铺")
                    .setGoodsImg(String.valueOf(R.drawable.cat))
                    .setGoodsName("我的名字很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长")
                    .setSales(20)
                    .setGoodsStyle("橘喵")
                    .setGoodsPrice(233)
                    .setGoodsCount(1);
            Goods guitar = new Goods()
                    .setShopId(2)
                    .setShopName("我也是一家店铺也是一家店铺也是一家店铺也是一家店铺")
                    .setGoodsImg(String.valueOf(R.drawable.guitar))
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
}
