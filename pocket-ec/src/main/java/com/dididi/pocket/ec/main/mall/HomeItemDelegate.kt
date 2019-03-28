package com.dididi.pocket.ec.main.mall

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.dididi.pocket.core.app.AccountManager
import com.dididi.pocket.core.app.IUserChecker
import com.dididi.pocket.core.delegates.PocketDelegate
import com.dididi.pocket.core.delegates.bottom.BottomItemDelegate
import com.dididi.pocket.core.entity.News
import com.dididi.pocket.core.fakedata.FakeUser
import com.dididi.pocket.core.ui.banner.GlideImageLoader
import com.dididi.pocket.core.util.AutoBarUtil
import com.dididi.pocket.core.util.PocketPreferences
import com.dididi.pocket.ec.R
import com.dididi.pocket.ec.main.PocketBottomDelegate
import com.dididi.pocket.ec.main.mall.adapter.NewsAdapter
import com.dididi.pocket.ec.main.mall.goods.GoodsListDelegate
import com.dididi.pocket.ec.main.mall.list.FakeImageList
import com.dididi.pocket.ec.sign.SignDelegate
import com.google.android.material.navigation.NavigationView
import com.youth.banner.BannerConfig
import com.youth.banner.Transformer
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.delegate_mall_home.*
import java.util.*

/**
 * @author dididi
 * @describe 首页fragment
 * @since 24/07/2018
 */

