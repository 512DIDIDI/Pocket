package com.dididi.pocket.ec.main.mall.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.dididi.pocket.ec.R;
import com.dididi.pocket_core.Entity.Goods;
import com.dididi.pocket_core.ui.GlideApp;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by dididi
 * on 06/08/2018 .
 */

public class GoodsAdapter extends RecyclerView.Adapter<GoodsAdapter.ViewHolder> {

    private List<Goods> mGoodsList;
    private Context mContext;

    public GoodsAdapter(ArrayList<Goods> goods) {
        this.mGoodsList = goods;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (mContext == null) {
            mContext = viewGroup.getContext();
        }
        View itemView = LayoutInflater.from(mContext)
                .inflate(R.layout.delegate_mall_goods_list, viewGroup, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final Goods goods = mGoodsList.get(i);
        GlideApp.with(mContext)
                .load(goods.getGoodsImg())
                .into(viewHolder.goodsImg);
        viewHolder.goodsName.setText(goods.getGoodsName());
        viewHolder.goodsPrice.setText(String.valueOf(goods.getGoodsPrice()));
        viewHolder.goodsSales.setText(goods.getSales());
        viewHolder.goodsEvaluate.setText(String.valueOf(goods.getEvaluate()));
        viewHolder.shopName.setText(goods.getShopName());
        //点击进入商店
        viewHolder.enterShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, "进入" + goods.getShopName(),
                        Toast.LENGTH_SHORT).show();
            }
        });
        viewHolder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, "进入" + goods.getGoodsName() + "详情页",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mGoodsList.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout mainLayout;
        AppCompatImageView goodsImg;
        AppCompatTextView goodsName;
        AppCompatTextView goodsPrice;
        AppCompatTextView goodsSales;
        AppCompatTextView goodsEvaluate;
        AppCompatTextView shopName;
        AppCompatTextView enterShop;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            mainLayout = itemView.findViewById(R.id.item_mall_goods_list_layout);
            goodsImg = itemView.findViewById(R.id.item_mall_goods_list_img);
            goodsName = itemView.findViewById(R.id.item_mall_goods_list_name);
            goodsPrice = itemView.findViewById(R.id.item_mall_goods_list_price);
            goodsSales = itemView.findViewById(R.id.item_mall_goods_list_sales);
            goodsEvaluate = itemView.findViewById(R.id.item_mall_goods_list_evaluate);
            shopName = itemView.findViewById(R.id.item_mall_goods_list_shop);
            enterShop = itemView.findViewById(R.id.item_mall_goods_list_enter);
        }
    }
}
