package com.dididi.pocket_core.delegates.bottom;

import android.widget.Toast;

import com.dididi.pocket_core.R;
import com.dididi.pocket_core.app.Pocket;
import com.dididi.pocket_core.delegates.PocketDelegate;

/**
 * Created by dididi
 * on 24/07/2018 .
 */

public abstract class BottomItemDelegate extends PocketDelegate {
    //页面容器

    private long mExitTime = 0;
    private static final int EXIT_TIME = 2000;

    @Override
    //TODO:双击退出程序, onKeyListener貌似有冲突
    public boolean onBackPressedSupport() {
        if ((System.currentTimeMillis() - mExitTime) < EXIT_TIME) {
            _mActivity.finish();
        } else {
            mExitTime = System.currentTimeMillis();
            Toast.makeText(_mActivity,
                    "双击退出" + Pocket.getApplicationContext().getString(R.string.app_name),
                    Toast.LENGTH_SHORT).show();
        }
        return true;
    }
}
