package com.dididi.pocket.core.net.interceptor;

import java.io.IOException;
import java.util.LinkedHashMap;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;


/**
 * Created by dididi
 * on 23/07/2018 .
 */

public abstract class BaseInterceptor implements Interceptor {

    /** 获取有序排列的url参数 */
    protected LinkedHashMap<String, String> getUrlParameters(Chain chain) {
        final HttpUrl URL = chain.request().url();
        int size = URL.querySize();
        final LinkedHashMap<String, String> PARAMS = new LinkedHashMap<>();
        for (int i = 0; i < size; i++) {
            //获取URL的key值和值
            PARAMS.put(URL.queryParameterName(i), URL.queryParameterValue(i));
        }
        return PARAMS;
    }

    /** 根据key值获取参数 */
    protected String getUrlParameters(Chain chain, String key) {
        final Request REQUEST = chain.request();
        return REQUEST.url().queryParameter(key);
    }

    /** 获取报文参数 */
    @SuppressWarnings("WeakerAccess")
    protected LinkedHashMap<String, String> getBodyParameters(Chain chain) {
        final FormBody FORMBODY = (FormBody) chain.request().body();
        final LinkedHashMap<String, String> PARAMS = new LinkedHashMap<>();
        int size = 0;
        if (FORMBODY != null) {
            size = FORMBODY.size();
        }
        for (int i = 0; i < size; i++) {
            PARAMS.put(FORMBODY.name(i), FORMBODY.value(i));
        }
        return PARAMS;
    }

    protected String getBodyParameters(Chain chain, String key) {
        return getBodyParameters(chain).get(key);
    }
}
