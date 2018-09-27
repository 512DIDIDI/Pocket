package com.dididi.pocket.core.net;

import com.dididi.pocket.core.net.rx.RxRestService;
import com.dididi.pocket.core.app.ConfigType;
import com.dididi.pocket.core.app.Pocket;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by dididi
 * on 22/07/2018 .
 */

public class RestCreator {

    /** 实例化Retrofit */
    private static final class RetrofitHolder {
        /** 获取配置文件url */
        private static final String BASE_URL =
                (String) Pocket.getConfigurations().get(ConfigType.API_HOST);
        private static final Retrofit RETROFIT = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                //惰性加载客户端
                .client(OkHttpHolder.OK_HTTP_CLIENT)
                //添加转换工厂
                .addConverterFactory(ScalarsConverterFactory.create())
                //添加rx适配器
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }
    /** 实例化OkHttpClient */
    private static final class OkHttpHolder {
        /** 设置超时时间(keep-alive持续时间) */
        private static final int TIME_OUT = 60;
        private static final OkHttpClient.Builder BUILDER = new OkHttpClient.Builder();
        /** 添加拦截器 */
        @SuppressWarnings("unchecked")
        private static final ArrayList<Interceptor> INTERCEPTORS =
                (ArrayList<Interceptor>) Pocket.getConfigurations().get(ConfigType.INTERCEPTOR);
        private static OkHttpClient.Builder addInterceptor(){
            if (INTERCEPTORS != null&&!INTERCEPTORS.isEmpty()){
                for (Interceptor interceptor : INTERCEPTORS) {
                    BUILDER.addInterceptor(interceptor);
                }
            }
            return BUILDER;
        }
        private static final OkHttpClient OK_HTTP_CLIENT = addInterceptor()
                .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
                .build();
    }
    /** 实例化RestService */
    private static final class RestServiceHolder {
        private static final RestService REST_SERVICE =
                RetrofitHolder.RETROFIT.create(RestService.class);
    }

    public static RestService getRestService() {
        return RestServiceHolder.REST_SERVICE;
    }

    private static final class RxRestServiceHolder {
        private static final RxRestService REST_SERVICE =
                RetrofitHolder.RETROFIT.create(RxRestService.class);
    }
    /** 获取RxRestService */
    public static RxRestService getRxRestService() {
        return RxRestServiceHolder.REST_SERVICE;
    }
}
