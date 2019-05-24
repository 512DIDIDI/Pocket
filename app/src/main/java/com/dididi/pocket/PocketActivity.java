package com.dididi.pocket;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.widget.Toast;

import com.dididi.pocket.core.util.FileUtil;
import com.dididi.pocket.core.util.LogUtil;
import com.dididi.pocket.core.util.PocketPreferences;
import com.dididi.pocket.ec.main.PocketBottomDelegate;
import com.dididi.pocket.ec.sign.ISignListener;
import com.dididi.pocket.ec.sign.SignDelegate;
import com.dididi.pocket.core.activities.ProxyActivity;
import com.dididi.pocket.core.app.AccountManager;
import com.dididi.pocket.core.app.IUserChecker;
import com.dididi.pocket.core.app.Pocket;
import com.dididi.pocket.core.delegates.PocketDelegate;
import com.tencent.imsdk.TIMCallBack;
import com.tencent.imsdk.TIMManager;


public class PocketActivity extends ProxyActivity implements ISignListener {
    //此时PocketActivity只是作为PocketDelegate的容器，实际加载的是PocketDelegate的布局

    private static final int PERMISSION_CODE = 5;

    private static final String TAG = "PocketActivity";

    /**
     * 设置根布局
     */
    @Override
    public PocketDelegate setRootDelegate() {
        final PocketDelegate[] root = new PocketDelegate[1];
        AccountManager.checkAccount(new IUserChecker() {
            @Override
            public void onSignIn() {
                root[0] = new SignDelegate();
            }

            @Override
            public void onNotSignIn() {
                root[0] = new SignDelegate();
            }
        });
        root[0].checkPermission(this, PERMISSION_CODE);
        return root[0];
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        Pocket.getConfigurator().withActivity(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_CODE:
                if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "未全部授权，部分功能将无法使用", Toast.LENGTH_SHORT).show();
                } else {
                    FileUtil.initPath();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
                break;
        }
    }

    @Override
    public void onSignUpSuccess() {
        Toast.makeText(this, "注册成功", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSignInSuccess() {
        Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show();
    }
}
