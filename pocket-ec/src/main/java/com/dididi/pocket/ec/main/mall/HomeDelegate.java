package com.dididi.pocket.ec.main.mall;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.dididi.pocket.ec.R;
import com.dididi.pocket_core.delegates.PocketDelegate;


/**
 * Created by dididi
 * on 24/07/2018 .
 */

public class HomeDelegate extends PocketDelegate {
    @Override
    public Object setLayout() {
        return R.layout.delegate_mall_home;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {

    }
}