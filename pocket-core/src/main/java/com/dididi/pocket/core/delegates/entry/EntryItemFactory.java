package com.dididi.pocket.core.delegates.entry;

import com.dididi.pocket.core.delegates.PocketDelegate;
import com.dididi.pocket.core.ui.item.CircleIconItem;

import java.util.LinkedHashMap;

/**
 * @author dididi
 * @describe grid recyclerView中的入口工厂 负责绑定入口与对应的fragment
 * @since 16/10/2018
 */

public class EntryItemFactory {

    /**
     * 存储入口与fragment
     */
    private final LinkedHashMap<EntryItemBean, PocketDelegate> ITEMS = new LinkedHashMap<>();

    static EntryItemFactory builder() {
        return new EntryItemFactory();
    }

    public final EntryItemFactory addItem(EntryItemBean bean, PocketDelegate delegate) {
        ITEMS.put(bean, delegate);
        return this;
    }

    public final EntryItemFactory addItems(LinkedHashMap<EntryItemBean, PocketDelegate> items) {
        ITEMS.putAll(items);
        return this;
    }

    public final LinkedHashMap<EntryItemBean, PocketDelegate> build() {
        return ITEMS;
    }
}
