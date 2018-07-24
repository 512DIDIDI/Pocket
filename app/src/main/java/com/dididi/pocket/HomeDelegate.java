package com.dididi.pocket;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import com.dididi.pocket_core.delegates.PocketDelegate;
import com.dididi.pocket_core.net.RestClient;
import com.dididi.pocket_core.net.callback.IError;
import com.dididi.pocket_core.net.callback.IFailure;
import com.dididi.pocket_core.net.callback.ISuccess;

/**
 * Created by dididi
 * on 19/07/2018 .
 */

public class HomeDelegate extends PocketDelegate {

    @Override
    //返回布局id
    public Object setLayout() {
        return R.layout.delegate_mall_home;
    }

    @Override
    //绑定控件
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        testRestClient();
    }

    private void testRestClient() {

        RestClient.builder()
                .url("http://127.0.0.1/index")
                .loading(getContext())
                .onSuccess(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        Toast.makeText(getContext(), response, Toast.LENGTH_LONG).show();
                    }
                })
                .onError(new IError() {
                    @Override
                    public void onError(int code, String msg) {
                        //Toast.makeText(getContext(), msg + code, Toast.LENGTH_LONG).show();
                    }
                })
                .onFailure(new IFailure() {
                    @Override
                    public void onFailure() {
                        //Toast.makeText(getContext(), "Failure", Toast.LENGTH_LONG).show();
                    }
                })
                .build()
                .get();
    }
}
