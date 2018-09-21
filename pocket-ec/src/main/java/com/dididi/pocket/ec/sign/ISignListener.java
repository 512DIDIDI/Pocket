package com.dididi.pocket.ec.sign;

/**
 * Created by dididi
 * on 30/08/2018 .
 */

public interface ISignListener {
    /** 注册成功回调 */
    void onSignUpSuccess();
    /** 登录成功回调 */
    void onSignInSuccess();
}
