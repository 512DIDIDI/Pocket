package com.dididi.pocket.ec.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.dididi.pocket.core.delegates.bottom.BaseBottomDelegate;
import com.dididi.pocket.core.delegates.bottom.BottomItemDelegate;
import com.dididi.pocket.core.delegates.bottom.BottomTabBean;
import com.dididi.pocket.core.delegates.bottom.ItemFactory;
import com.dididi.pocket.ec.R;
import com.dididi.pocket.ec.main.mall.HomeItemDelegate;
import com.dididi.pocket.ec.main.message.MessageItemDelegate;
import com.dididi.pocket.ec.main.personal.PersonalItemDelegate;
import com.dididi.pocket.ec.main.shoppingcart.ShopCartItemDelegate;

import org.jetbrains.annotations.NotNull;

import java.util.LinkedHashMap;


/**
 * @author dididi
 * @since 25/07/2018 .
 */

public class PocketBottomDelegate extends BaseBottomDelegate {
    //底部导航栏

    @NotNull
    @Override
    public LinkedHashMap<BottomTabBean, BottomItemDelegate> setItems(@NotNull ItemFactory factory) {
        final LinkedHashMap<BottomTabBean, BottomItemDelegate> items = new LinkedHashMap<>();
        items.put(new BottomTabBean("{faw-home}", "主页"),
                new HomeItemDelegate());
        items.put(new BottomTabBean("{faw-comments}", "消息"),
                new MessageItemDelegate());
        items.put(new BottomTabBean("{faw-shopping-cart}", "购物车"),
                new ShopCartItemDelegate());
        items.put(new BottomTabBean("{faw-user}", "我的"),
                new PersonalItemDelegate());
        return factory.addItem(items).build();
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) { }

    @Override
    public int setPressColor() {
        return R.color.tabPressColor;
    }

    @Override
    public int setIndexDelegate() {
        return 0;
    }

    public void setBottomBarVisible(int visible) {
        //解决跳到首页时 drawerLayout开启情况下 动画的重复播放
        if (visible == View.VISIBLE && getBottomBar().getVisibility() == View.GONE) {
            getBottomBar().setVisibility(View.VISIBLE);
        } else if (visible == View.GONE && getBottomBar().getVisibility() == View.VISIBLE) {
            getBottomBar().setVisibility(View.GONE);
        }
    }
}
