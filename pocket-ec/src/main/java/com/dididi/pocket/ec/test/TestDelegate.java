package com.dididi.pocket.ec.test;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.dididi.pocket.core.delegates.PocketDelegate;
import com.dididi.pocket.ec.R;

/**
 * @author dididi
 * @describe 测试布局
 * @since 25/09/2018
 */

public class TestDelegate extends PocketDelegate {


    @Override
    public Object setLayout() {
        return R.layout.delegate_test;
    }

    @Override
    public void onBindChildView(@Nullable Bundle savedInstanceState, View rootView) {

    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {

    }

    @Override
    public void setSwipeBackEnable(boolean enable) {
        super.setSwipeBackEnable(true);
    }

}
