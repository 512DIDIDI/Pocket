package com.dididi.pocket.core.ui.adapter

import android.graphics.Bitmap
import android.support.v4.view.PagerAdapter
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.dididi.pocket.core.ui.dialog.BaseDialog
import com.github.chrisbanes.photoview.PhotoView


/**
 * @author dididi(叶超)
 * @email yc512yc@163.com
 * @since 22/04/2019
 * @describe 照片查看器pager adapter
 */

class PhotoPagerAdapter(var photoList: List<Bitmap>, var dialog: BaseDialog) : PagerAdapter() {

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val bitmap = photoList[position]
        val photoView = PhotoView(dialog.context)
        Glide.with(dialog)
                .load(bitmap)
                .into(photoView)
        container.addView(photoView)
        photoView.setOnClickListener {
            dialog.dismiss()
        }
        return container
    }

    override fun isViewFromObject(p0: View, p1: Any) = p0 == p1

    override fun getCount() = photoList.size

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View?)
    }
}