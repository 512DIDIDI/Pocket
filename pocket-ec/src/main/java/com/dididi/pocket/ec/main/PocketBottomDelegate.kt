package com.dididi.pocket.ec.main

import android.view.View

import com.dididi.pocket.core.delegates.bottom.BaseBottomDelegate
import com.dididi.pocket.core.delegates.bottom.BottomItemDelegate
import com.dididi.pocket.core.delegates.bottom.BottomTabBean
import com.dididi.pocket.core.delegates.bottom.ItemFactory
import com.dididi.pocket.ec.R
import com.dididi.pocket.ec.main.mall.HomeItemDelegate
import com.dididi.pocket.ec.main.message.MessageItemDelegate
import com.dididi.pocket.ec.main.personal.PersonalItemDelegate
import com.dididi.pocket.ec.main.shoppingcart.ShopCartItemDelegate

import java.util.LinkedHashMap


/**
 * @author dididi
 * @since 25/07/2018 .
 */

class PocketBottomDelegate : BaseBottomDelegate() {
    //底部导航栏

    override fun setItems(factory: ItemFactory): LinkedHashMap<BottomTabBean, BottomItemDelegate> {
        val items = LinkedHashMap<BottomTabBean, BottomItemDelegate>()
        items[BottomTabBean("{faw-home}", "主页")] = HomeItemDelegate()
        items[BottomTabBean("{faw-comments}", "消息")] = MessageItemDelegate()
        items[BottomTabBean("{faw-shopping-cart}", "购物车")] = ShopCartItemDelegate()
        items[BottomTabBean("{faw-user}", "我的")] = PersonalItemDelegate()
        return factory.addItem(items).build()
    }

    override fun setPressColor(): Int {
        return R.color.tabPressColor
    }

    override fun setIndexDelegate(): Int {
        return 0
    }

    fun setBottomBarVisible(visible: Int) {
        //解决跳到首页时 drawerLayout开启情况下 动画的重复播放
        if (visible == View.VISIBLE && mBottomBar!!.visibility == View.GONE) {
            mBottomBar!!.visibility = View.VISIBLE
        } else if (visible == View.GONE && mBottomBar!!.visibility == View.VISIBLE) {
            mBottomBar!!.visibility = View.GONE
        }
    }
}
