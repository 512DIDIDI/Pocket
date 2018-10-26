package com.dididi.pocket.ec.test;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.blankj.utilcode.util.SizeUtils;
import com.dididi.pocket.core.delegates.PocketDelegate;
import com.dididi.pocket.ec.R;
import com.dididi.pocket.ec.R2;
import com.rengwuxian.materialedittext.MaterialEditText;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;


/**
 * @author dididi
 * @describe 测试布局
 * @since 25/09/2018
 */

public class TestDelegate extends PocketDelegate implements View.OnClickListener {


    @BindView(R2.id.delegate_test_background_layout_logo)
    AppCompatImageView mBackgroundLayoutLogo;
    @BindView(R2.id.delegate_test_background_layout_name)
    AppCompatTextView mBackgroundLayoutName;
    @BindView(R2.id.delegate_test_background_layout_enter_sign)
    CircleImageView mBackgroundLayoutEnterSign;
    @BindView(R2.id.delegate_test_logo)
    AppCompatImageView mLogo;
    @BindView(R2.id.delegate_test_name)
    AppCompatTextView mName;
    @BindView(R2.id.delegate_test_sign_in_layout_login)
    AppCompatTextView mSignInLayoutLoginText;
    @BindView(R2.id.delegate_test_sign_in_layout_account)
    MaterialEditText mSignInLayoutAccount;
    @BindView(R2.id.delegate_test_sign_in_layout_password)
    MaterialEditText mSignInLayoutPassword;
    @BindView(R2.id.delegate_test_sign_in_layout_sign_in)
    AppCompatButton mSignInLayoutSignIn;
    @BindView(R2.id.delegate_test_sign_in_layout_sign_up)
    AppCompatTextView mSignInLayoutSignUp;
    @BindView(R2.id.delegate_test_sign_in_layout_forget_password)
    AppCompatTextView mSignInLayoutForgetPassword;
    @BindView(R2.id.delegate_test_sign_in_layout)
    RelativeLayout mSignInLayout;
    @BindView(R2.id.delegate_test_sign_up_layout_signUp)
    AppCompatTextView mSignUpLayoutSignUpText;
    @BindView(R2.id.delegate_test_sign_up_layout_account)
    MaterialEditText mSignUpLayoutAccount;
    @BindView(R2.id.delegate_test_sign_up_layout_password)
    MaterialEditText mSignUpLayoutPassword;
    @BindView(R2.id.delegate_test_sign_up_layout_reenter_password)
    MaterialEditText mSignUpLayoutReenterPassword;
    @BindView(R2.id.delegate_test_sign_up_layout_sign_up)
    AppCompatButton mSignUpLayoutSignUp;
    @BindView(R2.id.delegate_test_sign_up_layout)
    RelativeLayout mSignUpLayout;
    @BindView(R2.id.delegate_test_forget_password_layout_forget_password)
    AppCompatTextView mForgetPasswordLayoutForgetPasswordText;
    @BindView(R2.id.delegate_test_forget_password_layout_account)
    MaterialEditText mForgetPasswordLayoutAccount;
    @BindView(R2.id.delegate_test_forget_password_layout_password)
    MaterialEditText mForgetPasswordLayoutPassword;
    @BindView(R2.id.delegate_test_forget_password_layout_reenter_password)
    MaterialEditText mForgetPasswordLayoutReenterPassword;
    @BindView(R2.id.delegate_test_forget_password_layout_verify)
    MaterialEditText mForgetPasswordLayoutVerify;
    @BindView(R2.id.delegate_test_forget_password_layout_send_verify)
    AppCompatButton mForgetPasswordLayoutSendVerify;
    @BindView(R2.id.delegate_test_forget_password_layout_next)
    AppCompatButton mForgetPasswordLayoutNext;
    @BindView(R2.id.delegate_test_forget_password_layout_login)
    AppCompatButton mForgetPasswordLayoutLogin;
    @BindView(R2.id.delegate_test_forget_password_layout)
    RelativeLayout mForgetPasswordLayout;
    @BindView(R2.id.delegate_test_sign_layout)
    FrameLayout mSignLayout;
    @BindView(R2.id.delegate_test_sign_up_layout_back)
    AppCompatTextView mSignUpLayoutBack;
    @BindView(R2.id.delegate_test_forget_password_layout_back)
    AppCompatTextView mForgetPasswordLayoutBack;

