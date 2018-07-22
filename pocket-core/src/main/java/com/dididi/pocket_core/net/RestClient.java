package com.dididi.pocket_core.net;

import android.content.Context;

import com.dididi.pocket_core.net.callback.IError;
import com.dididi.pocket_core.net.callback.IFailure;
import com.dididi.pocket_core.net.callback.IRequest;
import com.dididi.pocket_core.net.callback.ISuccess;
import com.dididi.pocket_core.net.callback.RequestCallbacks;
import com.dididi.pocket_core.ui.LoaderStyle;
import com.dididi.pocket_core.ui.PocketLoader;

import java.util.Map;
import java.util.WeakHashMap;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by dididi
 * on 22/07/2018 .
 */

public class RestClient {

    private final String URL;
    private final WeakHashMap<String, Object> PARAMS = new WeakHashMap<>();
    private final ISuccess SUCCESS;
    private final IError ERROR;
    private final IFailure FAILURE;
    private final IRequest REQUEST;
    private final RequestBody BODY;
    private final Context CONTEXT;
    private final LoaderStyle STYLE;

    RestClient(String url, Map<String, Object> params,
               ISuccess iSuccess, IError iError,
               IFailure iFailure, IRequest iRequest,
               RequestBody body, Context context,
               LoaderStyle style) {
        this.URL = url;
        PARAMS.putAll(params);
        this.SUCCESS = iSuccess;
        this.ERROR = iError;
        this.FAILURE = iFailure;
        this.REQUEST = iRequest;
        this.BODY = body;
        this.CONTEXT = context;
        this.STYLE = style;
    }

    //使用建造者模式构建RestClient
    public static RestClientBuilder builder() {
        return new RestClientBuilder();
    }

    private void request(HttpMethod httpMethod) {
        //获取RestService
        RestService service = RestCreator.getRestService();
        Call<String> call = null;
        if (REQUEST != null) {
            REQUEST.onRequestStart();
        }
        //请求时加载loading
        if (STYLE != null) {
            PocketLoader.showLoading(CONTEXT, STYLE);

        }
        //传入url,params给service
        switch (httpMethod) {
            case GET:
                call = service.get(URL, PARAMS);
                break;
            case PUT:
                call = service.put(URL, PARAMS);
                break;
            case POST:
                call = service.post(URL, PARAMS);
                break;
            case DELETE:
                call = service.delete(URL, PARAMS);
                break;
            case UPLOAD:
                break;
            case PUT_RAM:
                break;
            case DOWNLOAD:
                break;
            case POST_RAM:
                break;
            default:
                break;
        }
        if (call != null) {
            //开启子线程执行,并返回callback
            call.enqueue(getRequestCallbacks());
        }
    }

    //获取Callback
    private Callback<String> getRequestCallbacks() {
        return new RequestCallbacks(SUCCESS, ERROR, FAILURE, REQUEST,STYLE);
    }

    public final void get() {
        request(HttpMethod.GET);
    }

    public final void put() {
        request(HttpMethod.PUT);
    }

    public final void post() {
        request(HttpMethod.POST);
    }

    public final void delete() {
        request(HttpMethod.DELETE);
    }
}
