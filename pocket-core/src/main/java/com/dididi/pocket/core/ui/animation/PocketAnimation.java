package com.dididi.pocket.core.ui.animation;

import android.content.Context;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.dididi.pocket.core.R;

/**
 * @author dididi
 * @describe 动画集合
 * @since 25/09/2018
 */

public final class PocketAnimation {

    public static Animation fadeIn(Context context){
        return AnimationUtils.loadAnimation(context,R.anim.fade_in);
    }

    public static Animation fadeOut(Context context){
        return AnimationUtils.loadAnimation(context,R.anim.fade_out);
    }
}
