package com.dididi.pocket.ec.main.mall.goods.details

import android.annotation.SuppressLint
import android.graphics.BitmapFactory
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.Gravity
import android.view.View

import com.dididi.pocket.core.delegates.PocketDelegate
import com.dididi.pocket.core.entity.Goods
import com.dididi.pocket.ec.R
import com.gyf.immersionbar.ktx.immersionBar
import kotlinx.android.synthetic.main.delegate_mall_goods_details_goods.*

/**
 * @author dididi
 * @describe 商品详情页面
 * @since 17/10/2018
 */

class GoodsPageDelegate
/**
 * 私有化构造函数，防止从外部实例化而导致没有传入应该传入的参数
 */
@SuppressLint("ValidFragment")
private constructor() : PocketDelegate() {

    override fun setLayout(): Any {
        return R.layout.delegate_mall_goods_details_goods
    }

    private lateinit var mGoods: Goods

    override fun onBindChildView(savedInstanceState: Bundle?, rootView: View?) {
        mGoods = arguments?.getParcelable("goods") as Goods
    }

    override fun onBindView(savedInstanceState: Bundle?, rootView: View?) {
        delegate_mall_goods_details_goods_img
                .setImageResource(resources.getIdentifier(mGoods.goodsImg,
                        "mipmap", context?.packageName))
        delegate_mall_goods_details_goods_collapsing_toolbar.title = mGoods.goodsName
        delegate_mall_goods_details_goods_collapsing_toolbar
                .setExpandedTitleColor(ContextCompat.getColor(context!!, R.color.textColorWhite))
        delegate_mall_goods_details_goods_collapsing_toolbar
                .setCollapsedTitleTextColor(ContextCompat.getColor(context!!, R.color.textColorWhite))
        delegate_mall_goods_details_goods_collapsing_toolbar.expandedTitleGravity = Gravity.CENTER
        delegate_mall_goods_details_goods_collapsing_toolbar.collapsedTitleGravity = Gravity.CENTER

    }

    override fun getTitleBarId() = R.id.delegate_mall_goods_details_goods_toolbar

    override fun initImmersionBar() {
        immersionBar {
            flymeOSStatusBarFontColor(R.color.textColorWhite)
            keyboardEnable(true)
        }
    }

    override fun setSwipeBackEnable(enable: Boolean) {
        super.setSwipeBackEnable(true)
    }

    companion object {

        /**
         * 外部通过此方法来获取实例，
         * @param goods 传入的goods信息
         * @return 包装后的GoodsPageDelegate
         */
        fun getStartGoods(goods: Goods): GoodsPageDelegate {
            val goodsPageDelegate = GoodsPageDelegate()
            val bundle = Bundle()
            bundle.putParcelable("goods", goods)
            goodsPageDelegate.arguments = bundle
            return goodsPageDelegate
        }
    }
}
