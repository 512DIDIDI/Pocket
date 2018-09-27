package com.dididi.pocket.core.net.rx;

import android.content.Context;

import com.dididi.pocket.core.ui.loader.LoaderStyle;
import com.dididi.pocket.core.ui.loader.PocketLoader;
import com.dididi.pocket.core.net.HttpMethod;
import com.dididi.pocket.core.net.RestCreator;

import java.io.File;
import java.util.Map;
import java.util.WeakHashMap;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Observable;

/**
 * Created by dididi
 * on 22/07/2018 .
 */

public class RxRestClient {

    private final String URL;
    private final WeakHashMap<String, Object> PARAMS = new WeakHashMap<>();
    private final RequestBody BODY;
    private final Context CONTEXT;
    private final LoaderStyle STYLE;
    private final File FILE;

    RxRestClient(String url, Map<String, Object> params,
                 RequestBody body, Context context,
                 LoaderStyle style, File file) {
        this.URL = url;
        PARAMS.putAll(params);
        this.BODY = body;
        this.CONTEXT = context;
        this.STYLE = style;
        this.FILE = file;
    }

    /** 使用建造者模式构建RestClient */
    public static RxRestClientBuilder builder() {
        return new RxRestClientBuilder();
    }

    private Observable<String> request(HttpMethod httpMethod) {
        //获取RestService
        RxRestService service = RestCreator.getRxRestService();
        Observable<String> observable = null;

        //请求时加载loading
        if (STYLE != null) {
            PocketLoader.showLoading(CONTEXT, STYLE);

        }
        //传入url,params给service
        switch (httpMethod) {
            case GET:
                observable = service.get(URL, PARAMS);
                break;
            case PUT:
                observable = service.put(URL, PARAMS);
                break;
            case POST:
                observable = service.post(URL, PARAMS);
                break;
            case DELETE:
                observable = service.delete(URL, PARAMS);
                break;
            case UPLOAD:
                //请求体文件类型
                final RequestBody requestBody =
                        RequestBody.create(MediaType.parse(MultipartBody.FORM.toString()), FILE);
                //创建FormData对象
                final MultipartBody.Part body =
                        MultipartBody.Part.createFormData("file", FILE.getName(), requestBody);
                observable = service.upload(URL, body);
                break;
            case PUT_RAM:
                observable = service.putRaw(URL, BODY);
                break;
            case DOWNLOAD:
                break;
            case POST_RAM:
                observable = service.postRaw(URL, BODY);
                break;
            default:
                break;
        }

        return observable;
    }

    public final Observable<String> get() {
        return request(HttpMethod.GET);
    }

    public final Observable<String> put() {
        if (BODY == null) {
            return request(HttpMethod.PUT);
        } else {
            if (!PARAMS.isEmpty()) {
                throw new RuntimeException("Params must be null");
            }
            return request(HttpMethod.PUT_RAM);
        }
    }

    public final Observable<String> post() {
        if (BODY == null) {
            return request(HttpMethod.POST);
        } else {
            if (!PARAMS.isEmpty()) {
                throw new RuntimeException("Params must be null");
            }
            return request(HttpMethod.POST_RAM);
        }
    }

    public final Observable<String> upload() {
        return request(HttpMethod.UPLOAD);
    }

    public final Observable<String> delete() {
        return request(HttpMethod.DELETE);
    }
}
