package com.dididi.pocket_core.delegates.bottom;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.dididi.pocket_core.R;
import com.dididi.pocket_core.delegates.PocketDelegate;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;


/**
 * Created by dididi
 * on 24/07/2018 .
 */

@SuppressWarnings({"FieldCanBeLocal", "MismatchedQueryAndUpdateOfCollection"})
public abstract class BaseBottomDelegate extends PocketDelegate {

    private final ArrayList<BottomTabBean> TAB_BEANS = new ArrayList<>();
    private final ArrayList<BottomItemDelegate> ITEM_DELEGATE = new ArrayList<>();

    private final LinkedHashMap<BottomTabBean, BottomItemDelegate> ITEMS = new LinkedHashMap<>();
    //当前fragment页面
    private int mCurrentDelegate = 0;
    //默认打开fragment页面
    private int mIndexDelegate = 0;
    //设置默认点击tab的颜色
    private int mClickedColor = R.color.pressButtonColor;

    public abstract LinkedHashMap<BottomTabBean, BottomItemDelegate> setItems(ItemFactory factory);

    //设置颜色
    public abstract int setClickedColor();

    //设置默认打开页面
    public abstract int setIndexDelegate();

    @Override
    //初始化delegate
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mIndexDelegate = setIndexDelegate();
        if (setClickedColor() != 0) {
            mClickedColor = setClickedColor();
        }
        final ItemFactory factory = ItemFactory.builder();
        final LinkedHashMap<BottomTabBean, BottomItemDelegate> items = setItems(factory);
        ITEMS.putAll(items);
        //遍历获取map键值对
        for (Map.Entry<BottomTabBean, BottomItemDelegate> item : ITEMS.entrySet()) {
            final BottomTabBean key = item.getKey();
            final BottomItemDelegate value = item.getValue();
            TAB_BEANS.add(key);
            ITEM_DELEGATE.add(value);
        }
    }

    @Override
    public Object setLayout() {
        return null;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {

    }
}
