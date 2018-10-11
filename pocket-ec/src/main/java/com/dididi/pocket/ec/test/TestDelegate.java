package com.dididi.pocket.ec.test;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.dididi.pocket.core.delegates.PocketDelegate;
import com.dididi.pocket.ec.R;
import com.dididi.pocket.ec.R2;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

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
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {

    }
}
