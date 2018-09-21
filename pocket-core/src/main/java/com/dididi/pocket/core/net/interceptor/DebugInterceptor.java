package com.dididi.pocket.core.net.interceptor;

import android.support.annotation.NonNull;
import android.support.annotation.RawRes;

import com.dididi.pocket.core.Util.FileUtil;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.Protocol;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by dididi
 * on 23/07/2018 .
 */

public class DebugInterceptor extends BaseInterceptor {
    //测试拦截器

    private final String DEBUG_URL;
    private final int DEBUG_RAW_ID;

    public DebugInterceptor(String baseUrl, int rawId) {
        this.DEBUG_URL = baseUrl;
        this.DEBUG_RAW_ID = rawId;
    }

    private Response getResponse(Chain chain, String json) {
        return new Response.Builder()
                .code(200)
                .addHeader("Content-Type", "application/json")
                .body(ResponseBody.create(MediaType.parse("application/json"), json))
                .request(chain.request())
                .message("ok")
                .protocol(Protocol.HTTP_1_1)
                .build();
    }

    //@RawRes强制只能传入id
    private Response debugResponse(Chain chain, @RawRes int rawId) {
        final String JSON = FileUtil.getRawFile(rawId);
        return getResponse(chain, JSON);
    }
    //职责链模式(okHttp的拦截链)
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        final String URL = chain.request().url().toString();
        //如果url包含DEBUG_URL 则返回debugResponse()处理后的结果
        if (URL.contains(DEBUG_URL)) {
            return debugResponse(chain, DEBUG_RAW_ID);
        }
        //否则交由下一个chain处理
        return chain.proceed(chain.request());
    }
}
