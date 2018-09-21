package com.dididi.pocket.core.net.callback;

import android.os.Handler;
import android.support.annotation.NonNull;

import com.dididi.pocket.core.ui.loader.LoaderStyle;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by dididi
 * on 22/07/2018 .
 */

public class RequestCallbacks implements Callback<String> {
    //回调接口实现

    private final ISuccess SUCCESS;
    private final IError ERROR;
    private final IFailure FAILURE;
    private final IRequest REQUEST;
    private final LoaderStyle STYLE;
    private static final Handler HANDLER = new Handler();

    public RequestCallbacks(ISuccess iSuccess, IError iError,
                            IFailure iFailure, IRequest iRequest,
                            LoaderStyle style) {
        this.SUCCESS = iSuccess;
        this.ERROR = iError;
        this.FAILURE = iFailure;
        this.REQUEST = iRequest;
        this.STYLE = style;
    }

    @Override
    public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
        if (response.isSuccessful()) {
            if (call.isExecuted()) {
                if (SUCCESS != null) {
                    SUCCESS.onSuccess(response.body());
                }
            }
        } else {
            if (ERROR != null) {
                ERROR.onError(response.code(), response.message());
            }
        }
        //PocketLoader.stopLoading();
    }

    @Override
    public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
        if (FAILURE != null) {
            FAILURE.onFailure();
        }
        if (REQUEST != null) {
            //失败时结束Request
            REQUEST.onRequestEnd();
        }
    }
}
