package com.dididi.pocket.ec.main.mall.goods.details

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View

import com.dididi.pocket.core.delegates.PocketDelegate
import com.dididi.pocket.core.entity.Goods
import com.dididi.pocket.ec.R

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

    override fun onBindChildView(savedInstanceState: Bundle?, rootView: View?) {

    }

    override fun onBindView(savedInstanceState: Bundle?, rootView: View?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
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
