package com.dididi.pocket.ec.main.shoppingcart;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.dididi.pocket.core.delegates.bottom.BottomItemDelegate;
import com.dididi.pocket.core.entity.Goods;
import com.dididi.pocket.core.entity.Shop;
import com.dididi.pocket.ec.R;
import com.dididi.pocket.ec.R2;
import com.dididi.pocket.ec.main.shoppingcart.adapter.ShopCartAdapter;
import com.mikepenz.iconics.view.IconicsTextView;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * @author dididi
 * @describe 购物车页面
 * @since 11/10/2018
 */

public class ShopCartItemDelegate extends BottomItemDelegate
        implements BaseQuickAdapter.OnItemChildClickListener {

    @BindView(R2.id.delegate_shoppingCart_shopcart_recyclerView)
    RecyclerView mRecyclerView = null;
    @BindView(R2.id.delegate_shoppingCart_shopcart_all_price)
    AppCompatTextView mPrice = null;
    @BindView(R2.id.delegate_shoppingCart_shopcart_all_selected)
    IconicsTextView mAllSelect = null;

    private List<Shop> shopList = new ArrayList<>();
    private ShopCartAdapter mAdapter = null;
    private double mTotalPrice = 0;
    private boolean isAllSelected = false;

    /**
     * 底部全选按钮点击事件
     */
    @OnClick({R2.id.delegate_shoppingCart_shopcart_all_selected,
            R2.id.delegate_shoppingCart_shopcart_all_selected_text})
    public void onSelectAll() {
        for (int i = 0; i < shopList.size(); i++) {
            if (shopList.get(i).isHeader) {
                shopList.get(i).setShopSelected(!isAllSelected);
                continue;
            }
            shopList.get(i).t.setGoodsSelected(!isAllSelected);
        }
        isAllSelected = !isAllSelected;
        notifyAllSelectChanged();
        mAdapter.notifyDataSetChanged();
        notifyPriceChanged();
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_shoppingcart_shopcart;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        initData();
        mAdapter = new ShopCartAdapter(R.layout.item_shopcart_goods,
                R.layout.item_shopcart_shop, shopList);
        mAdapter.setOnItemChildClickListener(this);
        mRecyclerView.setAdapter(mAdapter);
    }

    /**
     * adapter子项点击事件处理
     *
     * @param adapter  recyclerView适配器
     * @param view     当前点击的view
     * @param position 当前位置
     */
    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        StringBuilder content = null;
        Shop shop = (Shop) adapter.getItem(position);
        if (null == shop) {
            throw new RuntimeException("shop can not be null");
        }
        int id = view.getId();
        if (id == R.id.item_shopcart_shop_select) {
            //点击选择店铺
            shop.setShopSelected(!shop.isShopSelected());
            selectShop(position);
            adapter.notifyDataSetChanged();
            if (isAllSelected){
                isAllSelected = false;
                notifyAllSelectChanged();
            }
            notifyPriceChanged();
        } else if (id == R.id.item_shopcart_shop_chat) {
            //点击选择发起店主聊天
            content = new StringBuilder();
            content.append("你向")
                    .append(shop.getUserName())
                    .append("发起了聊天");
        } else if (id == R.id.item_shopcart_shop_name) {
            //点击店铺名字
            content = new StringBuilder();
            content.append("你点击了")
                    .append(shop.header)
                    .append("店铺");
        } else if (id == R.id.item_shopcart_goods_select) {
            //点击选择商品
            shop.t.setGoodsSelected(!shop.t.isGoodsSelected());
            adapter.notifyDataSetChanged();
            if (isAllSelected){
                isAllSelected = false;
                notifyAllSelectChanged();
            }
            notifyPriceChanged();
        } else if (id == R.id.item_shopcart_goods_style) {
            //点击更改商品样式
            content = new StringBuilder();
            content.append("你点击了")
                    .append(shop.t.getGoodsName())
                    .append("的样式");
            notifyPriceChanged();
        } else if (id == R.id.item_shopcart_goods_increase) {
            //点击增加商品
            int count = shop.t.getGoodsCount();
            shop.t.setGoodsCount(++count);
            adapter.notifyDataSetChanged();
            notifyPriceChanged();
        } else if (id == R.id.item_shopcart_goods_decrease) {
            //点击减少商品
            int count = shop.t.getGoodsCount();
            if (count == 1) {
                content = new StringBuilder();
                content.append(shop.t.getGoodsName())
                        .append("数量减少为0 已移除商品");
                removeGoods(position);
            }
            shop.t.setGoodsCount(--count);
            adapter.notifyDataSetChanged();
            notifyPriceChanged();
        } else if (id == R.id.item_shopcart_goods_delete) {
            //点击删除商品
            if (position >= 0 && position < shopList.size()) {
                removeGoods(position);
                adapter.notifyDataSetChanged();
            }
            notifyPriceChanged();
        } else if (id == R.id.item_shopcart_goods_collect) {
            //点击收藏商品
            content = new StringBuilder();
            content.append("你收藏了")
                    .append(shop.t.getGoodsName());
        }
        if (null != content) {
            Toast.makeText(getContext(), content.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 移除商品
     *
     * @param position 该商品对应的位置
     */
    private void removeGoods(int position) {
        shopList.remove(position);
        //判断是否要移除店铺item
        if (shopList.get(position - 1).isHeader) {
            //如果前一项是店铺item
            if (position == shopList.size()) {
                //并且当前是最后一项 删除店铺item
                shopList.remove(position - 1);
            } else if (shopList.get(position).isHeader) {
                //或者后一项是店铺item 删除店铺item
                shopList.remove(position - 1);
            }
        }
    }

    /**
     * 计算总价
     */
    private void notifyPriceChanged() {
        //每次计算前先清空总价
        mTotalPrice = 0;
        //遍历list中的每个商品(0必定是店铺名，所以0可不必加入循环)
        for (int i = 1; i < shopList.size(); i++) {
            //防止出现t空指针
            if (shopList.get(i).isHeader) {
                continue;
            }
            if (shopList.get(i).t.isGoodsSelected()) {
                double price = shopList.get(i).t.getGoodsPrice();
                int count = shopList.get(i).t.getGoodsCount();
                mTotalPrice += price * count;
            }
        }
        //改变底部结算栏文字
        mPrice.setText(R.string.yuan);
        mPrice.append(new BigDecimal(mTotalPrice).stripTrailingZeros().toPlainString());
    }

    /**
     * 实现点击店铺全选该店铺下的商品
     *
     * @param position 从何处开始遍历
     */
    private void selectShop(int position) {
        Shop shop = shopList.get(position);
        //遍历list
        for (int i = position; i < shopList.size(); i++) {
            //如果是店铺item，跳过当前循环
            if (shopList.get(i).isHeader) {
                continue;
            }
            //判断商品的商店id是否等于商店id
            if (shopList.get(i).t.getShopId() == shop.getShopId()) {
                //相等则选择该商品
                shopList.get(i).t.setGoodsSelected(shop.isShopSelected());
            } else {
                break;
            }
        }
    }

    private void notifyAllSelectChanged(){
        if (isAllSelected) {
            mAllSelect.setText(R.string.faw_check_circle);
        } else {
            mAllSelect.setText(R.string.faw_circle);
        }
    }

    private void initData() {
        Goods cat = new Goods()
                .setShopId(1)
                .setShopName("我的店铺名很长价格很贵")
                .setGoodsImg(String.valueOf(R.drawable.cat))
                .setGoodsName("我是一只大猫啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊")
                .setGoodsStyle("橘喵")
                .setGoodsPrice(23333333)
                .setGoodsCount(1);
        Goods cat2 = new Goods()
                .setShopId(1)
                .setShopName("我的店铺名很长价格很贵")
                .setGoodsImg(String.valueOf(R.drawable.cat))
                .setGoodsName("我是一只假黑猫")
                .setGoodsStyle("黑猫")
                .setGoodsPrice(100)
                .setGoodsCount(1);
        Goods guitar = new Goods()
                .setShopId(2)
                .setShopName("我也是一家店铺")
                .setGoodsImg(String.valueOf(R.drawable.guitar))
                .setGoodsName("我是一把小吉他")
                .setGoodsStyle("大吉它")
                .setGoodsPrice(666)
                .setGoodsCount(2);
        Goods guitar2 = new Goods()
                .setShopId(2)
                .setShopName("我也是一家店铺")
                .setGoodsImg(String.valueOf(R.drawable.guitar))
                .setGoodsName("我是一把大吉他")
                .setGoodsStyle("真的大吉它")
                .setGoodsPrice(555)
                .setGoodsCount(2);
        Goods flower = new Goods()
                .setShopId(3)
                .setShopName("我还是一家店铺")
                .setGoodsImg(String.valueOf(R.drawable.flower))
                .setGoodsName("菊花")
                .setGoodsStyle("小菊花")
                .setGoodsPrice(524)
                .setGoodsCount(1);
        Goods flower2 = new Goods()
                .setShopId(3)
                .setShopName("我还是一家店铺")
                .setGoodsImg(String.valueOf(R.drawable.flower))
                .setGoodsName("菊花")
                .setGoodsStyle("大菊花")
                .setGoodsPrice(524)
                .setGoodsCount(1);
        shopList.add(new Shop(true, "我的店铺名很长价格很贵啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊")
                .setUserName("dididi").setShopId(1));
        shopList.add(new Shop(cat));
        shopList.add(new Shop(cat2));
        shopList.add(new Shop(true, "我也是一家店铺").setUserName("lalala").setShopId(2));
        shopList.add(new Shop(guitar));
        shopList.add(new Shop(guitar2));
        shopList.add(new Shop(true, "我还是一家店铺").setUserName("hahaha").setShopId(3));
        shopList.add(new Shop(flower));
        shopList.add(new Shop(flower2));
    }
}
