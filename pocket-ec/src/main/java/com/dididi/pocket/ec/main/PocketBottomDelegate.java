package com.dididi.pocket.ec.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.dididi.pocket.core.ui.animation.PocketAnimation;
import com.dididi.pocket.ec.R;
import com.dididi.pocket.ec.main.mall.HomeItemDelegate;
import com.dididi.pocket.ec.main.message.MessageItemDelegate;
import com.dididi.pocket.ec.main.personal.PersonalItemDelegate;
import com.dididi.pocket.ec.main.shoppingCart.ShoppingCartItemDelegate;
import com.dididi.pocket.core.delegates.bottom.BaseBottomDelegate;
import com.dididi.pocket.core.delegates.bottom.BottomItemDelegate;
import com.dididi.pocket.core.delegates.bottom.BottomTabBean;
import com.dididi.pocket.core.delegates.bottom.ItemFactory;

import java.util.LinkedHashMap;


/**
 * Created by dididi
 * on 25/07/2018 .
 */

public class PocketBottomDelegate extends BaseBottomDelegate {
    //底部导航栏



    @Override
    public LinkedHashMap<BottomTabBean, BottomItemDelegate> setItems(ItemFactory factory) {
        final LinkedHashMap<BottomTabBean, BottomItemDelegate> items = new LinkedHashMap<>();
        items.put(new BottomTabBean("{faw-home}", "主页"),
                new HomeItemDelegate());
        items.put(new BottomTabBean("{faw-comments}", "消息"),
                new MessageItemDelegate());
        items.put(new BottomTabBean("{faw-shopping-cart}", "购物车"),
                new ShoppingCartItemDelegate());
        items.put(new BottomTabBean("{faw-user}", "我的"),
                new PersonalItemDelegate());
        return factory.addItem(items).build();
    }

    @Override
    public int setPressColor() {
        return R.color.tabPressColor;
    }

    @Override
    public int setIndexDelegate() {
        return 0;
    }

    public void setBottomBarVisible(int visible){
        //解决跳到首页时 drawerLayout开启情况下 动画的重复播放
        if (visible == View.VISIBLE && mBottomBar.getVisibility() == View.GONE){
            mBottomBar.startAnimation(PocketAnimation.fadeIn(getContext()));
        }else if (visible == View.GONE && mBottomBar.getVisibility() == View.VISIBLE){
            mBottomBar.startAnimation(PocketAnimation.fadeOut(getContext()));
        }
        mBottomBar.setVisibility(visible);
    }
}
