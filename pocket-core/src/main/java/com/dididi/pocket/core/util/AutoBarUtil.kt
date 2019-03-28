package com.dididi.pocket.core.util

import android.app.Activity
import android.content.Context
import android.os.Build
import android.view.*
import android.widget.FrameLayout
import androidx.annotation.ColorInt


/**
 * @author dididi(叶超)
 * @email yc512yc@163.com
 * @since 17/03/2019
 * @describe
 */

class AutoBarUtil {

    companion object {

        @ColorInt
        const val COLOR_TOOLBAR = 0xFF454553
        @ColorInt
        const val COLOR_GRAY = 0xFFE0E0E0

        fun changeBarColor(activity: Activity,@ColorInt color: Int) {
            val window = activity.window
            when {
                //适配android6.0以上设备
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.M -> {
                    //取消设置透明状态栏，使ContentView内容不再覆盖状态栏。
                    window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                    window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
                    //必须设置此flag才能改变 statusBarColor
                    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                    window.statusBarColor = color
                    window.navigationBarColor = color
                    val decor = window.decorView
                    var ui = decor.systemUiVisibility
                    ui = if (color == COLOR_GRAY.toInt()){
                        ui or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                    }else{
                        ui and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv()
                    }
                    decor.systemUiVisibility = ui
                }
                //适配android5.0以上设备
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP -> {
                    //取消设置透明状态栏，使ContentView内容不再覆盖状态栏。
                    window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                    window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
                    //必须设置此flag才能改变 statusBarColor
                    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                    window.statusBarColor = color
                    window.navigationBarColor = color
                }
                //适配android4.4以上设备
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT -> {
                    //设置状态栏为半透明，
                    window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                    //向Window窗口的decorView添加一个view，让他覆盖系统状态栏，
                    //改变view颜色即达到改变状态栏颜色。
                    val decorView = window.decorView as ViewGroup
                    val statusBarView = View(window.context)
                    val statusBarHeight = getStatusBarHeight(window.context)
                    val params =
                            FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, statusBarHeight)
                    params.gravity = Gravity.TOP
                    statusBarView.layoutParams = params
                    statusBarView.setBackgroundColor(color)
                    decorView.addView(statusBarView)
                    //内嵌activity的布局，使之不会被状态栏覆盖
                    val contentView = window.findViewById<ViewGroup>(Window.ID_ANDROID_CONTENT)
                    val childView = contentView.getChildAt(0)
                    if (childView != null) {
                        childView.fitsSystemWindows = true
                    }
                }
            }
        }

        //获取状态栏高度
        private fun getStatusBarHeight(context: Context): Int {
            var statusBarHeight = 0
            val res = context.resources
            val resourceId =
                    res.getIdentifier("status_bar_height", "dimen", "android")
            if (resourceId > 0) {
                statusBarHeight = res.getDimensionPixelSize(resourceId)
            }
            return statusBarHeight
        }
    }


}