package com.dididi.pocket.core.ui.dialog

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.support.v4.view.ViewPager
import android.view.View
import com.dididi.pocket.core.R
import com.dididi.pocket.core.ui.adapter.PhotoPagerAdapter


/**
 * @author dididi(叶超)
 * @email yc512yc@163.com
 * @since 09/04/2019
 * @describe 照片查看器
 */

class PhotoDialog : BaseDialog() {

    override val dialogTag: String
        get() = "PhotoDialog"

    private lateinit var bitmaps: List<Bitmap>

    fun create(photoList:List<Bitmap>): PhotoDialog {
        bitmaps = photoList
        return this
    }

    override fun getLayoutRes() = R.layout.dialog_photo

    @SuppressLint("ClickableViewAccessibility")
    override fun bindView(view: View) {
        //todo:照片查看有bug 无法显示照片
        val photoAdapter = PhotoPagerAdapter(bitmaps,this)
        val viewPager = view.findViewById<ViewPager>(R.id.dialog_photo_view_pager)
        viewPager.adapter = photoAdapter
    }
}