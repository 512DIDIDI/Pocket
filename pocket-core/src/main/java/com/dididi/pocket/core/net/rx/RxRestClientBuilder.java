package com.dididi.pocket.core.net.rx;

import android.content.Context;

import com.dididi.pocket.core.ui.loader.LoaderStyle;
import com.dididi.pocket.core.ui.loader.PocketLoader;

import java.io.File;
import java.util.Map;
import java.util.WeakHashMap;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by dididi
 * on 22/07/2018 .
 */

public class RxRestClientBuilder {

    private String mUrl;
    private final WeakHashMap<String, Object> PARAMS = new WeakHashMap<>();
    private RequestBody mBody;
    private LoaderStyle mStyle;
    private Context mContext;
    private File mFile;

    RxRestClientBuilder() {
    }

    public final RxRestClientBuilder url(String url) {
        this.mUrl = url;
        return this;
    }

    public final RxRestClientBuilder params(Map<String, Object> params) {
        PARAMS.putAll(params);
        return this;
    }

    public final RxRestClientBuilder params(String key, Object value) {
        PARAMS.put(key, value);
        return this;
    }

    /** 原始数据请求体 */
    public final RxRestClientBuilder raw(String raw) {
        this.mBody = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), raw);
        return this;
    }

    /** 传入loading样式 */
    public final RxRestClientBuilder loading(Context context, LoaderStyle style) {
        this.mContext = context;
        this.mStyle = style;
        return this;
    }

    public final RxRestClientBuilder file(File file) {
        this.mFile = file;
        return this;
    }

    public final RxRestClientBuilder file(String filePath) {
        this.mFile = new File(filePath);
        return this;
    }

    public final RxRestClientBuilder loading(Context context) {
        this.mContext = context;
        this.mStyle = (LoaderStyle) PocketLoader.DEFAULT_STYLE;
        return this;
    }

    public final RxRestClient build() {
        return new RxRestClient(mUrl, PARAMS, mBody, mContext, mStyle, mFile);
    }
}
