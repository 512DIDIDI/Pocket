package com.dididi.pocket_core.net.rx;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by dididi
 * on 22/07/2018 .
 */

public interface RxRestService {
    //观察者模式
    @GET
    Observable<String> get(@Url String url, @QueryMap Map<String, Object> params);
    //返回Call的回调,QueryMap自动拼接到url里

    @FormUrlEncoded
    @POST
    Observable<String> post(@Url String url, @FieldMap Map<String, Object> params);
    //新增

    @POST
    Observable<String> postRaw(@Url String url, @Body RequestBody body);
    //post原始数据

    @FormUrlEncoded
    @PUT
    Observable<String> put(@Url String url, @FieldMap Map<String, Object> params);
    //修改

    @PUT
    Observable<String> putRaw(@Url String url, @Body RequestBody body);
    //put原始数据

    @DELETE
    Observable<String> delete(@Url String url, @QueryMap Map<String, Object> params);
    //删除

    @Streaming
    @GET
    Observable<RequestBody> download(@Url String url, @QueryMap Map<String, Object> params);
    //下载

    @Multipart
    @POST
    Observable<String> upload(@Url String url, @Part MultipartBody.Part file);
    //上传
}
