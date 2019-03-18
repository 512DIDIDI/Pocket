package com.dididi.pocket.core.ui.item

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.dididi.pocket.core.R


/**
 * @author dididi(叶超)
 * @email yc512yc@163.com
 * @since 18/03/2019
 * @describe
 */

class MoreButtonItem : RelativeLayout {

    private lateinit var icon: ImageView
    private lateinit var title: TextView

    private var textTitle: CharSequence? = null
    private var iconSrc: Int? = null

    constructor(context: Context) : super(context) {
        initView(context)
    }

    constructor(context: Context, attributeSet: AttributeSet)
            : super(context, attributeSet) {
        initTypedArray(context, attributeSet)
        initView(context)
    }

    constructor(context: Context, attributeSet: AttributeSet, defStyleAttr: Int)
            : super(context, attributeSet, defStyleAttr) {
        initTypedArray(context, attributeSet)
        initView(context)
    }

    private fun initTypedArray(context: Context, attributeSet: AttributeSet) {
        context.obtainStyledAttributes(attributeSet, R.styleable.MoreButtonItem).run {
            textTitle = getText(R.styleable.MoreButtonItem_more_button_text)
            iconSrc = getResourceId(R.styleable.MoreButtonItem_more_button_icon, 0)
            recycle()
        }
    }

    private fun initView(context: Context) {
        LayoutInflater.from(context).inflate(R.layout.item_more_button, this, true)
        icon = findViewById(R.id.item_more_button_icon)
        title = findViewById(R.id.item_more_button_text)
        icon.setImageResource(iconSrc!!)
        title.text = textTitle
    }
}