class HomeItemDelegate : BottomItemDelegate(),
        NavigationView.OnNavigationItemSelectedListener,
        View.OnClickListener, BaseQuickAdapter.OnItemChildClickListener,
        DrawerLayout.DrawerListener {

    private val mFakeImages = FakeImageList()
    private val mNews = ArrayList<News>()
    private var mNavigationView: View? = null
    private var mEmail: AppCompatTextView? = null
    private var mName: AppCompatTextView? = null
    private var mAvatar: CircleImageView? = null

    private var mAdapter: NewsAdapter? = null

    override fun setLayout(): Any {
        return R.layout.delegate_mall_home
    }

    override fun onBindView(savedInstanceState: Bundle?, rootView: View) {
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //加载navigationView布局
        mNavigationView = layoutInflater.inflate(R.layout.item_home_nav_header, delegate_mall_home_nav)
        mEmail = mNavigationView!!.findViewById(R.id.item_home_nav_header_email)
        mName = mNavigationView!!.findViewById(R.id.item_home_nav_header_name)
        mAvatar = mNavigationView!!.findViewById(R.id.item_home_nav_header_avatar)
        //设置nav默认选中item
        delegate_mall_home_nav!!.setCheckedItem(R.id.menu_home_item_nav_discover)
        //设置刷新样式
        delegate_mall_home_swipe_refresh!!.setColorSchemeColors(resources.getColor(R.color.textColorDark))
        delegate_mall_home_swipe_refresh!!.setOnRefreshListener { this.refreshNews() }
        //初始化banner
        initBanner()
        //设置左侧图标样式
        delegate_mall_home_searchBar!!.setLeftIcon("{faw-bars}")
        //nav item点击事件监听
        delegate_mall_home_nav!!.setNavigationItemSelectedListener(this)
        //toolbar左侧图标点击事件监听
        delegate_mall_home_searchBar.setLeftIconListener(this)
        //toolbar搜索按钮点击事件监听
        delegate_mall_home_searchBar.setSearchIconListener(this)
        delegate_mall_home_photography.setOnClickListener {
            getParentDelegate<PocketDelegate>().supportDelegate.start(GoodsListDelegate())
        }
        //drawerLayout事件监听
        home_item_drawer!!.addDrawerListener(this)
        //初始化发现消息
        initFakeNews()
        //设置RecyclerView布局方式及加入适配器
        val layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        delegate_mall_home_discover!!.layoutManager = layoutManager
        mAdapter = NewsAdapter(R.layout.item_home_news, mNews)
        mAdapter!!.onItemChildClickListener = this
        delegate_mall_home_discover!!.adapter = mAdapter
        //初始化用户信息
        initUserIfo()
        if (activity != null) {
            AutoBarUtil.changeBarColor(activity!!, AutoBarUtil.COLOR_TOOLBAR.toInt())
        }
    }

    override fun onStart() {
        super.onStart()
        //开启banner自动播放
        delegate_mall_home_banner!!.startAutoPlay()
    }

    override fun onStop() {
        super.onStop()
        //暂停banner自动播放
        delegate_mall_home_banner!!.stopAutoPlay()
    }

    override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
        val itemId = menuItem.itemId
        when (itemId) {
            R.id.menu_home_item_nav_discover ->
                Toast.makeText(context, "点击跳转发现界面", Toast.LENGTH_SHORT).show()
            R.id.menu_home_item_nav_collect ->
                Toast.makeText(context, "点击跳转我的收藏界面", Toast.LENGTH_SHORT).show()
            R.id.menu_home_item_nav_attention ->
                Toast.makeText(context, "点击跳转我的关注界面", Toast.LENGTH_SHORT).show()
            R.id.menu_home_item_nav_friends ->
                Toast.makeText(context, "点击跳转我的朋友界面", Toast.LENGTH_SHORT).show()
            R.id.menu_home_item_nav_account ->
                Toast.makeText(context, "点击跳转账户界面", Toast.LENGTH_SHORT).show()
            R.id.menu_home_item_nav_exit -> {
                //清除sp中的内容以达到退出效果
                PocketPreferences.clearPocketProfile()
                getParentDelegate<PocketDelegate>().supportDelegate.startWithPop(SignDelegate())
            }
            R.id.menu_home_item_nav_setting ->
                Toast.makeText(context, "点击跳转设置界面", Toast.LENGTH_SHORT).show()
            R.id.menu_home_item_nav_help ->
                Toast.makeText(context, "点击跳转帮助界面", Toast.LENGTH_SHORT).show()
        }
        return true
    }

    private fun initBanner() {
        delegate_mall_home_banner!!.setBannerAnimation(Transformer.Default)
                .setBannerStyle(BannerConfig.CIRCLE_INDICATOR)
                .setIndicatorGravity(BannerConfig.CENTER)
                .setDelayTime(1500)
                .setImageLoader(GlideImageLoader())
                .setImages(mFakeImages.images)
                .start()
    }

    override fun onClick(view: View) {
        if (view.id == delegate_mall_home_searchBar!!.leftIconId) {
            delegate_mall_home_searchBar.setLeftIconListener {
                //开启滑动菜单
                home_item_drawer!!.openDrawer(GravityCompat.START)

            }
        } else if (view.id == delegate_mall_home_searchBar.searchIconId) {
            delegate_mall_home_searchBar.setSearchIconListener { Toast.makeText(context, "点击搜索", Toast.LENGTH_SHORT).show() }
        }
    }


    private fun initFakeNews() {
        mNews.clear()
        val news = arrayOf(News(FakeUser.getUser("2"),
                "试试下拉刷新", "27/7/2018"), News(FakeUser.getUser("3"),
                "试试左边缘滑动", "28/7/2018"), News(FakeUser.getUser("4"),
                "试试点击摄影入口", "26/7/2018"), News(FakeUser.getUser("5"),
                "试试下拉刷新", "27/7/2018"), News(FakeUser.getUser("6"),
                "试试点击摄影入口", "28/7/2018"), News(FakeUser.getUser("8"),
                "试试左边缘滑动", "26/7/2018"), News(FakeUser.getUser("9"),
                "试试下拉刷新", "27/7/2018"), News(FakeUser.getUser("10"),
                "试试左边缘滑动", "28/7/2018"), News(FakeUser.getUser("11"),
                "试试点击摄影入口", "26/7/2018"), News(FakeUser.getUser("12"),
                "试试左边缘滑动", "27/7/2018"))
        for (i in 0..10) {
            val random = Random()
            val index = random.nextInt(news.size)
            mNews.add(news[index])
        }
    }

    private fun refreshNews() {
        Thread {
            try {
                Thread.sleep(2000)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }

            //切换回ui线程
            if (activity != null) {
                activity!!.runOnUiThread {
                    initFakeNews()
                    mAdapter!!.notifyDataSetChanged()
                    delegate_mall_home_swipe_refresh!!.isRefreshing = false
                }
            }
        }.start()
    }

    /**
     * 初始化抽屉布局用户信息
     */
    private fun initUserIfo() {
        AccountManager.checkAccount(object : IUserChecker {
            override fun onSignIn() {
                //如果登录，从sp中获取用户信息
                val avatar = PocketPreferences
                        .getCustomPocketProfile("userAvatar")
                        .replace("\"", "")
                val name = PocketPreferences
                        .getCustomPocketProfile("userName")
                        .replace("\"", "")
                val email = PocketPreferences
                        .getCustomPocketProfile("userEmail")
                        .replace("\"", "")
                Glide.with(this@HomeItemDelegate)
                        .load(avatar)
                        .into(mAvatar!!)
                mName!!.text = name
                mEmail!!.text = email
            }

            override fun onNotSignIn() {
                val me = FakeUser.getUser("1")
                Glide.with(this@HomeItemDelegate)
                        .load(Integer.parseInt(me.avatar))
                        .into(mAvatar!!)
                mName!!.text = me.name
                mEmail!!.text = me.email
                mName!!.setOnClickListener {
                    getParentDelegate<PocketDelegate>().supportDelegate.startWithPop(SignDelegate())
                }
                mEmail!!.setOnClickListener {
                    getParentDelegate<PocketDelegate>().supportDelegate.startWithPop(SignDelegate())
                }
            }
        })
    }

    /**
     * newsAdapter的子控件点击事件
     *
     * @param adapter  newsAdapter
     * @param view     子控件
     * @param position item位置
     */
    override fun onItemChildClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
        val content: StringBuilder?
        val news = adapter.getItem(position) as News?
                ?: throw RuntimeException("news can not be null")
        val id = view.id
        content = when (id) {
            R.id.item_home_news_head -> {
                StringBuilder().append("你点击了")
                        .append(news.userName)
                        .append("头像")
            }
            R.id.item_home_news_name -> {
                StringBuilder().append("你点击了")
                        .append(news.userName)
                        .append("名字")
            }
            R.id.item_home_news_comment -> {
                StringBuilder().append("你点击了")
                        .append(news.userName)
                        .append("评论")
            }
            else -> null
        }
        if (content != null) {
            Toast.makeText(context, content.toString(), Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDrawerSlide(view: View, v: Float) {

    }

    override fun onDrawerOpened(view: View) {
        //隐藏bottomBar
        val pocketBottomDelegate =
                getParentDelegate<PocketDelegate>().findFragment(PocketBottomDelegate::class.java)
        pocketBottomDelegate.setBottomBarVisible(View.GONE)
    }

    override fun onDrawerClosed(view: View) {
        //显示bottomBar
        val pocketBottomDelegate =
                getParentDelegate<PocketDelegate>().findFragment(PocketBottomDelegate::class.java)
        pocketBottomDelegate.setBottomBarVisible(View.VISIBLE)
    }

    override fun onDrawerStateChanged(i: Int) {

    }

    /**
     * 重写父类的onScrollToTop()，使scrollView滚动到顶部
     */
    override fun onScrollToTop() {
        //设置toolbar的内容自动滑出来
        delegate_mall_home_appbar.setExpanded(true);
        //ScrollView滚动到顶部
        delegate_mall_home_scroll_view!!.smoothScrollTo(0, 0)
    }

    /**
     * 重写父类的onRefresh()，刷新当前页面
     */
    override fun onRefresh() {
        delegate_mall_home_swipe_refresh!!.isRefreshing = true
        refreshNews()
    }

    override fun isTop(): Boolean {
        return delegate_mall_home_scroll_view!!.scrollY == 0
    }
}
