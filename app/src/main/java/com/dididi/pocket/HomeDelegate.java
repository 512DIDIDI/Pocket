package com.dididi.pocket;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.dididi.pocket_core.delegates.PocketDelegate;


/**
 * Created by dididi
 * on 19/07/2018 .
 */

public class HomeDelegate extends PocketDelegate {

    @Override
    //返回布局id
    public Object setLayout() {
        return R.layout.delegate_home;
    }

    @Override
    //绑定控件
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {

    }
}
