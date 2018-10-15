package com.dididi.pocket.ec.main.mall;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.dididi.pocket.core.entity.User;
import com.dididi.pocket.core.fakedata.FakeUser;
import com.dididi.pocket.ec.R;
import com.dididi.pocket.ec.R2;
import com.dididi.pocket.ec.item.CircleIconItem;
import com.dididi.pocket.ec.item.SearchBarItem;
import com.dididi.pocket.ec.main.PocketBottomDelegate;
import com.dididi.pocket.ec.main.mall.adapter.NewsAdapter;
import com.dididi.pocket.core.entity.News;
import com.dididi.pocket.ec.main.mall.goods.GoodsListDelegate;
import com.dididi.pocket.ec.main.mall.list.FakeImageList;
import com.dididi.pocket.ec.sign.SignInDelegate;
import com.dididi.pocket.core.util.PocketPreferences;
import com.dididi.pocket.core.app.AccountManager;
import com.dididi.pocket.core.app.IUserChecker;
import com.dididi.pocket.core.delegates.bottom.BottomItemDelegate;
import com.dididi.pocket.core.ui.GlideApp;
import com.dididi.pocket.core.ui.PocketSwipeRefreshLayout;
import com.dididi.pocket.core.ui.banner.GlideImageLoader;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @author dididi
 * @since 24/07/2018
 * @describe 首页fragment
 */

