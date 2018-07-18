package com.dididi.pocket;

import com.dididi.pocket_core.activities.ProxyActivity;
import com.dididi.pocket_core.delegates.PocketDelegate;


public class PocketActivity extends ProxyActivity {
//此时PocketActivity只是作为PocketDelegate的容器，实际加载的是PocketDelegate的布局
    @Override
    //设置根布局
    public PocketDelegate setRootDelegate() {
        return new HomeDelegate();
    }
}
