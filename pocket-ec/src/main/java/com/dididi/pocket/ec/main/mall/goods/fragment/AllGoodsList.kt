package com.dididi.pocket.ec.main.mall.goods.fragment

import android.os.Bundle
import android.view.View
import android.widget.Toast

import com.chad.library.adapter.base.BaseQuickAdapter
import com.dididi.pocket.core.delegates.PocketDelegate
import com.dididi.pocket.core.entity.Goods
import com.dididi.pocket.ec.R
import com.dididi.pocket.ec.main.mall.goods.adapter.GoodsAdapter
import com.dididi.pocket.ec.main.mall.goods.details.GoodsPageDelegate
import com.dididi.pocket.ec.main.mall.goods.details.MerchantPageDelegate

import java.util.ArrayList
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.item_mall_goods_all.*


/**
 * @author dididi
 * @describe 全部商品页面
 * @since 17/10/2018
 */

class AllGoodsList : PocketDelegate(), BaseQuickAdapter.OnItemChildClickListener, BaseQuickAdapter.OnItemClickListener {

    private val mGoodsList = ArrayList<Goods>()
    private var mAdapter: GoodsAdapter? = null

    override fun setLayout(): Any {
        return R.layout.item_mall_goods_all
    }

    override fun onBindView(savedInstanceState: Bundle?, rootView: View) {
        initGoods()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val manager = LinearLayoutManager(this.context)
        item_mall_goods_all_recycler_view?.layoutManager = manager
        mAdapter = GoodsAdapter(R.layout.item_mall_goods_list, mGoodsList)
        mAdapter!!.onItemChildClickListener = this
        mAdapter!!.onItemClickListener = this
        item_mall_goods_all_recycler_view?.adapter = mAdapter
    }

    override fun onItemChildClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
        val goods = adapter.getItem(position) as Goods?
                ?: throw RuntimeException("goods can not be null!")
        if (view.id == R.id.item_mall_goods_list_enter) {
            getParentDelegate<PocketDelegate>().supportDelegate.start(MerchantPageDelegate.getStartShop(goods.shopId))
        }
    }

    override fun onItemClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
        val goods = adapter.getItem(position) as Goods?
                ?: throw RuntimeException("goods can not be null")
        getParentDelegate<PocketDelegate>().supportDelegate.start(GoodsPageDelegate.getStartGoods(goods))
        Toast.makeText(context, "功能尚未开发完成，请侧滑返回", Toast.LENGTH_SHORT).show()
    }

    private fun initGoods() {
        for (i in 0..4) {
            val cat = Goods()
                    .setShopId(1)
                    .setShopName("我是一家店铺")
                    .setGoodsImg(R.mipmap.banner_02.toString())
                    .setGoodsName("试试上下滑动")
                    .setSales(20)
                    .setGoodsStyle("橘喵")
                    .setGoodsPrice(233.0)
                    .setGoodsCount(1)
            val guitar = Goods()
                    .setShopId(2)
                    .setShopName("我也是一家店铺也是一家店铺也是一家店铺也是一家店铺")
                    .setGoodsImg(R.mipmap.banner_03.toString())
                    .setGoodsName("试试左右滑动")
                    .setGoodsStyle("小喵")
                    .setSales(1111111111)
                    .setGoodsPrice(564564.0)
                    .setGoodsCount(2)
            if (i != 0) {
                cat.isFirst = false
                guitar.isFirst = false
            }
            mGoodsList.add(cat)
            mGoodsList.add(guitar)
        }
    }
}
