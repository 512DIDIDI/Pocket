package com.dididi.pocket.ec.main.mall.goods

import android.os.Bundle
import android.view.View
import com.dididi.pocket.core.delegates.PocketDelegate
import com.dididi.pocket.ec.R
import com.dididi.pocket.ec.main.mall.goods.fragment.AllGoodsList
import com.dididi.pocket.ec.main.mall.goods.fragment.BestGoodsList
import com.dididi.pocket.ec.main.mall.goods.fragment.MerchantGoodsList
import com.gyf.immersionbar.ktx.immersionBar
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems
import kotlinx.android.synthetic.main.delegate_mall_goods_list.*


/**
 * @describe 商品页面
 * @author dididi
 * @since 05/08/2018 .
 */

class GoodsListDelegate : PocketDelegate(), View.OnClickListener {

    override fun setLayout(): Any {
        return R.layout.delegate_mall_goods_list
    }

    override fun onBindChildView(savedInstanceState: Bundle?, rootView: View?) {
    }

    override fun onBindView(savedInstanceState: Bundle?, rootView: View?) {
        val adapter = FragmentPagerItemAdapter(childFragmentManager, FragmentPagerItems.with(context)
                .add(R.string.all, AllGoodsList::class.java)
                .add(R.string.best, BestGoodsList::class.java)
                .add(R.string.store, MerchantGoodsList::class.java)
                .create())
        delegate_mall_goods_viewpager!!.adapter = adapter
        delegate_mall_goods_chooseBar!!.setViewPager(delegate_mall_goods_viewpager)
        delegate_mall_goods_searchBar!!.setLeftIcon(context?.getString(R.string.faw_chevron_left))
        delegate_mall_goods_searchBar!!.setLeftIconListener(this)
    }

    override fun getTitleBarId() = R.id.delegate_mall_goods_toolbar

    override fun initImmersionBar() {
        immersionBar {
            flymeOSStatusBarFontColor(R.color.textColorWhite)
            keyboardEnable(true)
        }
    }

    /**
     * 设置侧滑返回
     *
     * @param enable true即为可以侧滑返回
     */
    override fun setSwipeBackEnable(enable: Boolean) {
        super.setSwipeBackEnable(true)
    }

    override fun onClick(view: View) {
        if (view.id == delegate_mall_goods_searchBar!!.leftIconId) {
            supportDelegate.pop()
        }
    }

}
