package com.dididi.pocket.core.ui.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v4.app.DialogFragment
import android.support.v4.app.FragmentManager
import android.util.Log
import android.view.*
import com.dididi.pocket.core.R


/**
 * @author dididi(叶超)
 * @email yc512yc@163.com
 * @since 01/04/2019
 * @describe
 */

abstract class BaseDialog : DialogFragment() {

    abstract val dialogTag:String

    var isCanceledOnOutside: Boolean = true
    var dimAmount: Float = 1f
    var height: Int = ViewGroup.LayoutParams.WRAP_CONTENT
    var width: Int = ViewGroup.LayoutParams.WRAP_CONTENT
    var gravity: Int = Gravity.CENTER

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.pocket_dialog)
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        dialog.window?.requestFeature(Window.FEATURE_NO_TITLE)
        val view = inflater.inflate(getLayoutRes(), container, false)
        bindView(view)
        dialog.setCanceledOnTouchOutside(isCanceledOnOutside)
        val window = dialog.window
        val params = window?.attributes
        params?.dimAmount = dimAmount
        params?.width = width
        params?.height = height
        params?.gravity = gravity
        window?.attributes = params
        return view
    }

    /**
     * 返回布局
     */
    @LayoutRes
    abstract fun getLayoutRes(): Int

    /**
     * 控件绑定
     */
    abstract fun bindView(view: View)

    fun show(fragmentManager: FragmentManager){
        show(fragmentManager,dialogTag)
    }
}