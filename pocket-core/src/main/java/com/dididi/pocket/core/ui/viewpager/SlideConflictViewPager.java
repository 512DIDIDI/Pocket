package com.dididi.pocket.core.ui.viewpager;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;


/**
 * @author dididi
 * @describe
 * @since 17/10/2018
 */

public class SlideConflictViewPager extends ViewPager {

    private int downX = 0;

    public SlideConflictViewPager(@NonNull Context context) {
        super(context);
    }

    public SlideConflictViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        return super.onTouchEvent(ev);
    }
}
