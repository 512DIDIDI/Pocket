package com.dididi.pocket.core.ui.dialog

import android.graphics.Bitmap
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dididi.pocket.core.R


/**
 * @author dididi(叶超)
 * @email yc512yc@163.com
 * @since 09/04/2019
 * @describe
 */

class PhotoDialog : BaseDialog() {

    override val dialogTag: String
        get() = "PhotoDialog"

    private lateinit var mBitmap: Bitmap

    fun create(bitmap: Bitmap): PhotoDialog {
        mBitmap = bitmap
        return this
    }

    override fun getLayoutRes() = R.layout.dialog_photo

    override fun bindView(view: View) {
        val picture = view.findViewById<ImageView>(R.id.dialog_photo_picture)
        val options = RequestOptions().override(1000,1000).fitCenter()
        Glide.with(context!!)
                .load(mBitmap)
                .apply(options)
                .into(picture)
    }
}