package com.dididi.pocket_core.delegates;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.yokeyword.fragmentation_swipeback.SwipeBackFragment;


/**
 * Created by dididi
 * on 18/07/2018 .
 */

public abstract class BaseDelegate extends SwipeBackFragment {
    //fragment基础类

    private Unbinder mUnbinder = null;
    //抽象方法获取子类布局(可能传入view也可能是id，所以采用Object)
    public abstract Object setLayout();
    //抽象方法绑定控件
    public abstract void onBindView(@Nullable Bundle savedInstanceState,
                                    View rootView);

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = null;
        //如果setLayout()返回的是Integer(即返回layout的id)
        if (setLayout() instanceof Integer) {
            //加载布局id
            rootView = inflater.inflate((Integer) setLayout(), container, false);
        } else if (setLayout() instanceof View) {
            //如果setLayout()返回的是view，直接等于view
            rootView = (View) setLayout();
        }
        if (rootView != null) {
            //绑定视图
            mUnbinder = ButterKnife.bind(this, rootView);
            onBindView(savedInstanceState, rootView);
        }
        return rootView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //销毁活动时解绑(在fragment中需要解绑)
        if (mUnbinder != null) {
            mUnbinder.unbind();
        }
    }
}
