package com.dididi.pocket.core.util;

import android.content.res.Resources;
import android.util.DisplayMetrics;

import com.dididi.pocket.core.app.Pocket;

/**
 * Created by dididi
 * on 23/07/2018 .
 */

public class DimenUtil {
    //返回屏幕宽度
    public static int getScreenWidth() {
        final Resources RESOURCES = Pocket.getApplicationContext().getResources();
        final DisplayMetrics dm = RESOURCES.getDisplayMetrics();
        return dm.widthPixels;
    }

    //返回屏幕高度
    public static int getScreenHeight() {
        final Resources RESOURCES = Pocket.getApplicationContext().getResources();
        final DisplayMetrics dm = RESOURCES.getDisplayMetrics();
        return dm.heightPixels;
    }
}
