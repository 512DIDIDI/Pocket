package com.dididi.pocket.core.delegates.bottom;

import android.widget.Toast;

import com.dididi.pocket.core.R;
import com.dididi.pocket.core.app.Pocket;
import com.dididi.pocket.core.delegates.PocketDelegate;

/**
 * Created by dididi
 * on 24/07/2018 .
 */

public abstract class BottomItemDelegate extends PocketDelegate {
    //页面容器

    private long mExitTime = 0;
    private static final int EXIT_TIME = 2000;

    @Override
    public boolean onBackPressedSupport() {
        if ((System.currentTimeMillis() - mExitTime) < EXIT_TIME) {
            mActivity.finish();
        } else {
            mExitTime = System.currentTimeMillis();
            Toast.makeText(mActivity,
                    "双击退出" + Pocket.getApplicationContext().getString(R.string.app_name),
                    Toast.LENGTH_SHORT).show();
        }
        return true;
    }

}
