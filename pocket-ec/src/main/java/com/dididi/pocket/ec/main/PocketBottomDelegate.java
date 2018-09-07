package com.dididi.pocket.ec.main;

import com.dididi.pocket.ec.R;
import com.dididi.pocket.ec.main.mall.HomeItemDelegate;
import com.dididi.pocket.ec.main.message.MessageItemDelegate;
import com.dididi.pocket.ec.main.personal.PersonalItemDelegate;
import com.dididi.pocket.ec.main.shoppingCart.ShoppingCartItemDelegate;
import com.dididi.pocket_core.delegates.bottom.BaseBottomDelegate;
import com.dididi.pocket_core.delegates.bottom.BottomItemDelegate;
import com.dididi.pocket_core.delegates.bottom.BottomTabBean;
import com.dididi.pocket_core.delegates.bottom.IHideBottomBarListener;
import com.dididi.pocket_core.delegates.bottom.ItemFactory;

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
        return R.color.pressButtonColor;
    }

    @Override
    public int setIndexDelegate() {
        return 0;
    }

}
