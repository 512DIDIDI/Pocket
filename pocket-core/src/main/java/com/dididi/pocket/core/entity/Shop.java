package com.dididi.pocket.core.entity;

import com.chad.library.adapter.base.entity.SectionEntity;

/**
 * @author dididi
 * @describe 商店实体类
 * @since 26/09/2018
 */

public class Shop extends SectionEntity<Goods> {

    /** 商店id */
    private long shopId;

    /** 商店是否选择 */
    private boolean isShopSelected;

    /** 商店图片 */
    private String shopImg;

    /** 店主id */
    private long userId;

    /** 销量 */
    private int sales = 0;

    /** 好评率 */
    private float evaluate = 0;

    /** 最低价格 */
    private double minPrice = 0;

    /** 最高价格 */
    private double maxPrice = 0;

    public Shop(boolean isHeader, String header) {
        super(isHeader, header);
    }

    public Shop(Goods goods) {
        super(goods);
    }

    public long getShopId() {
        return shopId;
    }

    public Shop setShopId(long shopId) {
        this.shopId = shopId;
        return this;
    }

    public boolean isShopSelected() {
        return isShopSelected;
    }

    public Shop setShopSelected(boolean shopSelected) {
        isShopSelected = shopSelected;
        return this;
    }

    public long getUserId() {
        return userId;
    }

    public Shop setUserId(long userId) {
        this.userId = userId;
        return this;
    }

    public int getSales() {
        return sales;
    }

    public Shop setSales(int sales) {
        this.sales = sales;
        return this;
    }

    public float getEvaluate() {
        return evaluate;
    }

    public Shop setEvaluate(float evaluate) {
        this.evaluate = evaluate;
        return this;
    }

    public double getMinPrice() {
        return minPrice;
    }

    public Shop setMinPrice(double minPrice) {
        this.minPrice = minPrice;
        return this;
    }

    public double getMaxPrice() {
        return maxPrice;
    }

    public Shop setMaxPrice(double maxPrice) {
        this.maxPrice = maxPrice;
        return this;
    }

    public String getShopImg() {
        return shopImg;
    }

    public Shop setShopImg(String shopImg) {
        this.shopImg = shopImg;
        return this;
    }

}
