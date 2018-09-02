package com.dididi.pocket_core.net.interceptor;

import android.app.ActivityManager;
import android.text.TextUtils;

import com.dididi.pocket_core.Util.PocketPreferences;
import com.dididi.pocket_core.app.AccountManager;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;


/**
 * Created by dididi
 * on 02/09/2018 .
 */

public class TokenIntercepter implements Interceptor {
    //token拦截器
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response response = chain.proceed(request);
        if (isTokenExpired(response)){
            //TODO:如何实现失效后跳转登录页面?
        }
        return response;
    }

    private boolean isTokenExpired(Response response) {
        if (response.code() == -1) {
            AccountManager.setSignState(false);
            return true;
        } else {
            return false;
        }
    }
}
