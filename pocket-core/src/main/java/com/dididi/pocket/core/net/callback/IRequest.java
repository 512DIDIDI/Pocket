package com.dididi.pocket.core.net.callback;

/**
 * Created by dididi
 * on 22/07/2018 .
 */

public interface IRequest {
    //请求开始加载loader
    void onRequestStart();
    //请求完成结束loader
    void onRequestEnd();
}
