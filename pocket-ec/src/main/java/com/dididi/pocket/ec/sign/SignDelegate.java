package com.dididi.pocket.ec.sign;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.dididi.pocket.core.app.Pocket;
import com.dididi.pocket.core.delegates.PocketDelegate;
import com.dididi.pocket.core.net.RestClient;
import com.dididi.pocket.core.ui.animation.PocketAnimation;
import com.dididi.pocket.core.util.AutoBarUtil;
import com.dididi.pocket.core.util.LogUtil;
import com.dididi.pocket.ec.R;
import com.dididi.pocket.ec.R2;
import com.dididi.pocket.ec.main.PocketBottomDelegate;
import com.rengwuxian.materialedittext.MaterialEditText;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @author dididi(yechao)
 * @describe
 * @since 29/10/2018
 */

public class SignDelegate extends PocketDelegate implements View.OnClickListener {

    private ISignListener mSignListener;

    @BindView(R2.id.delegate_sign_background_layout_logo)
    AppCompatImageView mBackgroundLayoutLogo;
    @BindView(R2.id.delegate_sign_background_layout_name)
    AppCompatTextView mBackgroundLayoutName;
    @BindView(R2.id.delegate_sign_background_layout_enter_sign)
    CircleImageView mBackgroundLayoutEnterSign;
    @BindView(R2.id.delegate_sign_logo)
    AppCompatImageView mLogo;
    @BindView(R2.id.delegate_sign_name)
    AppCompatTextView mName;
    @BindView(R2.id.delegate_sign_sign_in_layout_login)
    AppCompatTextView mSignInLayoutLogin;
    @BindView(R2.id.delegate_sign_sign_in_layout_account)
    MaterialEditText mSignInLayoutAccount;
    @BindView(R2.id.delegate_sign_sign_in_layout_password)
    MaterialEditText mSignInLayoutPassword;
    @BindView(R2.id.delegate_sign_sign_in_layout_sign_in)
    AppCompatButton mSignInLayoutSignIn;
    @BindView(R2.id.delegate_sign_sign_in_layout_sign_up)
    AppCompatTextView mSignInLayoutSignUp;
    @BindView(R2.id.delegate_sign_sign_in_layout_forget_password)
    AppCompatTextView mSignInLayoutForgetPassword;
    @BindView(R2.id.delegate_sign_sign_in_layout)
    RelativeLayout mSignInLayout;
    @BindView(R2.id.delegate_sign_sign_up_layout_register)
    AppCompatTextView mSignUpLayoutRegister;
    @BindView(R2.id.delegate_sign_sign_up_layout_account)
    MaterialEditText mSignUpLayoutAccount;
    @BindView(R2.id.delegate_sign_sign_up_layout_password)
    MaterialEditText mSignUpLayoutPassword;
    @BindView(R2.id.delegate_sign_sign_up_layout_reenter_password)
    MaterialEditText mSignUpLayoutReenterPassword;
    @BindView(R2.id.delegate_sign_sign_up_layout_sign_up)
    AppCompatButton mSignUpLayoutSignUp;
    @BindView(R2.id.delegate_sign_sign_up_layout_back)
    AppCompatTextView mSignUpLayoutBack;
    @BindView(R2.id.delegate_sign_sign_up_layout)
    RelativeLayout mSignUpLayout;
    @BindView(R2.id.delegate_sign_forget_password_layout_forget_password)
    AppCompatTextView mForgetPasswordLayoutForgetPassword;
    @BindView(R2.id.delegate_sign_forget_password_layout_account)
    MaterialEditText mForgetPasswordLayoutAccount;
    @BindView(R2.id.delegate_sign_forget_password_layout_verify)
    MaterialEditText mForgetPasswordLayoutVerify;
    @BindView(R2.id.delegate_sign_forget_password_layout_send_verify)
    AppCompatButton mForgetPasswordLayoutSendVerify;
    @BindView(R2.id.delegate_sign_forget_password_layout_next)
    AppCompatButton mForgetPasswordLayoutNext;
    @BindView(R2.id.delegate_sign_forget_password_layout_back)
    AppCompatTextView mForgetPasswordLayoutBack;
    @BindView(R2.id.delegate_sign_forget_password_layout)
    RelativeLayout mForgetPasswordLayout;
    @BindView(R2.id.delegate_sign_reset_password_layout)
    RelativeLayout mResetPasswordLayout;
    @BindView(R2.id.delegate_sign_reset_password_layout_forget_password)
    AppCompatTextView mResetPasswordLayoutResetPassword;
    @BindView(R2.id.delegate_sign_reset_password_layout_password)
    MaterialEditText mResetPasswordLayoutPassword;
    @BindView(R2.id.delegate_sign_reset_password_layout_reenter_password)
    MaterialEditText mResetPasswordLayoutReenterPassword;
    @BindView(R2.id.delegate_sign_reset_password_layout_login)
    AppCompatButton mResetPasswordLayoutLogin;
    @BindView(R2.id.delegate_sign_reset_password_layout_back)
    AppCompatTextView mResetPasswordLayoutBack;
    @BindView(R2.id.delegate_sign_sign_layout)
    FrameLayout mSignLayout;
    @BindView(R2.id.delegate_sign_dididi_studio)
    AppCompatTextView mDididiStudio;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof ISignListener) {
            mSignListener = (ISignListener) activity;
        }
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_sign;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        mBackgroundLayoutEnterSign.setOnClickListener(this);
        mSignInLayoutSignUp.setOnClickListener(this);
        mSignInLayoutForgetPassword.setOnClickListener(this);
        mForgetPasswordLayoutBack.setOnClickListener(this);
        mSignUpLayoutBack.setOnClickListener(this);
        mForgetPasswordLayoutNext.setOnClickListener(this);
        mSignInLayoutSignIn.setOnClickListener(this);
        mSignUpLayoutSignUp.setOnClickListener(this);
        mForgetPasswordLayoutSendVerify.setOnClickListener(this);
        mResetPasswordLayoutLogin.setOnClickListener(this);
        mResetPasswordLayoutBack.setOnClickListener(this);
        if (getActivity() != null){
            AutoBarUtil.Companion.changeBarColor(getActivity(), (int) AutoBarUtil.COLOR_GRAY);
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.delegate_sign_background_layout_enter_sign) {
            getSignAnimation();
        } else if (v.getId() == R.id.delegate_sign_sign_in_layout_sign_up) {
            getSignUpAnimation();
        } else if (v.getId() == R.id.delegate_sign_sign_in_layout_forget_password) {
            getForgetPasswordAnimation();
        } else if (v.getId() == R.id.delegate_sign_sign_up_layout_back) {
            getSignUpBackAnimation();
        } else if (v.getId() == R.id.delegate_sign_forget_password_layout_back) {
            getForgetPasswordBackAnimation();
        } else if (v.getId() == R.id.delegate_sign_forget_password_layout_next) {
            getForgetPasswordNextAnimation();
        } else if (v.getId() == R.id.delegate_sign_sign_up_layout_sign_up) {
            //注册事件
            if (checkRegisterInputValid()) {
                RestClient.builder()
                        .url("")
                        .params("", mSignUpLayoutAccount.getText().toString())
                        .params("", mSignUpLayoutPassword.getText().toString())
                        .onSuccess(response -> {

                        })
                        .build()
                        .post();
            }
        } else if (v.getId() == R.id.delegate_sign_sign_in_layout_sign_in) {
            //登录事件
            if (checkLoginInputValid()) {
                RestClient.builder()
                        .url("")
                        .params("", mSignInLayoutAccount.getText().toString())
                        .params("", mSignInLayoutPassword.getText().toString())
                        .onSuccess(response -> {
                                    if (response.contains("\"code\":1")) {
                                        LogUtils.d(response);
                                        SignHandler.onSignIn(response, mSignListener);
                                        getSupportDelegate().startWithPop(new PocketBottomDelegate());
                                    } else {
                                        LogUtils.d(response);
                                        Toast.makeText(Pocket.getApplicationContext(),
                                                "登录失败,请重新输入用户名和密码", Toast.LENGTH_SHORT).show();
                                    }
                                }
                        )
                        .onError((code, msg) -> LogUtil.d("response:", code + msg))
                        .build()
                        .get();
            } else {
                getSupportDelegate().startWithPop(new PocketBottomDelegate());
            }
        } else if (v.getId() == R.id.delegate_sign_reset_password_layout_login) {
            //重设密码事件
            if (checkResetPasswordInputValid()) {
                RestClient.builder()
                        .url("")
                        .params("", mResetPasswordLayoutPassword.getText().toString())
                        .params("", mResetPasswordLayoutReenterPassword.getText().toString())
                        .onSuccess(response -> {
                                    if (response.contains("\"code\":1")) {
                                        LogUtils.d(response);
                                        SignHandler.onSignIn(response, mSignListener);
                                        getSupportDelegate().startWithPop(new PocketBottomDelegate());
                                    } else {
                                        LogUtils.d(response);
                                        Toast.makeText(Pocket.getApplicationContext(),
                                                "登录失败,请重新输入用户名和密码", Toast.LENGTH_SHORT).show();
                                    }
                                }
                        )
                        .onError((code, msg) -> LogUtil.d("response:", code + msg))
                        .build()
                        .get();
            }
        } else if (v.getId() == R.id.delegate_sign_forget_password_layout_send_verify) {
            if (checkForgetPasswordInputValid()) {
                //todo:发送验证码事件
            }
        } else if (v.getId() == R.id.delegate_sign_reset_password_layout_back) {
            getResetPasswordBackAnimation();
        }
    }

    /**
     * SignLayout动画封装
     */
    private void getSignAnimation() {
        mBackgroundLayoutEnterSign.setVisibility(View.INVISIBLE);
        getEnterSignAnimation();
    }

    /**
     * 注册页面动画切换
     */
    private void getSignUpAnimation() {
        PocketAnimation.setFlipAnimation(getContext(), mSignLayout, mSignInLayout, mSignUpLayout);
    }

    /**
     * 注册页面返回按钮动画切换
     */
    private void getSignUpBackAnimation() {
        PocketAnimation.setFlipAnimation(getContext(), mSignLayout, mSignUpLayout, mSignInLayout);
    }

    /**
     * 找回密码页面动画切换
     */
    private void getForgetPasswordAnimation() {
        PocketAnimation.setFlipAnimation(getContext(),
                mSignLayout, mSignInLayout, mForgetPasswordLayout);
    }

    /**
     * 找回页面返回按钮动画切换
     */
    private void getForgetPasswordBackAnimation() {
        PocketAnimation.setFlipAnimation(getContext(),
                mSignLayout, mForgetPasswordLayout, mSignInLayout);
    }

    /**
     * 找回密码下一步动画切换
     */
    private void getForgetPasswordNextAnimation() {
        PocketAnimation.setFlipAnimation(getContext(),
                mSignLayout, mForgetPasswordLayout, mResetPasswordLayout);
    }

    /**
     * 重设密码返回动画切换
     */
    private void getResetPasswordBackAnimation() {
        PocketAnimation.setFlipAnimation(getContext(),
                mSignLayout, mResetPasswordLayout, mForgetPasswordLayout);
    }

    /**
     * 点击进入Sign页面动画
     */
    private void getEnterSignAnimation() {
        //logo出现
        //这里用一个隐藏的logo用来定位最后logo应该位移多少
        //安卓中定位原点在左上角
        //故位移距离计算公式 =（隐藏的logo位置-backgroundLogo位置）
        PocketAnimation.setTranslationAnimation(mBackgroundLayoutLogo,
                PocketAnimation.TRANSLATION_Y, mBackgroundLayoutLogo.getTranslationY(),
                mLogo.getY() - mBackgroundLayoutLogo.getY(),
                0, 500, false);
        PocketAnimation.setTranslationAnimation(mBackgroundLayoutName,
                PocketAnimation.TRANSLATION_Y, mBackgroundLayoutName.getTranslationY(),
                mName.getY() - mBackgroundLayoutName.getY(),
                0, 500, false);
        //Sign页面及个人标识出现
        PocketAnimation.setMoveDelayAnimation(PocketAnimation.TRANSLATION_Y,
                SizeUtils.dp2px(200), 0, 1, true,
                500, 100, 500, mSignLayout, mDididiStudio);
        //登录信息页面依次从右往左出现
        PocketAnimation.setMoveDelayAnimation(PocketAnimation.TRANSLATION_X,
                SizeUtils.dp2px(100), 0, 1, true,
                1000, 100, 500,
                mSignInLayoutLogin, mSignInLayoutAccount, mSignInLayoutPassword,
                mSignInLayoutSignIn, mSignInLayoutSignUp, mSignInLayoutForgetPassword);
    }

    /**
     * 验证登录页面的内容输入是否正确
     */
    private boolean checkLoginInputValid() {
        boolean isValid = true;
        if (mSignInLayoutAccount.getText() == null
                || "".equals(mSignInLayoutAccount.getText().toString())
                || mSignInLayoutPassword.getText() == null
                || "".equals(mSignInLayoutPassword.getText().toString())) {
            Toast.makeText(getContext(), "不能输入为空", Toast.LENGTH_SHORT).show();
            isValid = false;
        } else {
            final String email = mSignInLayoutAccount.getText().toString();
            final String password = mSignInLayoutPassword.getText().toString();
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                isValid = false;
                mSignInLayoutAccount.setError("错误的邮箱格式");
            } else {
                mSignInLayoutAccount.setError(null);
            }
            if (password.length() <= 6) {
                isValid = false;
                mSignInLayoutPassword.setError("密码错误");
            } else {
                mSignInLayoutPassword.setError(null);
            }
        }
        return isValid;
    }

    /**
     * 验证注册信息是否输入正确
     */
    private boolean checkRegisterInputValid() {
        boolean isValid = true;
        if (mSignUpLayoutAccount.getText() == null
                || mSignUpLayoutReenterPassword.getText() == null
                || mSignUpLayoutPassword.getText() == null) {
            isValid = false;
        } else {
            final String email = mSignUpLayoutAccount.getText().toString();
            final String password = mSignUpLayoutPassword.getText().toString();
            final String rePassword = mSignUpLayoutReenterPassword.getText().toString();
            if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                isValid = false;
                mSignUpLayoutAccount.setError("错误的邮箱格式");
            } else {
                mSignUpLayoutAccount.setError(null);
            }
            if (password.isEmpty() || password.length() <= 6) {
                isValid = false;
                mSignUpLayoutPassword.setError("密码位数不能低于6位");
            } else {
                mSignUpLayoutPassword.setError(null);
            }
            if (!rePassword.equals(password)) {
                isValid = false;
                mSignUpLayoutReenterPassword.setError("密码不一致");
            } else {
                mSignUpLayoutReenterPassword.setError(null);
            }
        }
        return isValid;
    }

    /**
     * 验证找回密码页面的内容输入是否正确
     */
    private boolean checkForgetPasswordInputValid() {
        boolean isValid = true;
        if (mForgetPasswordLayoutAccount.getText() == null
                || "".equals(mForgetPasswordLayoutAccount.getText().toString())
                || mForgetPasswordLayoutVerify.getText() == null
                || "".equals(mForgetPasswordLayoutVerify.getText().toString())) {
            Toast.makeText(getContext(), "不能输入为空", Toast.LENGTH_SHORT).show();
            isValid = false;
        } else {
            final String email = mForgetPasswordLayoutAccount.getText().toString();
            final String verify = mForgetPasswordLayoutVerify.getText().toString();
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                isValid = false;
                mForgetPasswordLayoutAccount.setError("错误的邮箱格式");
            } else {
                mForgetPasswordLayoutAccount.setError(null);
            }
            if (verify.length() <= 4) {
                isValid = false;
                mForgetPasswordLayoutVerify.setError("验证码输入错误");
            } else {
                mForgetPasswordLayoutVerify.setError(null);
            }
        }
        return isValid;
    }

    /**
     * 验证重设密码页面的内容输入是否正确
     */
    private boolean checkResetPasswordInputValid() {
        boolean isValid = true;
        if (mResetPasswordLayoutPassword.getText() == null
                || "".equals(mResetPasswordLayoutPassword.getText().toString())
                || mResetPasswordLayoutReenterPassword.getText() == null
                || "".equals(mResetPasswordLayoutReenterPassword.getText().toString())) {
            Toast.makeText(getContext(), "不能输入为空", Toast.LENGTH_SHORT).show();
            isValid = false;
        } else {
            final String password = mResetPasswordLayoutPassword.getText().toString();
            final String rePassword = mResetPasswordLayoutReenterPassword.getText().toString();
            if (password.isEmpty() || password.length() <= 6) {
                isValid = false;
                mResetPasswordLayoutPassword.setError("密码位数不能低于6位");
            } else {
                mResetPasswordLayoutPassword.setError(null);
            }
            if (!rePassword.equals(password)) {
                isValid = false;
                mResetPasswordLayoutReenterPassword.setError("密码不一致");
            } else {
                mResetPasswordLayoutReenterPassword.setError(null);
            }
        }
        return isValid;
    }
}
