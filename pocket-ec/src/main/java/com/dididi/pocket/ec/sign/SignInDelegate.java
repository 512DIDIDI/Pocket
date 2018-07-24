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
import de.hdodenhof.circleimageview.CircleImageView;


/**
 * Created by dididi
 * on 24/07/2018 .
 */

public class SignInDelegate extends PocketDelegate {

    private boolean isValid = true;

    @BindView(R2.id.sign_in_email_edit)
    MaterialEditText mEmail = null;
    @BindView(R2.id.sign_in_password_edit)
    MaterialEditText mPassword = null;
    @BindView(R2.id.sign_in_head)
    CircleImageView mHead = null;

    //登录按钮
    @OnClick(R2.id.sign_in_btn)
    void onClickSignIn() {
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
                    .get();
            Toast.makeText(getContext(), "click this", Toast.LENGTH_LONG).show();
        }
    }

    //点击注册文字
    @OnClick(R2.id.sign_in_signUp)
    void onClickSignUp() {
        start(new SignUpDelegate(), SINGLETASK);
    }

    //点击忘记密码文字
    @OnClick(R2.id.sign_in_find_password)
    void onClickFindPassword() {
        start(new FindPasswordDelegate());
    }

    //点击微信登录图标
    @OnClick(R2.id.sign_in_weChat)
    void onClickWeChat() {
    }

    //点击qq登录图标
    @OnClick(R2.id.sign_in_qq)
    void onClickQQ() {
    }

    //点击微博登录图标
    @OnClick(R2.id.sign_in_weiBo)
    void onClickWeiBo() {
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_sign_in;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {

    }

    private boolean checkInputValid() {
        if (mEmail.getText() == null || mEmail.getText().toString().equals("")
                || mPassword.getText() == null || mPassword.getText().toString().equals("")) {
            Toast.makeText(getContext(), "不能输入为空", Toast.LENGTH_SHORT).show();
            isValid = false;
        } else {
            final String EMAIL = mEmail.getText().toString();
            final String PASSWORD = mPassword.getText().toString();
            if (!Patterns.EMAIL_ADDRESS.matcher(EMAIL).matches()) {
                isValid = false;
                mEmail.setError("错误的邮箱格式");
            } else {
                mEmail.setError(null);
            }
            if (PASSWORD.length() <= 6) {
                isValid = false;
                mPassword.setError("密码错误");
            } else {
                mPassword.setError(null);
            }
        }
        return isValid;
    }
}