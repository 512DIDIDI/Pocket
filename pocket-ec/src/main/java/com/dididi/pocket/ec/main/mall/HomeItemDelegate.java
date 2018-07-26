package com.dididi.pocket.ec.main.mall;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.view.View;
import android.widget.ScrollView;
import android.widget.Toast;

import com.dididi.pocket.ec.R;
import com.dididi.pocket.ec.R2;
import com.dididi.pocket.ec.item.SearchBarItem;
import com.dididi.pocket.ec.main.mall.list.FakeImageList;
import com.dididi.pocket_core.delegates.bottom.BottomItemDelegate;
import com.dididi.pocket_core.ui.SwipeRefreshLayout.PocketSwipeRefreshLayout;
import com.dididi.pocket_core.ui.banner.GlideImageLoader;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;


import butterknife.BindView;

/**
 * Created by dididi
 * on 24/07/2018 .
 */

public class HomeItemDelegate extends BottomItemDelegate
        implements NavigationView.OnNavigationItemSelectedListener,
        View.OnClickListener{

    //TODO:这里有个bug OnKeyListener与OnTouchListener貌似有冲突,在home页面双击退出失效.
    //TODO:偶偶会出现fragment重叠的bug

    @BindView(R2.id.home_item_searchBar)
    SearchBarItem mSearchBarItem = null;
    @BindView(R2.id.home_item_nav)
    NavigationView mNav = null;
    @BindView(R2.id.home_item_drawer)
    DrawerLayout mDrawer = null;
    @BindView(R2.id.home_item_banner)
    Banner mBanner = null;
    @BindView(R2.id.home_item_scroll_view)
    ScrollView mScrollView = null;
    @BindView(R2.id.home_item_swipe_refresh)
    PocketSwipeRefreshLayout mRefresh = null;

    private FakeImageList mFakeImages = new FakeImageList();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    //setLayout().onBindView()在onCreateView()方法中.
    public Object setLayout() {
        return R.layout.delegate_mall_home;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        //设置nav默认选中item
        mNav.setCheckedItem(R.id.home_item_nav_menu_discover);
        //设置刷新样式
        mRefresh.setColorSchemeColors(getResources().getColor(R.color.textColorDark));
        //初始化banner
        initBanner();
        //设置左侧图标样式
        mSearchBarItem.setLeftIcon("{faw-bars}");
        //nav item点击事件监听
        mNav.setNavigationItemSelectedListener(this);
        //toolbar左侧图标点击事件监听
        mSearchBarItem.setLeftIconListener(this);
        //toolbar搜索按钮点击事件监听
        mSearchBarItem.setSearchIconListener(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        //开启banner自动播放
        mBanner.startAutoPlay();
    }

    @Override
    public void onStop() {
        super.onStop();
        //暂停banner自动播放
        mBanner.stopAutoPlay();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    //设置navigationItem监听事件
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int itemId = menuItem.getItemId();
        if (itemId == R.id.home_item_nav_menu_discover) {

        } else if (itemId == R.id.home_item_nav_menu_collect) {

        } else if (itemId == R.id.home_item_nav_menu_attention) {

        } else if (itemId == R.id.home_item_nav_menu_friends) {

        } else if (itemId == R.id.home_item_nav_menu_account) {

        } else if (itemId == R.id.home_item_nav_menu_exit) {

        } else if (itemId == R.id.home_item_nav_menu_setting) {

        } else if (itemId == R.id.home_item_nav_menu_help) {

        }
        return true;
    }

    //设置banner
    private void initBanner() {
        mBanner.setBannerAnimation(Transformer.Default)
                .setBannerStyle(BannerConfig.CIRCLE_INDICATOR)
                .setIndicatorGravity(BannerConfig.CENTER)
                .setDelayTime(1500)
                .setImageLoader(new GlideImageLoader())
                .setImages(mFakeImages.getImages())
                .start();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == mSearchBarItem.getLeftIconId()) {
            mSearchBarItem.setLeftIconListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //开启滑动菜单
                    mDrawer.openDrawer(GravityCompat.START);
                }
            });
        } else if (view.getId() == mSearchBarItem.getSearchIconId()) {
            mSearchBarItem.setSearchIconListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getContext(), "click search", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
