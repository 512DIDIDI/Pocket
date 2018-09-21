package com.dididi.pocket.core.Util;

import android.util.Log;

import com.google.gson.JsonParser;

/**
 * Created by dididi
 * on 18/07/2018 .
 */

@SuppressWarnings("WeakerAccess")
public class LogUtil {
    //日志工具类

    public static final int VERBOSE = 0;
    public static final int DEBUG = 1;
    public static final int INFO = 2;
    public static final int WARN = 3;
    public static final int ERROR = 4;
    public static final int NOTHING = 5;
    public static int level = VERBOSE;

    public static void v(String TAG, String msg) {
        if (level <= VERBOSE) {
            Log.v(TAG, msg);
        }
    }

    public static void d(String TAG, String msg) {
        if (level <= DEBUG) {
            Log.d(TAG, msg);
        }
    }

    public static void i(String TAG, String msg) {
        if (level <= INFO) {
            Log.i(TAG, msg);
        }
    }

    public static void w(String TAG, String msg) {
        if (level <= WARN) {
            Log.w(TAG, msg);
        }
    }

    public static void e(String TAG, String msg) {
        if (level <= ERROR) {
            Log.e(TAG, msg);
        }
    }

    public static void json(String TAG,String msg){
        Log.d(TAG, new JsonParser().parse(msg).getAsJsonObject().toString());
    }
}
