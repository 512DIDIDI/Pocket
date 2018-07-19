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
        return R.layout.delegate_home;
    }

    @Override
    //绑定控件
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        testRestClient();
    }

    private void testRestClient() {
        RestClient.builder()
                .url("http://www.baidu.com/")
                .loader(getContext())
                .onSuccess(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {

                    }
                })
                .onError(new IError() {
                    @Override
                    public void onError(int code, String msg) {

                    }
                })
                .onFailure(new IFailure() {
                    @Override
                    public void onFailure() {

                    }
                })
                .build()
                .get();
    }
}
