package com.dididi.pocket.ec.main.mall;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.dididi.pocket.ec.R;
import com.dididi.pocket_core.delegates.PocketDelegate;


/**
 * Created by dididi
 * on 05/08/2018 .
 */

public class GoodsListDelegate extends PocketDelegate {
    @Override
    public Object setLayout() {
        return R.layout.delegate_mall_goods_list;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {

    }

    /**
     * 传入GoodsListDelegate的参数
     * @param context 上下文
     * @param goodsBelongTo 商品分类
     */
    public static void startIntent(Context context,String goodsBelongTo){
        Intent intent = new Intent(context,GoodsListDelegate.class);
        intent.putExtra("belongTo",goodsBelongTo);
        context.startActivity(intent);
    }
}
