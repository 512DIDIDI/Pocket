package com.dididi.pocket.core.ui.animation;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.view.View;
import android.view.animation.OvershootInterpolator;

import com.blankj.utilcode.util.SizeUtils;

import io.reactivex.annotations.NonNull;

/**
 * @author dididi
 * @describe 动画效果封装
 * @since 25/09/2018
 */

public final class PocketAnimation {

    /**
     * 动画关键字
     */
    public static final String TRANSLATION_Y = "translationY";
    public static final String TRANSLATION_X = "translationX";
    public static final String ROTATION_Y = "rotationY";
    public static final String ROTATION_X = "rotationX";

    /**
     * 平移动画
     * translationDistance指的是应该平移多少距离
     */
    public static void setTranslationAnimation(View view, String translation,
                                               float startTranslation, float translationDistance,
                                               long delayMills, long duration,boolean isOvershoot) {
        ObjectAnimator animator;
        animator = ObjectAnimator.ofFloat(view, translation, startTranslation,
                translationDistance).setDuration(duration);
        animator.setStartDelay(delayMills);
        if (isOvershoot){
            animator.setInterpolator(new OvershootInterpolator(1.5f));
        }
        animator.start();
    }

    /**
     * 透明动画
     * 最后一个isVisible是为了防止alpha为0时控件依然可被点击
     */
    public static void setAlphaAnimation(final View view, float startAlpha,
                                         float endAlpha, long delayMills,
                                         long duration, boolean isVisible) {
        ObjectAnimator animator;
        animator = ObjectAnimator.ofFloat(view, "alpha", startAlpha, endAlpha);
        animator.setDuration(duration).setStartDelay(delayMills);
        animator.start();
        //透明度动画伴随着页面是否可见，否则前一个页面仍然有点击事件
        //如果为true，即要显示的页面，如果为false，即要隐藏的页面
        if (isVisible) {
            animator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                    view.setVisibility(View.VISIBLE);
                }

                @Override
                public void onAnimationEnd(Animator animation) {

                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
        } else {
            animator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    view.setVisibility(View.INVISIBLE);
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
        }
    }

    /**
     * 旋转动画，其中isOvershoot代表是否有回弹效果
     */
    public static void setRotateAnimation(View view, String rotate, float startAngle,
                                          float endAngle, long delayMills,
                                          long duration, boolean isOvershoot) {
        ObjectAnimator animator;
        if (ROTATION_X.equals(rotate)) {
            animator = ObjectAnimator.ofFloat(view, ROTATION_X, startAngle, endAngle);
        } else if (ROTATION_Y.equals(rotate)) {
            animator = ObjectAnimator.ofFloat(view, ROTATION_Y, startAngle, endAngle);
        } else {
            throw new RuntimeException("rotate can not be else string," +
                    " except 'rotationY,rotationX'");
        }
        if (isOvershoot) {
            animator.setInterpolator(new OvershootInterpolator(1.5f));
        }
        animator.setDuration(duration).setStartDelay(delayMills);
        animator.start();
    }

    /**
     * 翻转动画
     *
     * @param context        当前上下文，提供给setCameraDistance()使用
     * @param backgroundView 背景View
     * @param frontView      当前处于前面的View
     * @param behindView     想要翻转显示的View
     */
    public static void setFlipAnimation(Context context, View backgroundView,
                                        View frontView, View behindView) {
        setCameraDistance(context, backgroundView, backgroundView);
        setCameraDistance(context, frontView, behindView);
        setAlphaAnimation(frontView, 1, 0, 0, 500, false);
        //必须让backgroundView和frontView同时翻转 否则会出现异常
        setRotateAnimation(backgroundView, ROTATION_Y, 0,
                180, 0, 1000, false);
        setRotateAnimation(frontView, ROTATION_Y, 0, 180,
                0, 1000, false);
        setRotateAnimation(behindView, ROTATION_Y, 0, -180,
                0, 1000, false);
        setAlphaAnimation(behindView, 0, 1,
                500, 500, true);
    }

    /**
     * 设置移动延迟动画，类似于每个控件依次入场或退场动画
     *
     * @param isVisible    控件是否可见 参考setAlphaAnimation解释
     * @param eachDelay    每一个控件进场或退场的延迟时间
     * @param eachDuration 每一个控件的duration
     * @param views        控件可变长参数，按顺序输入依次进场或退场
     */
    public static void setMoveDelayAnimation(String translation,
                                             float startTranslation, float endTranslation,
                                             float startAlpha, float endAlpha, boolean isVisible,
                                             long firstDelay, long eachDelay, long eachDuration,
                                             @NonNull View... views) {
        int i = 0;
        for (View view : views) {
            if (i == 0) {
                setTranslationAnimation(view, translation,
                        startTranslation, endTranslation, firstDelay, eachDuration,true);
                setAlphaAnimation(view, startAlpha, endAlpha, firstDelay, eachDuration, isVisible);
            } else {
                long delayMills = eachDelay * i + firstDelay;
                setTranslationAnimation(view, translation,
                        startTranslation, endTranslation, delayMills, eachDuration,true);
                setAlphaAnimation(view, startAlpha, endAlpha, delayMills, eachDuration, isVisible);
            }
            i++;
        }
    }

    /**
     * 重载设置移动延迟动画函数，结束位置默认控件位移位置
     *
     * @param isVisible    控件是否可见 参考setAlphaAnimation解释
     * @param eachDelay    每一个控件进场或退场的延迟时间
     * @param eachDuration 每一个控件的duration
     * @param views        控件可变长参数，按顺序输入依次进场或退场
     */
    public static void setMoveDelayAnimation(String translation,
                                             float startTranslation,
                                             float startAlpha, float endAlpha, boolean isVisible,
                                             long firstDelay, long eachDelay, long eachDuration,
                                             View... views) {
        int i = 0;
        for (View view : views) {
            float endTranslation;
            if (TRANSLATION_X.equals(translation)) {
                endTranslation = view.getTranslationX();
            } else if (TRANSLATION_Y.equals(translation)) {
                endTranslation = view.getTranslationY();
            } else {
                throw new RuntimeException("translation can not be other word," +
                        "except 'translationX,translationY'");
            }
            if (i == 0) {
                setTranslationAnimation(view, translation,
                        startTranslation, endTranslation, firstDelay, eachDuration,true);
                setAlphaAnimation(view, startAlpha, endAlpha, firstDelay, eachDuration, isVisible);
            } else {
                long delayMills = eachDelay * i + firstDelay;
                setTranslationAnimation(view, translation,
                        startTranslation, endTranslation, delayMills, eachDuration,true);
                setAlphaAnimation(view, startAlpha, endAlpha, delayMills, eachDuration, isVisible);
            }
            i++;
        }
    }

    /**
     * 调整相机距离，避免旋转的时候高度差变化引起视觉效果差
     *
     * @param context    当前上下文
     * @param frontView  正面的view
     * @param behindView 背面的view
     */
    private static void setCameraDistance(Context context, View frontView, View behindView) {
        int distance = 16000;
        float scale = context.getResources().getDisplayMetrics().density * distance;
        frontView.setCameraDistance(scale);
        behindView.setCameraDistance(scale);
    }

}
