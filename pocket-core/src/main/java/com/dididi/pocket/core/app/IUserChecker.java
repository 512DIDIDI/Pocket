package com.dididi.pocket.core.app;

/**
 * Created by dididi
 * on 02/09/2018 .
 */

public interface IUserChecker {

    /**
     * 已登录
     */
    void onSignIn();

    /**
     * 未登录
     */
    void onNotSignIn();
}
