package com.dididi.pocket.ec.sign;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.util.Patterns;
import android.view.View;

import com.dididi.pocket.core.ui.animation.PocketAnimation;
import com.dididi.pocket.ec.R;
import com.dididi.pocket.ec.R2;
import com.dididi.pocket.core.delegates.PocketDelegate;
import com.dididi.pocket.core.net.RestClient;
import com.rengwuxian.materialedittext.MaterialEditText;

import butterknife.BindView;
import butterknife.OnClick;

public class SignUpDelegate extends PocketDelegate {

    private static final String TAG = "SignUpDelegate";

    private boolean isValid = true;
    private ISignListener mISignListener = null;

    @BindView(R2.id.delegate_sign_up_name_edit)
    MaterialEditText mName = null;
    @BindView(R2.id.delegate_sign_up_email_edit)
    MaterialEditText mEmail = null;
    @BindView(R2.id.delegate_sign_up_password_edit)
    MaterialEditText mPassword = null;
    @BindView(R2.id.delegate_sign_up_rePassword_edit)
    MaterialEditText mRePassword = null;
    @BindView(R2.id.delegate_sign_up_btn)
    AppCompatButton mSignUp = null;
    @BindView(R2.id.delegate_sign_up_toolbar)
    Toolbar mToolBar = null;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof ISignListener) {
            mISignListener = (ISignListener) activity;
        }
    }

    /** 点击登录文字 */
    @OnClick(R2.id.delegate_sign_up_login)
    void onClickLogin() {
        //跳转登录界面
        getSupportDelegate().startWithPop(new SignInDelegate());
    }

    /** 点击注册按钮 */
    @SuppressWarnings("ConstantConditions")
    @OnClick(R2.id.delegate_sign_up_btn)
    void onClickSignUp() {
        mSignUp.setBackgroundColor(getResources().getColor(R.color.pressButtonColor));
        if (checkInputValid()) {
            RestClient.builder()
                    .url("http://192.168.1.105:3000/signup")
                    .params("user[name]", mName.getText().toString())
                    .params("user[password]", mEmail.getText().toString())
                    .params("user[email]", mPassword.getText().toString())
                    .onSuccess(response -> {

                    })
                    .build()
                    .post();
        }
    }

    /** 点击toolbar返回按钮 */
    @OnClick(R2.id.delegate_sign_up_back_btn)
    void onClickBack() {
        getSupportDelegate().pop();
    }


    @Override
    public Object setLayout() {
        return R.layout.delegate_sign_up;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
    }

    @Override
    public void setSwipeBackEnable(boolean enable) {
        super.setSwipeBackEnable(true);
    }

    private boolean checkInputValid() {
        if (mName.getText() == null || mEmail.getText() == null
                || mRePassword.getText() == null || mPassword.getText() == null) {
            isValid = false;
        } else {
            final String name = mName.getText().toString();
            final String email = mEmail.getText().toString();
            final String password = mPassword.getText().toString();
            final String rePassword = mRePassword.getText().toString();
            if (name.isEmpty()) {
                isValid = false;
                mName.setError("名字不能为空");
            } else {
                mName.setError(null);
            }
            if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                isValid = false;
                mEmail.setError("错误的邮箱格式");
            } else {
                mEmail.setError(null);
            }
            if (password.isEmpty() || password.length() <= 6) {
                isValid = false;
                mPassword.setError("密码位数不能低于6位");
            } else {
                mPassword.setError(null);
            }
            if (!rePassword.equals(password)) {
                isValid = false;
                mRePassword.setError("密码不一致");
            } else {
                mRePassword.setError(null);
            }
        }
        return isValid;
    }
}
