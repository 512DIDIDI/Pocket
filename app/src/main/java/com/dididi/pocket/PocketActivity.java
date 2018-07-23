package com.dididi.pocket;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;

import com.dididi.pocket.ec.sign.SignInDelegate;
import com.dididi.pocket_core.activities.ProxyActivity;
import com.dididi.pocket_core.delegates.PocketDelegate;


public class PocketActivity extends ProxyActivity {
    //此时PocketActivity只是作为PocketDelegate的容器，实际加载的是PocketDelegate的布局
    @Override
    //设置根布局
    public PocketDelegate setRootDelegate() {
        return new SignInDelegate();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
    }
}
