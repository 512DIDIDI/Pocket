package com.dididi.pocket.ec.sign;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.dididi.pocket.ec.R;
import com.dididi.pocket.ec.R2;
import com.dididi.pocket_core.delegates.PocketDelegate;
import com.dididi.pocket_core.net.RestClient;
import com.dididi.pocket_core.net.callback.ISuccess;
import com.rengwuxian.materialedittext.MaterialEditText;

import butterknife.BindView;
import butterknife.OnClick;

public class SignUpDelegate extends PocketDelegate {

    private boolean isValid = true;

    @BindView(R2.id.sign_up_name_edit)
    MaterialEditText mName = null;
    @BindView(R2.id.sign_up_email_edit)
    MaterialEditText mEmail = null;
    @BindView(R2.id.sign_up_password_edit)
    MaterialEditText mPassword = null;
    @BindView(R2.id.sign_up_rePassword_edit)
    MaterialEditText mRePassword = null;

    //点击登录文字
    @OnClick(R2.id.sign_up_login)
    void onClickLogin() {
        //跳转登录界面
        startWithPop(new SignInDelegate());
    }

    //点击注册按钮
    @OnClick(R2.id.sign_up_btn)
    void onClickSignUp() {
        if (checkInputValid()) {
            RestClient.builder()
                    .url("")
                    .params("", "")
                    .onSuccess(new ISuccess() {
                        @Override
                        public void onSuccess(String response) {

                        }
                    })
                    .build()
                    .post();
        }
    }

    //点击toolbar返回按钮
    @OnClick(R2.id.sign_up_back_btn)
    void onClickBack() {
        //TODO:这里有bug,需要判断fragment堆是否为空
        getSupportDelegate().pop();
    }


    @Override
    public Object setLayout() {
        return R.layout.delegate_sign_up;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
    }

    private boolean checkInputValid() {
        if (mName.getText() == null || mEmail.getText() == null
                || mRePassword.getText() == null || mPassword.getText() == null) {
            isValid = false;
        } else {
            final String NAME = mName.getText().toString();
            final String EMAIL = mEmail.getText().toString();
            final String PASSWORD = mPassword.getText().toString();
            final String RE_PASSWORD = mRePassword.getText().toString();
            if (NAME.isEmpty()) {
                isValid = false;
                mName.setError("名字不能为空");
            } else {
                mName.setError(null);
            }
            if (EMAIL.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(EMAIL).matches()) {
                isValid = false;
                mEmail.setError("错误的邮箱格式");
            } else {
                mEmail.setError(null);
            }
            if (PASSWORD.isEmpty() || PASSWORD.length() <= 6) {
                isValid = false;
                mPassword.setError("密码位数不能低于6位");
            } else {
                mPassword.setError(null);
            }
            if (!RE_PASSWORD.equals(PASSWORD)) {
                isValid = false;
                mRePassword.setError("密码不一致");
            } else {
                mRePassword.setError(null);
            }
        }
        return isValid;
    }
}
