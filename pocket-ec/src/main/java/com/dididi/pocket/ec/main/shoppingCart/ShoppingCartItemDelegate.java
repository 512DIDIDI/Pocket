package com.dididi.pocket.ec.main.shoppingCart;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.dididi.pocket.ec.R;
import com.dididi.pocket.ec.R2;
import com.dididi.pocket.ec.item.SearchBarItem;
import com.dididi.pocket_core.delegates.bottom.BottomItemDelegate;

import butterknife.BindView;


/**
 * Created by dididi
 * on 24/07/2018 .
 */

public class ShoppingCartItemDelegate extends BottomItemDelegate {

    @BindView(R2.id.shop_cart_item_searchBar)
    SearchBarItem mSearchBar = null;

    @Override
    public Object setLayout() {
        return R.layout.delegate_shoppingcart_shopCart;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        mSearchBar.setLeftIconGone();
    }
}
