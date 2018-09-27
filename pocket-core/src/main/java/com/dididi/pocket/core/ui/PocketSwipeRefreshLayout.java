package com.dididi.pocket.core.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;


/**
 * Created by dididi
 * on 26/07/2018 .
 */

public class PocketSwipeRefreshLayout extends SwipeRefreshLayout {
    /** 解决SwipeRefreshLayout与ViewPager冲突问题 */
    private float mStartX = 0;
    private float mStartY = 0;
    /** 记录ViewPager是否被拖拉 */
    private boolean mIsVpDrag;
    private final int mTouchSlop;
    public PocketSwipeRefreshLayout(@NonNull Context context,
                                    @Nullable AttributeSet attrs) {
        super(context, attrs);
        //获取滑动距离范围
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                //记录手指按下的位置
                mStartX = ev.getX();
                mStartY = ev.getY();
                mIsVpDrag = false;
                break;
            case MotionEvent.ACTION_MOVE:
                //如果ViewPager正在拖拽,则不拦截ViewPager事件
                if (mIsVpDrag) {
                    return false;
                }
                float endY = ev.getY();
                float endX = ev.getX();
                float distanceX = Math.abs(endX - mStartX);
                float distanceY = Math.abs(endY - mStartY);
                //滑动x位移大于y位移时,不拦截ViewPager事件
                if (distanceX > mTouchSlop && distanceX > distanceY){
                    mIsVpDrag = true;
                    return false;
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                mIsVpDrag = false;
                break;
            default:
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }
}