public class HomeItemDelegate extends BottomItemDelegate
        implements NavigationView.OnNavigationItemSelectedListener,
        View.OnClickListener,BaseQuickAdapter.OnItemChildClickListener,DrawerLayout.DrawerListener{

    private static final String TAG = "HomeItemDelegate";

    @BindView(R2.id.delegate_mall_home_searchBar)
    SearchBarItem mSearchBarItem = null;
    @BindView(R2.id.delegate_mall_home_nav)
    NavigationView mNav = null;
    @BindView(R2.id.home_item_drawer)
    DrawerLayout mDrawer = null;
    @BindView(R2.id.delegate_mall_home_banner)
    Banner mBanner = null;
    @BindView(R2.id.delegate_mall_home_scroll_view)
    NestedScrollView mScrollView = null;
    @BindView(R2.id.delegate_mall_home_swipe_refresh)
    PocketSwipeRefreshLayout mRefresh = null;
    @BindView(R2.id.delegate_mall_home_discover)
    RecyclerView mDiscover = null;
    @BindView(R2.id.delegate_mall_home_photography)
    CircleIconItem mPhotography = null;
    @BindView(R2.id.delegate_mall_home_paint)
    CircleIconItem mPaint = null;
    @BindView(R2.id.delegate_mall_home_purchaseAgency)
    CircleIconItem mAgency = null;

    @OnClick(R2.id.delegate_mall_home_photography)
    public void setmPhotography() {
        getParentDelegate().getSupportDelegate().start(new GoodsListDelegate());
    }

    private FakeImageList mFakeImages = new FakeImageList();
    private List<News> mNews = new ArrayList<>();
    @SuppressWarnings("FieldCanBeLocal")
    private View mNavigationView = null;
    private AppCompatTextView mEmail = null;
    private AppCompatTextView mName = null;
    private CircleImageView mAvatar = null;

    private NewsAdapter mAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_mall_home;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        //加载navigationView布局
        mNavigationView = getLayoutInflater().inflate(R.layout.item_home_nav_header, mNav);
        mEmail = mNavigationView.findViewById(R.id.item_home_nav_header_email);
        mName = mNavigationView.findViewById(R.id.item_home_nav_header_name);
        mAvatar = mNavigationView.findViewById(R.id.item_home_nav_header_avatar);
        //设置nav默认选中item
        mNav.setCheckedItem(R.id.menu_home_item_nav_discover);
        //设置刷新样式
        mRefresh.setColorSchemeColors(getResources().getColor(R.color.textColorDark));
        mRefresh.setOnRefreshListener(this::refreshNews);
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
        //drawerLayout事件监听
        mDrawer.addDrawerListener(this);
        //初始化发现消息
        initFakeNews();
        //设置RecyclerView布局方式及加入适配器
        LinearLayoutManager layoutManager =
                new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mDiscover.setLayoutManager(layoutManager);
        mAdapter = new NewsAdapter(R.layout.item_home_news,mNews);
        mAdapter.setOnItemChildClickListener(this);
        mDiscover.setAdapter(mAdapter);
        //初始化用户信息
        initUserIfo();
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
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int itemId = menuItem.getItemId();
        if (itemId == R.id.menu_home_item_nav_discover) {
            Toast.makeText(getContext(), "点击跳转发现界面", Toast.LENGTH_SHORT).show();
        } else if (itemId == R.id.menu_home_item_nav_collect) {
            Toast.makeText(getContext(), "点击跳转我的收藏界面", Toast.LENGTH_SHORT).show();
        } else if (itemId == R.id.menu_home_item_nav_attention) {
            Toast.makeText(getContext(), "点击跳转我的关注界面", Toast.LENGTH_SHORT).show();
        } else if (itemId == R.id.menu_home_item_nav_friends) {
            Toast.makeText(getContext(), "点击跳转我的朋友界面", Toast.LENGTH_SHORT).show();
        } else if (itemId == R.id.menu_home_item_nav_account) {
            Toast.makeText(getContext(), "点击跳转账户界面", Toast.LENGTH_SHORT).show();
        } else if (itemId == R.id.menu_home_item_nav_exit) {
            //清除sp中的内容以达到退出效果
            PocketPreferences.clearPocketProfile();
            getParentDelegate().getSupportDelegate().startWithPop(new SignInDelegate());
        } else if (itemId == R.id.menu_home_item_nav_setting) {
            Toast.makeText(getContext(), "点击跳转设置界面", Toast.LENGTH_SHORT).show();
        } else if (itemId == R.id.menu_home_item_nav_help) {
            Toast.makeText(getContext(), "点击跳转帮助界面", Toast.LENGTH_SHORT).show();
        }
        return true;
    }

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
            mSearchBarItem.setLeftIconListener(view1 -> {
                //开启滑动菜单
                mDrawer.openDrawer(GravityCompat.START);

            });
        } else if (view.getId() == mSearchBarItem.getSearchIconId()) {
            mSearchBarItem.setSearchIconListener(view12 ->
                    Toast.makeText(getContext(), "click search", Toast.LENGTH_SHORT).show());
        }
    }


    private void initFakeNews() {
        mNews.clear();
        News[] news = {
                new News(FakeUser.getUser("2"), "真的累死了", "27/7/2018"),
                new News(FakeUser.getUser("3"),
                        "终于快打完了终于快打完了终于快打完了终于快打完了完了", "28/7/2018"),
                new News(FakeUser.getUser("4"), "细节需要优化一下", "26/7/2018"),
                new News(FakeUser.getUser("5"), "今天测试一下这个", "27/7/2018"),
                new News(FakeUser.getUser("6"), "明天测试一下那个", "28/7/2018"),
                new News(FakeUser.getUser("8"), "这其实是朋友圈", "26/7/2018"),
                new News(FakeUser.getUser("9"), "这里应该做成下拉刷新", "27/7/2018"),
                new News(FakeUser.getUser("10"), "以后再改吧 现在懒得写了", "28/7/2018"),
                new News(FakeUser.getUser("11"), "这其实不是很正常", "26/7/2018"),
                new News(FakeUser.getUser("12"), "这些全都是假象", "27/7/2018"),

        };
        for (int i = 0; i < 11; i++) {
            Random random = new Random();
            int index = random.nextInt(news.length);
            mNews.add(news[index]);
        }
    }

    private void refreshNews() {
        new Thread(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //切换回ui线程
            if (getActivity() != null) {
                getActivity().runOnUiThread(() -> {
                    initFakeNews();
                    mAdapter.notifyDataSetChanged();
                    mRefresh.setRefreshing(false);
                });
            }
        }).start();
    }

    /** 初始化抽屉布局用户信息 */
    private void initUserIfo() {
        AccountManager.checkAccount(new IUserChecker() {
            @Override
            public void onSignIn() {
                //如果登录，从sp中获取用户信息
                String avatar = PocketPreferences
                        .getCustomPocketProfile("userAvatar")
                        .replace("\"", "");
                String name = PocketPreferences
                        .getCustomPocketProfile("userName")
                        .replace("\"", "");
                String email = PocketPreferences
                        .getCustomPocketProfile("userEmail")
                        .replace("\"", "");
                GlideApp.with(HomeItemDelegate.this)
                        .load(avatar)
                        .into(mAvatar);
                mName.setText(name);
                mEmail.setText(email);
            }

            @Override
            public void onNotSignIn() {
                User me = FakeUser.getUser("1");
                GlideApp.with(HomeItemDelegate.this)
                        .load(Integer.parseInt(me.getAvatar()))
                        .into(mAvatar);
                mName.setText(me.getName());
                mEmail.setText(me.getEmail());
                mName.setOnClickListener(view ->
                        getParentDelegate().getSupportDelegate().startWithPop(new SignInDelegate()));
                mEmail.setOnClickListener(view ->
                        getParentDelegate().getSupportDelegate().startWithPop(new SignInDelegate()));
            }
        });
    }

    /**
     * newsAdapter的子控件点击事件
     * @param adapter newsAdapter
     * @param view 子控件
     * @param position item位置
     */
    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        StringBuilder content = null;
        News news = (News) adapter.getItem(position);
        if (null == news){
            throw new RuntimeException("news can not be null");
        }
        int id = view.getId();
        if (id == R.id.item_home_news_head){
            content = new StringBuilder();
            content.append("你点击了")
                    .append(news.getUserName())
                    .append("头像");
        }else if (id == R.id.item_home_news_name){
            content = new StringBuilder();
            content.append("你点击了")
                    .append(news.getUserName())
                    .append("名字");
        }else if (id == R.id.item_home_news_comment){
            content = new StringBuilder();
            content.append("你点击了")
                    .append(news.getUserName())
                    .append("评论");
        }
        if (content != null){
            Toast.makeText(getContext(),content.toString(),Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDrawerSlide(@NonNull View view, float v) {

    }

    @Override
    public void onDrawerOpened(@NonNull View view) {
        //隐藏bottomBar
        PocketBottomDelegate pocketBottomDelegate =
                getParentDelegate().findFragment(PocketBottomDelegate.class);
        pocketBottomDelegate.setBottomBarVisible(View.GONE);
    }

    @Override
    public void onDrawerClosed(@NonNull View view) {
        //显示bottomBar
        PocketBottomDelegate pocketBottomDelegate =
                getParentDelegate().findFragment(PocketBottomDelegate.class);
        pocketBottomDelegate.setBottomBarVisible(View.VISIBLE);
    }

    @Override
    public void onDrawerStateChanged(int i) {

    }
}
