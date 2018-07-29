package com.dididi.pocket.ec.main.shoppingCart.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.widget.ImageViewCompat;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.dididi.pocket.ec.R;
import com.dididi.pocket.ec.item.CountItem;
import com.dididi.pocket.ec.main.shoppingCart.Listener.OnGoodsPriceListener;
import com.dididi.pocket.ec.main.shoppingCart.entity.Goods;
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
        if (mContext == null){
            mContext = viewGroup.getContext();
        }
        View itemView = LayoutInflater.from(mContext)
                .inflate(R.layout.item_shopCart_goods_card,viewGroup,false);
        final ViewHolder holder = new ViewHolder(itemView);
        //点击选择店铺
        holder.shopSelected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                mGoodsList.get(position).setShopSelected(true);
                mGoodsList.get(position).setShopSelected("{faw-check-circle}");
                onGoodsPriceListener.onShopSelected(position);
            }
        });
        //点击店铺名字
        holder.shopName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        //点击选择商品
        holder.goodsSelected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                mGoodsList.get(position).setGoodsSelected(true);
                mGoodsList.get(position).setGoodsSelected("{faw-check-circle}");
                onGoodsPriceListener.onGoodsSelected(position);
            }
        });
        //点击商品图片
        holder.goodsImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        //点击商品名字
        holder.goodsName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        //点击更改样式
        holder.goodsStyle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        //点击商品数量更改
        holder.goodsCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                if (view == holder.goodsCount.getChildAt(0)){
                    onGoodsPriceListener.onDecrease(position);
                }else if (view == holder.goodsCount.getChildAt(1)){

                }else if (view == holder.goodsCount.getChildAt(2)){
                    onGoodsPriceListener.onIncrease(position);
                }
            }
        });
        //点击收藏按钮
        holder.collect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                onGoodsPriceListener.onCollect(position);
            }
        });
        //点击删除按钮
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                onGoodsPriceListener.onDelete(position);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Goods goods = mGoodsList.get(i);
        viewHolder.shopSelected.setText(goods.getShopSelected());
        viewHolder.shopName.setText(goods.getShopName());
        viewHolder.goodsSelected.setText(goods.getGoodsSelected());
        viewHolder.goodsImg.setImageResource(goods.getGoodsImg());
        viewHolder.goodsName.setText(goods.getGoodsName());
        viewHolder.goodsStyle.setText(goods.getGoodsStyle());
        viewHolder.goodsPrice.setText((int) goods.getGoodsPrice());
    }

    @Override
    public int getItemCount() {
        return mGoodsList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        RelativeLayout mainLayout;
        IconicsTextView shopSelected;
        AppCompatTextView shopName;
        IconicsTextView goodsSelected;
        AppCompatImageView goodsImg;
        AppCompatTextView goodsName;
        AppCompatTextView goodsStyle;
        AppCompatTextView goodsPrice;
        CountItem goodsCount;
        AppCompatButton collect;
        AppCompatButton delete;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            mainLayout = itemView.findViewById(R.id.item_shopCart_main_layout);
            shopSelected = itemView.findViewById(R.id.item_shopCart_shop_select);
            shopName = itemView.findViewById(R.id.item_shopCart_shop_name);
            goodsSelected = itemView.findViewById(R.id.item_shopCart_goods_select);
            goodsImg = itemView.findViewById(R.id.item_shopCart_goods_img);
            goodsName = itemView.findViewById(R.id.item_shopCart_goods_name);
            goodsStyle = itemView.findViewById(R.id.item_shopCart_goods_style);
            goodsPrice = itemView.findViewById(R.id.item_shopCart_goods_price);
            goodsCount = itemView.findViewById(R.id.item_shopCart_count);
            collect = itemView.findViewById(R.id.item_shopCart_goods_collect);
            delete = itemView.findViewById(R.id.item_shopCart_goods_delete);
        }
    }

    public OnGoodsPriceListener getOnGoodsPriceListener() {
        return onGoodsPriceListener;
    }

    public void setOnGoodsPriceListener(OnGoodsPriceListener onGoodsPriceListener) {
        this.onGoodsPriceListener = onGoodsPriceListener;
    }
}