    /**
     * 动画关键字
     */
    private final String TRANSLATION_Y = "translationY";
    private final String TRANSLATION_X = "translationX";
    private final String ROTATION_Y = "rotationY";
    private final String ROTATION_X = "rotationX";

    @Override
    public Object setLayout() {
        return R.layout.delegate_test;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        mBackgroundLayoutEnterSign.setOnClickListener(this);
        mSignInLayoutSignUp.setOnClickListener(this);
        mSignInLayoutForgetPassword.setOnClickListener(this);
        mForgetPasswordLayoutBack.setOnClickListener(this);
        mSignUpLayoutBack.setOnClickListener(this);
    }

    @Override
    public void setSwipeBackEnable(boolean enable) {
        super.setSwipeBackEnable(true);
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.delegate_test_background_layout_enter_sign) {
            getSignAnimation();
        } else if (v.getId() == R.id.delegate_test_sign_in_layout_sign_up) {
            getSignUpAnimation();
        } else if (v.getId() == R.id.delegate_test_sign_in_layout_forget_password) {
            getForgetPasswordAnimation();
        } else if (v.getId() == R.id.delegate_test_sign_up_layout_back) {
            getSignUpBackAnimation();
        } else if (v.getId() == R.id.delegate_test_forget_password_layout_back) {
            getForgetPasswordBackAnimation();
        } else if (v.getId() == R.id.delegate_sign_find_password_next){
            getForgetPasswordNextAnimation();
        }
    }

    private void getSignAnimation() {
        mSignLayout.setVisibility(View.VISIBLE);
        mBackgroundLayoutEnterSign.setVisibility(View.INVISIBLE);
        mBackgroundLayoutLogo.setVisibility(View.INVISIBLE);
        mBackgroundLayoutName.setVisibility(View.INVISIBLE);
        getEnterSignAnimation();
    }

    private void getSignUpAnimation() {
        setFlipAnimation(mSignInLayout, mSignUpLayout);
    }

    private void getSignUpBackAnimation() {
        setFlipAnimation(mSignUpLayout, mSignInLayout);
    }

    private void getForgetPasswordAnimation() {
        setFlipAnimation(mSignInLayout, mForgetPasswordLayout);
    }

    private void getForgetPasswordBackAnimation() {
        setFlipAnimation(mForgetPasswordLayout, mSignInLayout);
    }

    private void getForgetPasswordNextAnimation(){
        //todo:这里还没做好
    }

    /**
     * 平移动画
     */
    private void setTranslationAnimation(View view, String translation,
                                         float startTranslation, float endTranslation,
                                         long delayMills, long duration) {
        ObjectAnimator animator;
        if (TRANSLATION_Y.equals(translation)) {
            animator = ObjectAnimator.ofFloat(view, translation, startTranslation,
                    endTranslation).setDuration(duration);
        } else if (TRANSLATION_X.equals(translation)) {
            animator = ObjectAnimator.ofFloat(view, translation, startTranslation,
                    endTranslation).setDuration(duration);
        } else {
            throw new RuntimeException("translation can not be else string except 'translationY,translationX'");
        }
        animator.setStartDelay(delayMills);
        animator.setInterpolator(new OvershootInterpolator(2f));
        animator.start();
    }

    /**
     * 透明动画
     */
    private void setAlphaAnimation(View view, float startAlpha,
                                   float endAlpha, long delayMills, long duration, boolean isVisible) {
        ObjectAnimator animator;
        animator = ObjectAnimator.ofFloat(view, "alpha", startAlpha, endAlpha);
        animator.setDuration(duration).setStartDelay(delayMills);
        animator.start();
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

    private void setRotateAnimation(View view, String rotate, float startAngle,
                                    float endAngle, long delayMills, long duration, boolean isOvershoot) {
        ObjectAnimator animator;
        if (ROTATION_X.equals(rotate)) {
            animator = ObjectAnimator.ofFloat(view, ROTATION_X, startAngle, endAngle);
        } else if (ROTATION_Y.equals(rotate)) {
            animator = ObjectAnimator.ofFloat(view, ROTATION_Y, startAngle, endAngle);
        } else {
            throw new RuntimeException("rotate can not be else string except 'rotationY,rotationX'");
        }
        if (isOvershoot) {
            animator.setInterpolator(new OvershootInterpolator(1.5f));
        }
        animator.setDuration(duration).setStartDelay(delayMills);
        animator.start();
    }

    /**
     * 翻转动画
     */
    private void setFlipAnimation(View frontView, View behindView) {
        setCameraDistance(mSignLayout, mSignLayout);
        setCameraDistance(frontView, behindView);
        setAlphaAnimation(frontView, 1, 0, 0, 500, false);
        setRotateAnimation(mSignLayout, ROTATION_Y, 0, 180, 0, 1000, true);
        setRotateAnimation(behindView, ROTATION_Y, 0, 180, 0, 1000, true);
        setAlphaAnimation(behindView, 0, 1, 500, 500, true);
    }

    /**
     * todo:这里的动画逻辑应该并不是最优解
     */
    private void getEnterSignAnimation() {
        setTranslationAnimation(mLogo, TRANSLATION_Y, mBackgroundLayoutLogo.getY(),
                mLogo.getTranslationY(), 0, 800);
        setTranslationAnimation(mName, TRANSLATION_Y, mBackgroundLayoutName.getY(),
                mName.getTranslationY(), 0, 800);
        setAlphaAnimation(mLogo, 1, 1, 0, 500, true);
        setAlphaAnimation(mName, 1, 1, 0, 500, true);
        setTranslationAnimation(mSignLayout, TRANSLATION_Y, SizeUtils.dp2px(200),
                mSignLayout.getTranslationY(), 500, 500);
        setAlphaAnimation(mSignLayout, 0, 1, 500, 500, true);
        setTranslationAnimation(mSignInLayoutLoginText, TRANSLATION_X, SizeUtils.dp2px(100),
                mSignLayout.getTranslationX(), 1000, 500);
        setAlphaAnimation(mSignInLayoutLoginText, 0, 1, 1000, 500, true);
        setTranslationAnimation(mSignInLayoutAccount, TRANSLATION_X, SizeUtils.dp2px(100),
                mSignLayout.getTranslationX(), 1100, 500);
        setAlphaAnimation(mSignInLayoutAccount, 0, 1, 1100, 500, true);
        setTranslationAnimation(mSignInLayoutPassword, TRANSLATION_X, SizeUtils.dp2px(100),
                mSignLayout.getTranslationX(), 1200, 500);
        setAlphaAnimation(mSignInLayoutPassword, 0, 1, 1200, 500, true);
        setTranslationAnimation(mSignInLayoutSignIn, TRANSLATION_X, SizeUtils.dp2px(100),
                mSignLayout.getTranslationX(), 1300, 500);
        setAlphaAnimation(mSignInLayoutSignIn, 0, 1, 1300, 500, true);
        setTranslationAnimation(mSignInLayoutSignUp, TRANSLATION_X, SizeUtils.dp2px(100),
                mSignLayout.getTranslationX(), 1400, 500);
        setAlphaAnimation(mSignInLayoutSignUp, 0, 1, 1400, 500, true);
        setTranslationAnimation(mSignInLayoutForgetPassword, TRANSLATION_X, SizeUtils.dp2px(100),
                mSignLayout.getTranslationX(), 1500, 500);
        setAlphaAnimation(mSignInLayoutForgetPassword, 0, 1, 1500, 500, true);
    }

    /**
     * 调整相机距离，避免旋转的时候高度差变化引起视觉效果差
     *
     * @param frontView  正面的view
     * @param behindView 背面的view
     */
    private void setCameraDistance(View frontView, View behindView) {
        int distance = 16000;
        float scale = getResources().getDisplayMetrics().density * distance;
        frontView.setCameraDistance(scale);
        behindView.setCameraDistance(scale);
    }
}
