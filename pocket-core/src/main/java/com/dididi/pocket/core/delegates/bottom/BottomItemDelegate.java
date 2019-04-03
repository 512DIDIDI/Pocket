package com.dididi.pocket.core.delegates.bottom;

import android.widget.Toast;

import com.dididi.pocket.core.R;
import com.dididi.pocket.core.app.Pocket;
import com.dididi.pocket.core.delegates.PocketDelegate;

/**
 * @author dididi
 * @describe 实现IClickTabListener,监听初始页面的四个tab按钮点击事件
 * @since 24/07/2018 .
 */

public abstract class BottomItemDelegate extends PocketDelegate implements IClickTabListener{
    //页面容器

    private long mExitTime = 0;
    private static final int EXIT_TIME = 2000;

    @Override
    public boolean onBackPressedSupport() {
        if ((System.currentTimeMillis() - mExitTime) < EXIT_TIME) {
            getProxyActivity().finish();
        } else {
            mExitTime = System.currentTimeMillis();
            Toast.makeText(getProxyActivity(),
                    "双击退出" + Pocket.getApplicationContext().getString(R.string.app_name),
                    Toast.LENGTH_SHORT).show();
        }
        return true;
    }

}
