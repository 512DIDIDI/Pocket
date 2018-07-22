package com.dididi.pocket_core.net;

import java.util.HashMap;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
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

/**
 * Created by dididi
 * on 22/07/2018 .
 */

public interface RestService {
    @GET
    Call<String> get(@Url String url, @QueryMap Map<String, Object> params);
    //返回Call的回调,QueryMap自动拼接到url里

    @FormUrlEncoded
    @POST
    Call<String> post(@Url String url, @FieldMap Map<String, Object> params);
    //新增或修改

    @FormUrlEncoded
    @PUT
    Call<String> put(@Url String url, @FieldMap Map<String, Object> params);
    //修改

    @DELETE
    Call<String> delete(@Url String url, @QueryMap Map<String, Object> params);
    //删除

    @Streaming
    @GET
    Call<RequestBody> download(@Url String url, @QueryMap Map<String, Object> params);
    //下载

    @Multipart
    @POST
    Call<String> upload(@Url String url, @Part MultipartBody.Part file);
    //上传
}
