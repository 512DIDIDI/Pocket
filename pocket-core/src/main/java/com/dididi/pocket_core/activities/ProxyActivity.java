package com.dididi.pocket_core.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.FrameLayout;

import com.dididi.pocket_core.R;
import com.dididi.pocket_core.delegates.PocketDelegate;

import me.yokeyword.fragmentation.SupportActivity;


/**
 * Created by dididi
 * on 18/07/2018 .
 */

public abstract class ProxyActivity extends SupportActivity {
    //提供唯一的activity容器(采用单activity多fragment架构)
    //获取根Delegate
    public abstract PocketDelegate setRootDelegate();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initContainer(savedInstanceState);
    }
    //初始化activity容器
    private void initContainer(@Nullable Bundle savedInstanceState) {
        final FrameLayout container = new FrameLayout(this);
        container.setId(R.id.delegate_container);
        setContentView(container);
        //如果存储状态被销毁，需要重新加载
        if (savedInstanceState == null) {
            //加载根Fragment
            loadRootFragment(R.id.delegate_container, setRootDelegate());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //由于是单Activity方式，所以容器销毁的时候可以做一些垃圾回收处理
        System.gc();
        System.runFinalization();
    }
}
