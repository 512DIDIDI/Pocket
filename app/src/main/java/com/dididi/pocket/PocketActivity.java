package com.dididi.pocket;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.widget.Toast;

import com.dididi.pocket.ec.main.PocketBottomDelegate;
import com.dididi.pocket.ec.sign.ISignListener;
import com.dididi.pocket.ec.sign.SignInDelegate;
import com.dididi.pocket_core.activities.ProxyActivity;
import com.dididi.pocket_core.app.AccountManager;
import com.dididi.pocket_core.app.IUserChecker;
import com.dididi.pocket_core.delegates.PocketDelegate;


public class PocketActivity extends ProxyActivity implements ISignListener {
    //此时PocketActivity只是作为PocketDelegate的容器，实际加载的是PocketDelegate的布局
    @Override
    //设置根布局
    public PocketDelegate setRootDelegate() {
        final PocketDelegate[] root = new PocketDelegate[1];
        AccountManager.checkAccount(new IUserChecker() {
            @Override
            public void onSignIn() {
                root[0] = new PocketBottomDelegate();
            }

            @Override
            public void onNotSignIn() {
                root[0] = new SignInDelegate();
            }
        });
        return root[0];
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
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
