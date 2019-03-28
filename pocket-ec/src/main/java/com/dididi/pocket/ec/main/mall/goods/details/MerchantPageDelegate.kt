package com.dididi.pocket.ec.main.mall.goods.details

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.dididi.pocket.core.delegates.PocketDelegate
import com.dididi.pocket.core.entity.Goods
import com.dididi.pocket.ec.R
import com.dididi.pocket.ec.main.mall.goods.details.adapter.MerchantPageAdapter
import kotlinx.android.synthetic.main.delegate_mall_goods_details_merchant.*
import java.util.*


/**
 * @author dididi
 * @describe 商家详情页面
 * @since 17/10/2018
 */

class MerchantPageDelegate
/**
 * 私有化构造函数，防止从外部实例化而导致没有传入应该传入的参数
 */
@SuppressLint("ValidFragment")
private constructor() : PocketDelegate() {

    private var mAdapter: MerchantPageAdapter? = null
    private val goodsList = ArrayList<Goods>()

    override fun setLayout(): Any {
        return R.layout.delegate_mall_goods_details_merchant
    }

    override fun onBindView(savedInstanceState: Bundle?, rootView: View) {
        initGoods()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val gridLayoutManager = GridLayoutManager(context, 2)
        delegate_mall_goods_details_merchant_avatar!!.setImageResource(R.mipmap.avatarman01)
        delegate_mall_goods_details_merchant_name?.text = "张三"
        delegate_mall_goods_details_merchant_attention_num!!.text = 233.toString()
        delegate_mall_goods_details_merchant_fans_num!!.text = 666.toString()
        delegate_mall_goods_details_merchant_product_num!!.text = 6.toString()
        delegate_mall_goods_details_merchant_searchbar!!.setLeftIcon("{faw-chevron-left}")
        delegate_mall_goods_details_merchant_searchbar!!.setLeftIconListener { supportDelegate.pop() }
        delegate_mall_goods_details_merchant_recycler_view!!.layoutManager = gridLayoutManager
        mAdapter = MerchantPageAdapter(R.layout.item_mall_goods_details_merchant, goodsList)
        delegate_mall_goods_details_merchant_recycler_view!!.adapter = mAdapter
    }

    override fun setSwipeBackEnable(enable: Boolean) {
        super.setSwipeBackEnable(true)
    }

    private fun initGoods() {
        val cat = Goods()
                .setGoodsImg(R.mipmap.banner_04.toString())
                .setGoodsName("试试点击全选按钮")
        val cat2 = Goods()
                .setGoodsImg(R.mipmap.banner_02.toString())
                .setGoodsName("试试向左滑动")
        val guitar = Goods()
                .setGoodsImg(R.mipmap.banner_01.toString())
                .setGoodsName("试试点击删除商品")
        val guitar2 = Goods()
                .setGoodsImg(R.mipmap.banner_02.toString())
                .setGoodsName("试试减少增加商品")
        val flower = Goods()
                .setGoodsImg(R.mipmap.banner_05.toString())
                .setGoodsName("试试点击与店主聊天icon")
        val flower2 = Goods()
                .setGoodsImg(R.mipmap.banner_03.toString())
                .setGoodsName("试试点击toolbar清空购物车")
        goodsList.add(cat)
        goodsList.add(cat2)
        goodsList.add(guitar)
        goodsList.add(guitar2)
        goodsList.add(flower)
        goodsList.add(flower2)
    }

    companion object {

        /**
         * 外部通过此方法来获取实例，
         *
         * @param shopId 传入的shopId信息
         * @return 包装后的MerchantPageDelegate
         */
        fun getStartShop(shopId: Long): MerchantPageDelegate {
            val merchantPageDelegate = MerchantPageDelegate()
            val bundle = Bundle()
            bundle.putLong("shopId", shopId)
            merchantPageDelegate.arguments = bundle
            return merchantPageDelegate
        }
    }
}
