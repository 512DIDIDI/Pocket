package com.dididi.pocket_core.Entity;

/**
 * Created by dididi
 * on 29/07/2018 .
 */

public class Goods {
    //商品id
    private int goodsId;
    //商店id
    private int shopId;
    private String shopName;
    private int goodsImg;
    private String goodsName;
    private String goodsStyle;
    private float goodsPrice;
    private int goodsCount;
    //商品对应的店铺是否选择
    private boolean isShopSelected = false;
    //商品是否选择
    private boolean isGoodsSelected = false;
    //商品是否处于第一个(用于判断快速判断是否是同一个商店的东西)
    private boolean isFirst = true;
    //商品分类
    private String belongTo;
    //销量
    private int sales;
    //好评率
    private float evaluate;

    public int getSales() {
        return sales;
    }

    public Goods setSales(int sales) {
        this.sales = sales;
        return this;
    }

    public float getEvaluate() {
        return evaluate;
    }

    public Goods setEvaluate(float evaluate) {
        this.evaluate = evaluate;
        return this;
    }

    public Goods setBelongTo(String belongTo) {
        this.belongTo = belongTo;
        return this;
    }

    public Goods setGoodsId(int goodsId) {
        this.goodsId = goodsId;
        return this;
    }

    public Goods setShopId(int shopId) {
        this.shopId = shopId;
        return this;
    }

    public Goods setShopName(String shopName) {
        this.shopName = shopName;
        return this;
    }

    public Goods setGoodsImg(int goodsImg) {
        this.goodsImg = goodsImg;
        return this;
    }

    public Goods setGoodsName(String goodsName) {
        this.goodsName = goodsName;
        return this;
    }

    public Goods setGoodsStyle(String goodsStyle) {
        this.goodsStyle = goodsStyle;
        return this;
    }

    public Goods setGoodsPrice(float goodsPrice) {
        this.goodsPrice = goodsPrice;
        return this;
    }

    public Goods setFirst(boolean first) {
        this.isFirst = first;
        return this;
    }

    public Goods setGoodsCount(int goodsCount) {
        this.goodsCount = goodsCount;
        return this;
    }

    public String getBelongTo() {
        return belongTo;
    }

    public void setShopSelected(boolean shopSelected) {
        isShopSelected = shopSelected;
    }

    public void setGoodsSelected(boolean goodsSelected) {
        isGoodsSelected = goodsSelected;
    }

    public int getGoodsId() {
        return goodsId;
    }

    public int getShopId() {
        return shopId;
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

    public boolean isFirst() {
        return isFirst;
    }
}
