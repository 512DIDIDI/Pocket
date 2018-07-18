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

    private Unbinder mUnbinder = null;

    public abstract Object setLayout();

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
            rootView = inflater.inflate((Integer) setLayout(), container, false);
        } else if (setLayout() instanceof View) {
            //如果setLayout()返回的是view
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
        //销毁活动时解绑
        if (mUnbinder != null) {
            mUnbinder.unbind();
        }
    }
}
