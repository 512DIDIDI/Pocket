package com.dididi.pocket_core.net;

import android.content.Context;

import com.dididi.pocket_core.net.callback.IError;
import com.dididi.pocket_core.net.callback.IFailure;
import com.dididi.pocket_core.net.callback.IRequest;
import com.dididi.pocket_core.net.callback.ISuccess;
import com.dididi.pocket_core.ui.loader.LoaderStyle;
import com.dididi.pocket_core.ui.loader.PocketLoader;

import java.io.File;
import java.util.Map;
import java.util.WeakHashMap;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by dididi
 * on 22/07/2018 .
 */

public class RestClientBuilder {

    private String mUrl;
    private final WeakHashMap<String, Object> PARAMS = new WeakHashMap<>();
    private ISuccess mSuccess;
    private IError mError;
    private IFailure mFailure;
    private IRequest mRequest;
    private RequestBody mBody;
    private LoaderStyle mStyle;
    private Context mContext;
    private File mFile;

    RestClientBuilder() {
    }

    public final RestClientBuilder url(String url) {
        this.mUrl = url;
        return this;
    }

    public final RestClientBuilder params(Map<String, Object> params) {
        PARAMS.putAll(params);
        return this;
    }

    public final RestClientBuilder params(String key, Object value) {
        PARAMS.put(key, value);
        return this;
    }

    public final RestClientBuilder onSuccess(ISuccess iSuccess) {
        this.mSuccess = iSuccess;
        return this;
    }

    public final RestClientBuilder onFailure(IFailure iFailure) {
        this.mFailure = iFailure;
        return this;
    }

    public final RestClientBuilder onError(IError iError) {
        this.mError = iError;
        return this;
    }

    public final RestClientBuilder onRequest(IRequest iRequest) {
        this.mRequest = iRequest;
        return this;
    }

    //原始数据请求体
    public final RestClientBuilder raw(String raw) {
        this.mBody = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), raw);
        return this;
    }

    //传入loading样式
    public final RestClientBuilder loading(Context context, LoaderStyle style) {
        this.mContext = context;
        this.mStyle = style;
        return this;
    }

    public final RestClientBuilder file(File file) {
        this.mFile = file;
        return this;
    }

    public final RestClientBuilder file(String filePath) {
        this.mFile = new File(filePath);
        return this;
    }

    public final RestClientBuilder loading(Context context) {
        this.mContext = context;
        this.mStyle = (LoaderStyle) PocketLoader.DEFAULT_STYLE;
        return this;
    }

    public final RestClient build() {
        return new RestClient(mUrl, PARAMS, mSuccess, mError, mFailure,
                mRequest, mBody, mContext, mStyle, mFile);
    }
}
