package com.dididi.pocket_core.net;

import com.dididi.pocket_core.app.ConfigType;
import com.dididi.pocket_core.app.Pocket;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by dididi
 * on 22/07/2018 .
 */

public class RestCreator {

    public static RestService getRestService() {
        return RestServiceHolder.REST_SERVICE;
    }
    //实例化Retrofit
    private static final class RetrofitHolder {
        //获取配置文件url
        private static final String BASE_URL =
                (String) Pocket.getConfigurations().get(ConfigType.API_HOST);
        private static final Retrofit RETROFIT = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                //惰性加载客户端
                .client(OkHttpHolder.OK_HTTP_CLIENT)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
    }
    //实例化OkHttpClient
    private static final class OkHttpHolder {
        //设置超时时间
        private static final int TIME_OUT = 60;
        private static final OkHttpClient OK_HTTP_CLIENT = new OkHttpClient.Builder()
                .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
                .build();
    }
    //实例化RestService
    private static final class RestServiceHolder {
        private static final RestService REST_SERVICE =
                RetrofitHolder.RETROFIT.create(RestService.class);
    }
}
