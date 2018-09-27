package com.dididi.pocket.core.delegates.bottom;

import java.util.LinkedHashMap;

/**
 * Created by dididi
 * on 25/07/2018 .
 */

public class ItemFactory {
    //简单工厂模式
    /** 将tab与item形成映射,linkedHashMap产生有序map */
    private final LinkedHashMap<BottomTabBean, BottomItemDelegate> ITEMS = new LinkedHashMap<>();

    static ItemFactory builder() {
        return new ItemFactory();
    }

    public final ItemFactory addItem(BottomTabBean tabBean, BottomItemDelegate delegate) {
        ITEMS.put(tabBean, delegate);
        return this;
    }

    public final ItemFactory addItem(LinkedHashMap<BottomTabBean, BottomItemDelegate> items) {
        ITEMS.putAll(items);
        return this;
    }
    /** 返回产品LinkedHashMap<> */
    public final LinkedHashMap<BottomTabBean, BottomItemDelegate> build() {
        return ITEMS;
    }
}
