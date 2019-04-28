package com.dididi.pocket.ec.main.shoppingcart

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.Toast
import com.chad.library.adapter.base.BaseQuickAdapter
import com.dididi.pocket.core.delegates.PocketDelegate
import com.dididi.pocket.core.delegates.bottom.BottomItemDelegate
import com.dididi.pocket.core.entity.Goods
import com.dididi.pocket.core.entity.Message
import com.dididi.pocket.core.entity.Shop
import com.dididi.pocket.core.fakedata.FakeUser
import com.dididi.pocket.core.ui.dialog.PocketDialog
import com.dididi.pocket.ec.R
import com.dididi.pocket.ec.main.mall.goods.details.MerchantPageDelegate
import com.dididi.pocket.ec.main.message.chat.ChatDelegate
import com.dididi.pocket.ec.main.shoppingcart.adapter.ShopCartAdapter
import com.gyf.immersionbar.ktx.immersionBar
import kotlinx.android.synthetic.main.delegate_shoppingcart_shopcart.*
import java.math.BigDecimal
import java.util.*


/**
 * @author dididi
 * @describe 购物车页面
 * @since 11/10/2018
 */

class ShopCartItemDelegate : BottomItemDelegate(),
        BaseQuickAdapter.OnItemChildClickListener, View.OnClickListener {

    private val shopList = ArrayList<Shop>()
    private var mAdapter: ShopCartAdapter? = null
    private var mTotalPrice = 0.0
    private var isAllSelected = false

    override fun setLayout(): Any {
        return R.layout.delegate_shoppingcart_shopcart
    }

    override fun onBindChildView(savedInstanceState: Bundle?, rootView: View?) {
    }

    override fun onBindView(savedInstanceState: Bundle?, rootView: View?) {
        val layoutManager = LinearLayoutManager(context,
                LinearLayoutManager.VERTICAL, false)
        delegate_shoppingCart_shopcart_recyclerView!!.layoutManager = layoutManager
        initData()
        mAdapter = ShopCartAdapter(R.layout.item_shopcart_goods,
                R.layout.item_shopcart_shop, shopList)
        mAdapter!!.onItemChildClickListener = this
        delegate_shoppingCart_shopcart_recyclerView!!.adapter = mAdapter
        delegate_shoppingCart_shopcart_all_delete.setOnClickListener(this)
        delegate_shoppingCart_shopcart_all_selected.setOnClickListener(this)
        delegate_shoppingCart_shopcart_all_selected_text.setOnClickListener(this)
    }

    override fun getTitleBarId() = R.id.delegate_shoppingCart_shopcart_toolbar

    override fun initImmersionBar() {
        immersionBar {
            flymeOSStatusBarFontColor(R.color.textColorWhite)
            keyboardEnable(true)
        }
    }

    /**
     * adapter子项点击事件处理
     *
     * @param adapter  recyclerView适配器
     * @param view     当前点击的view
     * @param position 当前位置
     */
    override fun onItemChildClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
        var content: StringBuilder? = null
        val shop = adapter.getItem(position) as Shop?
                ?: throw RuntimeException("shop can not be null")
        val id = view.id
        if (id == R.id.item_shopcart_shop_select) {
            //点击选择店铺
            shop.isShopSelected = !shop.isShopSelected
            selectShop(position)
            adapter.notifyDataSetChanged()
            if (isAllSelected) {
                isAllSelected = false
                notifyAllSelectChanged()
            }
            notifyPriceChanged()
        } else if (id == R.id.item_shopcart_shop_chat) {
            //点击选择发起店主聊天
            val message = Message("你好", Message.TYPE_SENT, FakeUser.getUser("1"),
                    FakeUser.getUser(shop.userId.toString()), "15/10/2018")
            getParentDelegate<PocketDelegate>().supportDelegate.start(ChatDelegate.getStartChat(message))
        } else if (id == R.id.item_shopcart_shop_name) {
            getParentDelegate<PocketDelegate>().supportDelegate.start(MerchantPageDelegate.getStartShop(shop.shopId))
        } else if (id == R.id.item_shopcart_goods_select) {
            //点击选择商品
            shop.t.isGoodsSelected = !shop.t.isGoodsSelected
            adapter.notifyDataSetChanged()
            if (isAllSelected) {
                //如果全选按钮选中，则更改其为否
                isAllSelected = false
                notifyAllSelectChanged()
            }
            notifyPriceChanged()
        } else if (id == R.id.item_shopcart_goods_style) {
            //点击更改商品样式
            content = StringBuilder()
            content.append("你点击了")
                    .append(shop.t.goodsName)
                    .append("的样式")
            notifyPriceChanged()
        } else if (id == R.id.item_shopcart_goods_increase) {
            //点击增加商品
            var count = shop.t.goodsCount
            shop.t.goodsCount = ++count
            adapter.notifyDataSetChanged()
            notifyPriceChanged()
        } else if (id == R.id.item_shopcart_goods_decrease) {
            //点击减少商品
            var count = shop.t.goodsCount
            if (count == 1) {
                removeGoods(position, "商品数量为1，继续减少将会删除商品")
            }
            //防止出现count为0的情况
            if (count != 1) {
                shop.t.goodsCount = --count
                adapter.notifyDataSetChanged()
            }
        } else if (id == R.id.item_shopcart_goods_delete) {
            //点击删除商品
            if (position >= 0 && position < shopList.size) {
                removeGoods(position, "确认删除商品吗？")
            }
        } else if (id == R.id.item_shopcart_goods_collect) {
            //点击收藏商品
            content = StringBuilder()
            content.append("你收藏了")
                    .append(shop.t.goodsName)
        }
        if (null != content) {
            Toast.makeText(context, content.toString(), Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * 移除商品
     *
     * @param position 该商品对应的位置
     * @param content  dialog消息内容
     */
    private fun removeGoods(position: Int, content: String) {
        val dialog = PocketDialog.getInstance(context)
        dialog.setTitle("删除商品")
                .setContent(content)
                .isCancelableOnTouchOutside(true)
                .setConfirmClickListener {
                    shopList.removeAt(position)
                    //判断是否要移除店铺item
                    if (shopList[position - 1].isHeader) {
                        //如果前一项是店铺item
                        if (position == shopList.size) {
                            //并且当前是最后一项 删除店铺item
                            shopList.removeAt(position - 1)
                        } else if (shopList[position].isHeader) {
                            //或者后一项是店铺item 删除店铺item
                            shopList.removeAt(position - 1)
                        }
                    }
                    //刷新数据
                    mAdapter!!.notifyDataSetChanged()
                    //刷新价格
                    notifyPriceChanged()
                    dialog.dismiss()
                }
                .setCancelClickListener { dialog.dismiss() }
                .show()
    }

    /**
     * 计算总价
     */
    private fun notifyPriceChanged() {
        //每次计算前先清空总价
        mTotalPrice = 0.0
        //遍历list中的每个商品(0必定是店铺名，所以0可不必加入循环)
        for (i in 1 until shopList.size) {
            //防止出现t空指针
            if (shopList[i].isHeader) {
                continue
            }
            if (shopList[i].t.isGoodsSelected) {
                val price = shopList[i].t.goodsPrice
                val count = shopList[i].t.goodsCount
                mTotalPrice += price * count
            }
        }
        //改变底部结算栏文字
        delegate_shoppingCart_shopcart_all_price!!.setText(R.string.yuan)
        delegate_shoppingCart_shopcart_all_price!!.append(BigDecimal(mTotalPrice).stripTrailingZeros().toPlainString())
    }

    /**
     * 实现点击店铺全选该店铺下的商品
     *
     * @param position 从何处开始遍历
     */
    private fun selectShop(position: Int) {
        val shop = shopList[position]
        //遍历list
        for (i in position until shopList.size) {
            //如果是店铺item，跳过当前循环
            if (shopList[i].isHeader) {
                continue
            }
            //判断商品的商店id是否等于商店id
            if (shopList[i].t.shopId == shop.shopId) {
                //相等则选择该商品
                shopList[i].t.isGoodsSelected = shop.isShopSelected
            } else {
                break
            }
        }
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            //全删除
            R.id.delegate_shoppingCart_shopcart_all_delete -> {
                val dialog = PocketDialog.getInstance(context)
                dialog.setTitle("清空购物车")
                        .setContent("确认清空购物车吗")
                        .isCancelableOnTouchOutside(true)
                        .setCancelClickListener { dialog.dismiss() }
                        .setConfirmClickListener {
                            shopList.clear()
                            mAdapter!!.notifyDataSetChanged()
                            isAllSelected = false
                            notifyAllSelectChanged()
                            notifyPriceChanged()
                            dialog.dismiss()
                        }
                        .show()
            }
            //全选
            R.id.delegate_shoppingCart_shopcart_all_selected,
            R.id.delegate_shoppingCart_shopcart_all_selected_text -> {
                for (i in shopList.indices) {
                    if (shopList[i].isHeader) {
                        shopList[i].isShopSelected = !isAllSelected
                        continue
                    }
                    shopList[i].t.isGoodsSelected = !isAllSelected
                }
                isAllSelected = !isAllSelected
                notifyAllSelectChanged()
                mAdapter!!.notifyDataSetChanged()
                notifyPriceChanged()
            }
        }
    }

    /**
     * 底部结算栏全选按钮改变
     */
    private fun notifyAllSelectChanged() {
        if (isAllSelected) {
            delegate_shoppingCart_shopcart_all_selected!!.setText(R.string.faw_check_circle)
        } else {
            delegate_shoppingCart_shopcart_all_selected!!.setText(R.string.faw_circle)
        }
    }

    override fun onScrollToTop() {
        //设置toolbar的内容自动滑出来
        delegate_shoppingCart_shopcart_appbar!!.setExpanded(true)
        delegate_shoppingCart_shopcart_recyclerView!!.smoothScrollToPosition(0)
    }

    override fun onRefresh() {

    }

    override fun isTop(): Boolean {
        return false
    }

    /**
     * 假数据初始化 不用管他什么意思
     */
    private fun initData() {
        val cat = Goods()
                .setShopId(1)
                .setGoodsImg(R.mipmap.banner_04.toString())
                .setGoodsName("试试点击全选按钮")
                .setGoodsStyle("这是商品样式")
                .setGoodsPrice(23333333.0)
                .setGoodsCount(1)
        val cat2 = Goods()
                .setShopId(1)
                .setGoodsImg(R.mipmap.banner_02.toString())
                .setGoodsName("试试向左滑动")
                .setGoodsStyle("style")
                .setGoodsPrice(100.0)
                .setGoodsCount(1)
        val guitar = Goods()
                .setShopId(2)
                .setGoodsImg(R.mipmap.banner_01.toString())
                .setGoodsName("试试点击删除商品")
                .setGoodsStyle("小喵")
                .setGoodsPrice(666.0)
                .setGoodsCount(2)
        val guitar2 = Goods()
                .setShopId(2)
                .setGoodsImg(R.mipmap.banner_02.toString())
                .setGoodsName("试试减少增加商品")
                .setGoodsStyle("大喵")
                .setGoodsPrice(555.0)
                .setGoodsCount(2)
        val flower = Goods()
                .setShopId(3)
                .setGoodsImg(R.mipmap.banner_05.toString())
                .setGoodsName("试试点击与店主聊天icon")
                .setGoodsStyle("little puppy")
                .setGoodsPrice(524.0)
                .setGoodsCount(1)
        val flower2 = Goods()
                .setShopId(3)
                .setGoodsImg(R.mipmap.banner_03.toString())
                .setGoodsName("试试点击toolbar清空购物车")
                .setGoodsStyle("big puppy")
                .setGoodsPrice(524.0)
                .setGoodsCount(1)
        shopList.add(Shop(true, "老王的店").setUserId(5).setShopId(1))
        shopList.add(Shop(cat))
        shopList.add(Shop(cat2))
        shopList.add(Shop(true, "张三的店").setUserId(2).setShopId(2))
        shopList.add(Shop(guitar))
        shopList.add(Shop(guitar2))
        shopList.add(Shop(true, "李四的店").setUserId(3).setShopId(3))
        shopList.add(Shop(flower))
        shopList.add(Shop(flower2))
    }

    companion object {

        /**
         * 添加商品到购物车中，当然，实际操作中应该是加到服务器数据库中，从服务器读取购物车数据
         */
        fun addGoodsToShopCart(goods: Goods) {

        }
    }
}
