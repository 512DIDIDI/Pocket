package com.dididi.pocket.core.ui.animation

import android.content.Context
import android.support.design.widget.CoordinatorLayout
import android.support.design.widget.FloatingActionButton
import android.support.v4.view.ViewCompat
import android.util.AttributeSet
import android.util.Log
import android.view.View


/**
 * @author dididi(叶超)
 * @email yc512yc@163.com
 * @since 28/04/2019
 * @describe
 */

class FabScrollBehavior : FloatingActionButton.Behavior {

    constructor(context: Context,attrs:AttributeSet):super(context,attrs)

    override fun onStartNestedScroll(coordinatorLayout: CoordinatorLayout,
                                     child: FloatingActionButton, directTargetChild: View,
                                     target: View, axes: Int, type: Int): Boolean {
        return axes == ViewCompat.SCROLL_AXIS_VERTICAL
    }

    override fun onNestedScroll(coordinatorLayout: CoordinatorLayout,
                                child: FloatingActionButton, target: View, dxConsumed: Int,
                                dyConsumed: Int, dxUnconsumed: Int, dyUnconsumed: Int, type: Int) {
        super.onNestedScroll(coordinatorLayout, child, target,
                dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, type)
        //todo:方法无效
        if (dyConsumed > 0){
            Log.d("FabScrollBehavior","down")
            PocketAnimation.setAlphaAnimation(child,1f,0f,0L,300L,false)
        }else if (dyConsumed <0){
            Log.d("FabScrollBehavior","up")
            PocketAnimation.setAlphaAnimation(child,0f,1f,0L,300L,true)
        }
    }
}