package com.dididi.pocket.core.delegates;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;

import com.dididi.pocket.core.R;
import com.dididi.pocket.core.activities.ProxyActivity;
import com.gyf.immersionbar.ImmersionBar;

import me.yokeyword.fragmentation.ExtraTransaction;
import me.yokeyword.fragmentation.ISupportFragment;
import me.yokeyword.fragmentation.SupportFragmentDelegate;
import me.yokeyword.fragmentation.anim.FragmentAnimator;
import me.yokeyword.fragmentation_swipeback.SwipeBackFragment;

/**
 * Created by dididi
 * on 18/07/2018 .
 */

public abstract class BaseDelegate extends SwipeBackFragment implements ISupportFragment {
    //fragment基础类

    private final SupportFragmentDelegate DELEGATE = new SupportFragmentDelegate(this);


    /**
     * 抽象方法获取子类布局(可能传入view也可能是id，所以采用Object)
     */
    public abstract Object setLayout();

    /**
     * 抽象方法绑定子控件(如NavigationView当中的item控件)
     */
    public abstract void onBindChildView(@Nullable Bundle savedInstanceState,
                                         View rootView);

    /**
     * 绑定一般控件使用
     */
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
        if (null == rootView) {
            throw new RuntimeException("请检查setLayout()是否为null");
        }
        //绑定视图
        onBindChildView(savedInstanceState, rootView);
        setSwipeBackEnable(false);
        return attachToSwipeBack(rootView);
    }

    /**
     * immersionBar适配方案
     */
    protected int getTitleBarId() {
        return 0;
    }

    protected int setStatusBarView() {
        return 0;
    }

    public void initImmersionBar() {
        ImmersionBar.with(this).keyboardEnable(true).init();
    }

    private boolean isImmersionBarEnabled() {
        return true;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        onBindView(savedInstanceState, view);
        View statusBarView = view.findViewById(setStatusBarView());
        ImmersionBar.setStatusBarView(mActivity, statusBarView);
        View titleBarView = view.findViewById(getTitleBarId());
        ImmersionBar.setTitleBar(mActivity,titleBarView);
    }

    public final ProxyActivity getProxyActivity() {
        return (ProxyActivity) mActivity;
    }

    protected FragmentActivity mActivity;

    @Override
    public SupportFragmentDelegate getSupportDelegate() {
        return DELEGATE;
    }

    @Override
    public ExtraTransaction extraTransaction() {
        return DELEGATE.extraTransaction();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        DELEGATE.onAttach((Activity) context);
        mActivity = DELEGATE.getActivity();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DELEGATE.onCreate(savedInstanceState);
    }

    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        return DELEGATE.onCreateAnimation(transit, enter, nextAnim);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        DELEGATE.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        DELEGATE.onSaveInstanceState(outState);
    }

    @Override
    public void onResume() {
        super.onResume();
        DELEGATE.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        DELEGATE.onPause();
    }

    @Override
    public void onDestroyView() {
        DELEGATE.onDestroyView();
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        DELEGATE.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        DELEGATE.onHiddenChanged(hidden);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        DELEGATE.setUserVisibleHint(isVisibleToUser);
    }

    @Override
    public void onEnterAnimationEnd(Bundle savedInstanceState) {
        DELEGATE.onEnterAnimationEnd(savedInstanceState);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        DELEGATE.onLazyInitView(savedInstanceState);
    }

    @Override
    public void onSupportVisible() {
        DELEGATE.onSupportVisible();
        if (isImmersionBarEnabled()) {
            initImmersionBar();
        }
    }

    @Override
    public void onSupportInvisible() {
        DELEGATE.onSupportInvisible();
    }

    @Override
    public FragmentAnimator onCreateFragmentAnimator() {
        return DELEGATE.onCreateFragmentAnimator();
    }

    @Override
    public FragmentAnimator getFragmentAnimator() {
        return DELEGATE.getFragmentAnimator();
    }

    @Override
    public void setFragmentAnimator(FragmentAnimator fragmentAnimator) {
        DELEGATE.setFragmentAnimator(fragmentAnimator);
    }

    @Override
    public boolean onBackPressedSupport() {
        return DELEGATE.onBackPressedSupport();
    }


    @Override
    public void setFragmentResult(int resultCode, Bundle bundle) {
        DELEGATE.setFragmentResult(resultCode, bundle);
    }


    @Override
    public void onFragmentResult(int requestCode, int resultCode, Bundle data) {
        DELEGATE.onFragmentResult(requestCode, resultCode, data);
    }

    @Override
    public void post(Runnable runnable) {

    }

    @Override
    public void onNewBundle(Bundle args) {
        DELEGATE.onNewBundle(args);
    }

    @Override
    public void putNewBundle(Bundle newBundle) {
        DELEGATE.putNewBundle(newBundle);
    }


    @Override
    public void start(ISupportFragment toFragment) {
        DELEGATE.start(toFragment);
    }

    @Override
    public void start(final ISupportFragment toFragment, @LaunchMode int launchMode) {
        DELEGATE.start(toFragment, launchMode);
    }

}
