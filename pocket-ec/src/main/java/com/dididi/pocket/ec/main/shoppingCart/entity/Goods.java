package com.dididi.pocket.ec.main.shoppingCart.entity;

/**
 * Created by dididi
 * on 29/07/2018 .
 */

public class Goods {

    private String shopName;
    private int goodsImg;
    private String goodsName;
    private String goodsStyle;
    private float goodsPrice;
    private int goodsCount;
    //设置店铺选择文字
    private String shopSelected;
    //设置商品选择文字
    private String goodsSelected;
    //商品对应的店铺是否选择
    private boolean isShopSelected = false;
    //商品是否选择
    private boolean isGoodsSelected = false;

    public Goods(String shopName, int goodsImg, String goodsName,
                 String goodsStyle, float goodsPrice, int goodsCount) {
        this.shopName = shopName;
        this.goodsImg = goodsImg;
        this.goodsName = goodsName;
        this.goodsStyle = goodsStyle;
        this.goodsPrice = goodsPrice;
        this.goodsCount = goodsCount;
    }

    public void setGoodsCount(int goodsCount) {
        this.goodsCount = goodsCount;
    }

    public void setShopSelected(boolean shopSelected) {
        isShopSelected = shopSelected;
    }

    public void setGoodsSelected(boolean goodsSelected) {
        isGoodsSelected = goodsSelected;
    }

    public void setShopSelected(String shopSelected) {
        this.shopSelected = shopSelected;
    }

    public void setGoodsSelected(String goodsSelected) {
        this.goodsSelected = goodsSelected;
    }

    public String getShopSelected() {
        return shopSelected;
    }

    public String getGoodsSelected() {
        return goodsSelected;
    }

    public String getShopName() {
        return shopName;
    }

    public int getGoodsImg() {
        return goodsImg;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public String getGoodsStyle() {
        return goodsStyle;
    }

    public float getGoodsPrice() {
        return goodsPrice;
    }

    public int getGoodsCount() {
        return goodsCount;
    }

    public boolean isShopSelected() {
        return isShopSelected;
    }

    public boolean isGoodsSelected() {
        return isGoodsSelected;
    }
}
