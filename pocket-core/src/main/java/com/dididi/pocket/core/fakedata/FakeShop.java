package com.dididi.pocket.core.fakedata;

import com.dididi.pocket.core.entity.Goods;
import com.dididi.pocket.core.entity.Shop;

import java.util.HashMap;
import java.util.List;

/**
 * @author dididi
 * @describe 假商店数据
 * @since 17/10/2018
 */

public class FakeShop {

    private static final HashMap<String,List<Shop>> SHOP_MAP = new HashMap<>();

    public static List<Shop> getShop(String shopId){
        return SHOP_MAP.get(shopId);
    }

    public static void init(){
        Shop shop1 = new Shop(true,"张三的店铺").setShopId(1);
        Shop shop2 = new Shop(true,"李四的店铺");
        Shop shop3 = new Shop(true,"王五的店铺");
        Shop shop4 = new Shop(true,"老王的店铺");
        Shop shop5 = new Shop(true,"tony老师的店铺");
        Shop shop6 = new Shop(true,"小芳的店铺");
        Goods goods1 = new Goods();
        Goods goods2 = new Goods();
        Goods goods3 = new Goods();
        Goods goods4 = new Goods();
        Goods goods5 = new Goods();
        Goods goods6 = new Goods();
        Goods goods7 = new Goods();
        Goods goods8 = new Goods();
        Goods goods9 = new Goods();
        Goods goods10 = new Goods();
        Goods goods11 = new Goods();
        Goods goods12 = new Goods();
        Goods goods13 = new Goods();
        Goods goods14 = new Goods();
        Goods goods15 = new Goods();
        Goods goods16 = new Goods();
        Goods goods17 = new Goods();
        Goods goods18 = new Goods();
    }
}
