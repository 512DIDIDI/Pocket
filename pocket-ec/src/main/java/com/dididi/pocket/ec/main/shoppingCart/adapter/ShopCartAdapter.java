package com.dididi.pocket.ec.main.shoppingCart.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.dididi.pocket.ec.R;
import com.dididi.pocket.ec.item.RoundRectImageView;
import com.dididi.pocket.ec.main.shoppingCart.Listener.OnGoodsPriceListener;
import com.dididi.pocket.core.entity.Goods;
import com.mikepenz.iconics.view.IconicsTextView;

import java.util.List;


/**
 * Created by dididi
 * on 29/07/2018 .
 */

public class ShopCartAdapter extends RecyclerView.Adapter<ShopCartAdapter.ViewHolder> {
    //TODO:这里暂时还有bug 应该考虑同一个店铺下把商品归类到同一个cardView中

    private Context mContext;
    private List<Goods> mGoodsList;
    private OnGoodsPriceListener onGoodsPriceListener;

    public ShopCartAdapter(List<Goods> goods) {
        this.mGoodsList = goods;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (mContext == null) {
            mContext = viewGroup.getContext();
        }
        View itemView = LayoutInflater.from(mContext)
                .inflate(R.layout.item_shopcart_goods_card, viewGroup, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
        final Goods goods = mGoodsList.get(i);
        viewHolder.shopName.setText(goods.getShopName());
        //todo:先写死了传入id 后期再改
        viewHolder.goodsImg.setImageResource(Integer.parseInt(goods.getGoodsImg()));
        viewHolder.goodsName.setText(goods.getGoodsName());
        viewHolder.goodsStyle.setText(goods.getGoodsStyle());
        viewHolder.goodsPrice.setText(String.valueOf(goods.getGoodsPrice()));
        viewHolder.goodsCount.setText(String.valueOf(goods.getGoodsCount()));
        //判断商品是否被选中来决定选中图标的样式
        if (goods.isGoodsSelected()) {
            viewHolder.goodsSelected.setText(R.string.faw_check_circle);
        } else {
            viewHolder.goodsSelected.setText(R.string.faw_circle);
        }
        //判断商店是否选中
        if (goods.isShopSelected()) {
            viewHolder.shopSelected.setText(R.string.faw_check_circle);
        } else {
            viewHolder.shopSelected.setText(R.string.faw_circle);
        }

        //点击选择店铺
        viewHolder.shopSelected.setOnClickListener(view -> {
            int position = viewHolder.getAdapterPosition();
            //点击勾选商店时,改变选择状态
            mGoodsList.get(position)
                    .setShopSelected(!mGoodsList.get(position).isShopSelected());
            for (int i1 = 0; i1 < mGoodsList.size(); i1++) {
                //商店id相同,则该商店下的所有商品都应该与商店勾选情况一致
                if (mGoodsList.get(i1).getShopId() == mGoodsList.get(position).getShopId()) {
                    mGoodsList.get(i1)
                            .setGoodsSelected(mGoodsList.get(position).isShopSelected());
                }
            }
            notifyDataSetChanged();
            //交由外界处理(通知价格变化)
            onGoodsPriceListener.onShopSelected(position);
        });
        //点击店铺名字
        viewHolder.shopName.setOnClickListener(view -> {
            int position = viewHolder.getAdapterPosition();
            Toast.makeText(mContext, "点击进入" + mGoodsList.get(position).getShopName(),
                    Toast.LENGTH_SHORT).show();
        });
        //点击选择商品
        viewHolder.goodsSelected.setOnClickListener(view -> {
            int position = viewHolder.getAdapterPosition();
            //设置商品选择取反
            mGoodsList.get(position)
                    .setGoodsSelected(!mGoodsList.get(position).isGoodsSelected());
            for (int i12 = 0; i12 < mGoodsList.size(); i12++) {
                //遍历获取店铺的第一个商品
                if (mGoodsList.get(i12).isFirst()) {
                    for (int j = 0; j < mGoodsList.size(); j++) {
                        if (mGoodsList.get(j).getShopId() == mGoodsList.get(i12).getShopId()
                                && !mGoodsList.get(j).isGoodsSelected()) {
                            //如果同一家商品中有一个商品未选择,则店铺取消勾选
                            mGoodsList.get(i12).setShopSelected(false);
                            break;
                        } else {
                            mGoodsList.get(i12).setShopSelected(true);
                        }
                    }
                }
            }
            notifyDataSetChanged();
            onGoodsPriceListener.onGoodsSelected(position);
        });
        //点击商品图片
        viewHolder.goodsImg.setOnClickListener(view -> {
            int position = viewHolder.getAdapterPosition();
            Toast.makeText(mContext, "点击进入" + mGoodsList.get(position).getShopName(),
                    Toast.LENGTH_SHORT).show();
        });
        //点击商品名字
        viewHolder.goodsName.setOnClickListener(view -> {
            int position = viewHolder.getAdapterPosition();
            Toast.makeText(mContext, "点击进入" + mGoodsList.get(position).getShopName(),
                    Toast.LENGTH_SHORT).show();
        });
        //点击更改样式
        viewHolder.goodsStyle.setOnClickListener(view -> {
            int position = viewHolder.getAdapterPosition();
            onGoodsPriceListener.onChangeStyle(position);
        });
        //点击减少商品数量
        viewHolder.goodsDecrease.setOnClickListener(view -> {
            int position = viewHolder.getAdapterPosition();
            onGoodsPriceListener.onDecrease(position);
        });
        //点击商品数量更改
        viewHolder.goodsCount.setOnClickListener(view -> {
            int position = viewHolder.getAdapterPosition();
            Toast.makeText(mContext, "商品数量" + mGoodsList.get(position).getGoodsCount(),
                    Toast.LENGTH_SHORT).show();
        });
        //点击增加商品数量
        viewHolder.goodsIncrease.setOnClickListener(view -> {
            int position = viewHolder.getAdapterPosition();
            onGoodsPriceListener.onIncrease(position);
        });
        //点击收藏按钮
        viewHolder.collect.setOnClickListener(view -> {
            int position = viewHolder.getAdapterPosition();
            onGoodsPriceListener.onCollect(position);
        });
        //点击删除按钮
        viewHolder.delete.setOnClickListener(view -> {
            int position = viewHolder.getAdapterPosition();
            onGoodsPriceListener.onDelete(position);
        });
    }

    //获取item数量
    @Override
    public int getItemCount() {
        return mGoodsList == null ? 0 : mGoodsList.size();
    }

    //实例化控件
    static class ViewHolder extends RecyclerView.ViewHolder {

        RelativeLayout mainLayout;
        IconicsTextView shopSelected;
        AppCompatTextView shopName;
        IconicsTextView goodsSelected;
        RoundRectImageView goodsImg;
        AppCompatTextView goodsName;
        AppCompatTextView goodsStyle;
        AppCompatTextView goodsPrice;
        AppCompatTextView goodsCount;
        AppCompatTextView goodsDecrease;
        AppCompatTextView goodsIncrease;
        AppCompatButton collect;
        AppCompatButton delete;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            mainLayout = itemView.findViewById(R.id.item_shopcart_main_layout);
            shopSelected = itemView.findViewById(R.id.item_shopcart_shop_select);
            shopName = itemView.findViewById(R.id.item_shopcart_shop_name);
            goodsSelected = itemView.findViewById(R.id.item_shopcart_goods_select);
            goodsImg = itemView.findViewById(R.id.item_shopcart_goods_img);
            goodsName = itemView.findViewById(R.id.item_shopcart_goods_name);
            goodsStyle = itemView.findViewById(R.id.item_shopcart_goods_style);
            goodsPrice = itemView.findViewById(R.id.item_shopcart_goods_price);
            goodsCount = itemView.findViewById(R.id.item_shopcart_goods_number);
            goodsDecrease = itemView.findViewById(R.id.item_shopcart_goods_decrease);
            goodsIncrease = itemView.findViewById(R.id.item_shopcart_goods_increase);
            collect = itemView.findViewById(R.id.item_shopcart_goods_collect);
            delete = itemView.findViewById(R.id.item_shopcart_goods_delete);
        }
    }

    public OnGoodsPriceListener getOnGoodsPriceListener() {
        return onGoodsPriceListener;
    }

    //设置商品价格监听
    public void setOnGoodsPriceListener(OnGoodsPriceListener onGoodsPriceListener) {
        this.onGoodsPriceListener = onGoodsPriceListener;
    }
}
