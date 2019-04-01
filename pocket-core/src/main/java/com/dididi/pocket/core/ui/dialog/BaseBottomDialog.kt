package com.dididi.pocket.core.ui.dialog

import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dididi.pocket.core.R


/**
 * @author dididi(叶超)
 * @email yc512yc@163.com
 * @since 01/04/2019
 * @describe
 */

abstract class BaseBottomDialog:DialogFragment(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.pocket_dialog)
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    /**
     * 返回布局
     */
    @LayoutRes
    abstract fun getLayoutRes():Int

    /**
     * 控件绑定
     */
    abstract fun bindView(view: View)
}