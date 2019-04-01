package com.dididi.pocket.ec.main.mall.goods.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.dididi.pocket.core.delegates.PocketDelegate
import com.dididi.pocket.core.entity.Shop
import com.dididi.pocket.ec.R
import com.dididi.pocket.ec.main.mall.goods.adapter.MerchantAdapter
import com.dididi.pocket.ec.main.mall.goods.details.MerchantPageDelegate
import java.util.*


/**
 * @author dididi
 * @describe 商家商品页面
 * @since 17/10/2018
 */

class MerchantGoodsList : PocketDelegate(), BaseQuickAdapter.OnItemClickListener {

    private val mShopList = ArrayList<Shop>()
    private var mAdapter: MerchantAdapter? = null

    override fun setLayout(): Any {
        return R.layout.item_mall_goods_merchant
    }

    override fun onBindChildView(savedInstanceState: Bundle?, rootView: View?) {
        initShop()
        val manager = LinearLayoutManager(this.context)
        val merchantGoodsView = rootView!!.findViewById<RecyclerView>(R.id.item_mall_goods_merchant_recycler_view)
        merchantGoodsView.layoutManager = manager
        mAdapter = MerchantAdapter(R.layout.item_mall_shop_list, mShopList)
        mAdapter!!.onItemClickListener = this
        merchantGoodsView.adapter = mAdapter
    }

    override fun onBindView(savedInstanceState: Bundle?, rootView: View?) {
    }

    override fun onItemClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
        val shop = adapter.getItem(position) as Shop?
                ?: throw RuntimeException("shop can not be null")
        getParentDelegate<PocketDelegate>().supportDelegate.start(MerchantPageDelegate.getStartShop(shop.shopId))
    }

    private fun initShop() {
        val shop1 = Shop(true, "我是一家店铺")
                .setShopId(1)
                .setShopImg(R.mipmap.banner_01.toString())
                .setEvaluate(98f)
                .setSales(999)
                .setMinPrice(233.0)
                .setMaxPrice(666.0)
        val shop2 = Shop(true, "试试左侧滑返回")
                .setShopId(2)
                .setShopImg(R.mipmap.banner_02.toString())
                .setEvaluate(96f)
                .setSales(777)
                .setMinPrice(111.0)
                .setMaxPrice(555.0)
        val shop3 = Shop(true, "我还是一家店铺")
                .setShopId(3)
                .setShopImg(R.mipmap.banner_03.toString())
                .setEvaluate(97f)
                .setSales(1001)
                .setMinPrice(1.0)
                .setMaxPrice(1024.0)
        for (i in 0..2) {
            mShopList.add(shop1)
            mShopList.add(shop2)
            mShopList.add(shop3)
        }
    }
}
