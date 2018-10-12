package com.dididi.pocket.core.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.math.BigDecimal;

/**
 * Created by dididi
 * on 29/07/2018 .
 */

public class Goods implements Parcelable {
    /** 商品 id*/
    private long goodsId;
    /** 商店 id*/
    private long shopId;
    /** 商店名字 */
    private String shopName;
    /** 商品图片 */
    private String goodsImg;
    /** 商品名字 */
    private String goodsName;
    /** 商品样式 */
    private String goodsStyle;
    /** 商品价格 */
    private double goodsPrice;
    /** 商品数量 */
    private int goodsCount;
    /** 商品对应的店铺是否选择 */
    private boolean isShopSelected = false;
    /** 商品是否选择 */
    private boolean isGoodsSelected = false;
    /** 商品是否处于第一个(用于判断快速判断是否是同一个商店的东西) */
    private boolean isFirst = true;
    /** 商品分类 */
    private String belongTo;
    /** 销量 */
    private int sales = 0;
    /** 好评率 */
    private float evaluate = 0;

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

    public Goods setGoodsId(long goodsId) {
        this.goodsId = goodsId;
        return this;
    }

    public Goods setShopId(long shopId) {
        this.shopId = shopId;
        return this;
    }

    public Goods setShopName(String shopName) {
        this.shopName = shopName;
        return this;
    }

    public Goods setGoodsImg(String goodsImg) {
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

    public Goods setGoodsPrice(double goodsPrice) {
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

    public long getGoodsId() {
        return goodsId;
    }

    public long getShopId() {
        return shopId;
    }

    public String getShopName() {
        return shopName;
    }

    public String getGoodsImg() {
        return goodsImg;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public String getGoodsStyle() {
        return goodsStyle;
    }

    public double getGoodsPrice() {
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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.goodsId);
        dest.writeLong(this.shopId);
        dest.writeString(this.shopName);
        dest.writeString(this.goodsImg);
        dest.writeString(this.goodsName);
        dest.writeString(this.goodsStyle);
        dest.writeDouble(this.goodsPrice);
        dest.writeInt(this.goodsCount);
        dest.writeByte(this.isShopSelected ? (byte) 1 : (byte) 0);
        dest.writeByte(this.isGoodsSelected ? (byte) 1 : (byte) 0);
        dest.writeByte(this.isFirst ? (byte) 1 : (byte) 0);
        dest.writeString(this.belongTo);
        dest.writeInt(this.sales);
        dest.writeFloat(this.evaluate);
    }

    public Goods() {
    }

    protected Goods(Parcel in) {
        this.goodsId = in.readInt();
        this.shopId = in.readInt();
        this.shopName = in.readString();
        this.goodsImg = in.readString();
        this.goodsName = in.readString();
        this.goodsStyle = in.readString();
        this.goodsPrice = in.readFloat();
        this.goodsCount = in.readInt();
        this.isShopSelected = in.readByte() != 0;
        this.isGoodsSelected = in.readByte() != 0;
        this.isFirst = in.readByte() != 0;
        this.belongTo = in.readString();
        this.sales = in.readInt();
        this.evaluate = in.readFloat();
    }

    public static final Parcelable.Creator<Goods> CREATOR = new Parcelable.Creator<Goods>() {
        @Override
        public Goods createFromParcel(Parcel source) {
            return new Goods(source);
        }

        @Override
        public Goods[] newArray(int size) {
            return new Goods[size];
        }
    };
}
