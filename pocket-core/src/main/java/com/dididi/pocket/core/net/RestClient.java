package com.dididi.pocket.core.net;

import android.content.Context;

import com.dididi.pocket.core.net.callback.IError;
import com.dididi.pocket.core.net.callback.IFailure;
import com.dididi.pocket.core.net.callback.IRequest;
import com.dididi.pocket.core.net.callback.ISuccess;
import com.dididi.pocket.core.net.callback.RequestCallbacks;
import com.dididi.pocket.core.ui.loader.LoaderStyle;
import com.dididi.pocket.core.ui.loader.PocketLoader;

import java.io.File;
import java.util.Map;
import java.util.WeakHashMap;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
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
    private final File FILE;

    RestClient(String url, Map<String, Object> params,
               ISuccess iSuccess, IError iError,
               IFailure iFailure, IRequest iRequest,
               RequestBody body, Context context,
               LoaderStyle style, File file) {
        this.URL = url;
        PARAMS.putAll(params);
        this.SUCCESS = iSuccess;
        this.ERROR = iError;
        this.FAILURE = iFailure;
        this.REQUEST = iRequest;
        this.BODY = body;
        this.CONTEXT = context;
        this.STYLE = style;
        this.FILE = file;
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
                //请求体文件类型
                final RequestBody requestBody =
                        RequestBody.create(MediaType.parse(MultipartBody.FORM.toString()), FILE);
                //创建FormData对象
                final MultipartBody.Part body =
                        MultipartBody.Part.createFormData("file", FILE.getName(), requestBody);
                call = service.upload(URL, body);
                break;
            case PUT_RAM:
                call = service.putRaw(URL, BODY);
                break;
            case DOWNLOAD:
                break;
            case POST_RAM:
                call = service.postRaw(URL, BODY);
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
        return new RequestCallbacks(SUCCESS, ERROR, FAILURE, REQUEST, STYLE);
    }

    public final void get() {
        request(HttpMethod.GET);
    }

    public final void put() {
        if (BODY == null) {
            request(HttpMethod.PUT);
        } else {
            if (!PARAMS.isEmpty()) {
                throw new RuntimeException("Params must be null");
            }
            request(HttpMethod.PUT_RAM);
        }
    }

    public final void post() {
        if (BODY == null) {
            request(HttpMethod.POST);
        } else {
            if (!PARAMS.isEmpty()) {
                throw new RuntimeException("Params must be null");
            }
            request(HttpMethod.POST_RAM);
        }
    }

    public final void upload() {
        request(HttpMethod.UPLOAD);
    }

    public final void delete() {
        request(HttpMethod.DELETE);
    }
}
