package com.dididi.pocket.core.ui.loader;

import android.content.Context;
import android.support.v7.app.AppCompatDialog;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.dididi.pocket.core.R;
import com.dididi.pocket.core.util.DimenUtil;
import com.wang.avi.AVLoadingIndicatorView;
import com.wang.avi.Indicator;

import java.util.ArrayList;

/**
 * Created by dididi
 * on 22/07/2018 .
 */

@SuppressWarnings("WeakerAccess")
public class PocketLoader {
    //缩放倍数
    private static final int LOADER_SIZE_SCALE = 8;
    //偏移量
    private static final int LOADER_OFF_SIZE_SCALE = 10;
    //dialog集合
    private static final ArrayList<AppCompatDialog> DIALOGS = new ArrayList<>();
    //默认loading样式
    public static final Enum<LoaderStyle> DEFAULT_STYLE = LoaderStyle.LineScalePartyIndicator;

    //传入自定义loading
    public static void showLoading(Context context, String type) {
        final AppCompatDialog dialog = new AppCompatDialog(context, R.style.dialog);
        final AVLoadingIndicatorView avLoadingIndicatorView = getLoading(context, type);
        dialog.setContentView(avLoadingIndicatorView);

        int deviceWidth = DimenUtil.getScreenWidth();
        int deviceHeight = DimenUtil.getScreenHeight();
        final Window dialogWindow = dialog.getWindow();

        if (dialogWindow != null) {
            WindowManager.LayoutParams lp = dialogWindow.getAttributes();
            lp.width = deviceWidth / LOADER_SIZE_SCALE;
            lp.height = deviceHeight / LOADER_SIZE_SCALE;
            lp.height = lp.height + deviceHeight / LOADER_OFF_SIZE_SCALE;
            lp.gravity = Gravity.CENTER;
        }
        DIALOGS.add(dialog);
        dialog.show();
    }

    //显示默认loading
    public static void showLoading(Context context) {
        showLoading(context, DEFAULT_STYLE.name());
    }

    //传入key值
    public static void showLoading(Context context, Enum<LoaderStyle> style) {
        showLoading(context, style.name());
    }

    //统一关闭loading
    public static void stopLoading() {
        for (AppCompatDialog dialog : DIALOGS) {
            if (dialog != null) {
                //cancel相比较dismiss有回调方法
                dialog.cancel();
            }
        }
    }

    //获取AVLoadingIndicatorView
    private static AVLoadingIndicatorView getLoading(Context context, String type) {
        final AVLoadingIndicatorView avLoadingIndicatorView = new AVLoadingIndicatorView(context);
        final Indicator indicator = getIndicator(type);
        avLoadingIndicatorView.setIndicator(indicator);
        return avLoadingIndicatorView;
    }

    //获取Indicator
    private static Indicator getIndicator(String name) {
        if (name == null || name.isEmpty()) {
            return null;
        }
        final StringBuilder drawableClassName = new StringBuilder();
        if (!name.contains(".")) {
            final String defaultPackageName = AVLoadingIndicatorView.class.getPackage().getName();
            drawableClassName.append(defaultPackageName)
                    .append(".indicators")
                    .append(".");
        }
        drawableClassName.append(name);
        final Class<?> drawableClass;
        try {
            drawableClass = Class.forName(drawableClassName.toString());
            return (Indicator) drawableClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }
}
