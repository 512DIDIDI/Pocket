package com.dididi.pocket.core.entity;

import com.chad.library.adapter.base.entity.SectionEntity;

/**
 * @author dididi
 * @describe 商店实体类
 * @since 26/09/2018
 */

public class Shop extends SectionEntity<Goods> {

    /** 商店id1 */
    private long shopId;

    /** 商店是否选择 */
    private boolean isShopSelected;

    private long userId;

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
}
