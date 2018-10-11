package com.dididi.pocket.ec.main.shoppingcart.Listener;

/**
 * Created by dididi
 * on 29/07/2018 .
 */

public interface OnGoodsPriceListener {

    void onDelete(int position);

    void onIncrease(int position);

    void onDecrease(int position);

    void onGoodsSelected(int position);

    void onShopSelected(int position);

    void onCollect(int position);

    void onChangeStyle(int position);
}
