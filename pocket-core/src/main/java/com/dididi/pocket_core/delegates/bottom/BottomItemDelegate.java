package com.dididi.pocket_core.delegates.bottom;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.dididi.pocket_core.R;
import com.dididi.pocket_core.Util.LogUtil;
import com.dididi.pocket_core.delegates.PocketDelegate;

import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator;
import me.yokeyword.fragmentation.anim.FragmentAnimator;

/**
 * Created by dididi
 * on 24/07/2018 .
 */

public abstract class BottomItemDelegate extends PocketDelegate implements View.OnKeyListener {
    //页面容器

    private long mExitTime = 0;
    private static final int EXIT_TIME = 2000;

    @Override
    public void onResume() {
        super.onResume();
        final View rootView = getView();
        if (rootView != null) {
            //resume的时候重新获取焦点,具体原因查询fragment文档
            rootView.setFocusableInTouchMode(true);
            rootView.requestFocus();
            rootView.setOnKeyListener(this);
        }
    }

    @Override
    //设置连续点击返回按键退出app
    public boolean onKey(View view, int i, KeyEvent keyEvent) {
        if (i == KeyEvent.KEYCODE_BACK && keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - mExitTime) >= EXIT_TIME) {
                Toast.makeText(getContext(), "双击退出" + getString(R.string.app_name),
                        Toast.LENGTH_SHORT).show();
                mExitTime = System.currentTimeMillis();
            } else {
                //结束宿主activity,快速双击退出
                _mActivity.finish();
                if (mExitTime != 0) {
                    mExitTime = 0;
                }
            }
            //完成事件处理
            return true;
        }
        return false;
    }
}
