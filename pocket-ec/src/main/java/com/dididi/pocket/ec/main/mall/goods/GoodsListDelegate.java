package com.dididi.pocket.ec.main.mall.goods;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.dididi.pocket.ec.R;
import com.dididi.pocket.ec.R2;
import com.dididi.pocket.core.ui.item.SearchBarItem;
import com.dididi.pocket.core.delegates.PocketDelegate;
import com.dididi.pocket.ec.main.mall.goods.fragment.AllGoodsList;
import com.dididi.pocket.ec.main.mall.goods.fragment.BestGoodsList;
import com.dididi.pocket.ec.main.mall.goods.fragment.MerchantGoodsList;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import butterknife.BindView;


/**
 * @describe 商品页面
 * @author dididi
 * @since 05/08/2018 .
 */

public class GoodsListDelegate extends PocketDelegate
        implements View.OnClickListener {

    @BindView(R2.id.delegate_mall_goods_viewpager)
    ViewPager mViewPager = null;
    @BindView(R2.id.delegate_mall_goods_searchBar)
    SearchBarItem mSearchBar = null;
    @BindView(R2.id.delegate_mall_goods_chooseBar)
    SmartTabLayout mChooseBar = null;

    @Override
    public Object setLayout() {
        return R.layout.delegate_mall_goods_list;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        FragmentPagerItemAdapter adapter =
                new FragmentPagerItemAdapter(getChildFragmentManager(),FragmentPagerItems.with(getContext())
                        .add(R.string.all,AllGoodsList.class)
                        .add(R.string.best,BestGoodsList.class)
                        .add(R.string.store,MerchantGoodsList.class)
                        .create());
        mViewPager.setAdapter(adapter);
        mChooseBar.setViewPager(mViewPager);
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

}
