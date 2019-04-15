package com.dididi.pocket.core.ui.dialog

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import android.widget.Toast
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

    @SuppressLint("ClickableViewAccessibility")
    override fun bindView(view: View) {
        val picture = view.findViewById<ImageView>(R.id.dialog_photo_picture)
        val options = RequestOptions().override(1000, 1000).fitCenter()
        Glide.with(context!!)
                .load(mBitmap)
                .apply(options)
                .into(picture)
        val photoMoveGesture = GestureDetector(context, PhotoMoveGestureListener())
        picture.setOnTouchListener { _, event ->
            photoMoveGesture.onTouchEvent(event)
        }
    }

    private val TAG = "PhotoDialog"

    inner class PhotoMoveGestureListener : GestureDetector.OnGestureListener {

        /**
         * 按下触发
         */
        override fun onShowPress(e: MotionEvent?) {
            //Toast.makeText(context,"$TAG onShowPress",Toast.LENGTH_SHORT).show()
        }

        /**
         * 单指轻击抬起操作
         */
        override fun onSingleTapUp(e: MotionEvent?): Boolean {
            Toast.makeText(context, "$TAG onSingleTapUp", Toast.LENGTH_SHORT).show()
            return false
        }

        /**
         * 按下
         */
        override fun onDown(e: MotionEvent?): Boolean {
            Toast.makeText(context, "$TAG onDown", Toast.LENGTH_SHORT).show()
            return true
        }

        /**
         * 拖动松开
         */
        override fun onFling(e1: MotionEvent?, e2: MotionEvent?, velocityX: Float, velocityY: Float): Boolean {
            Toast.makeText(context, "$TAG onFling + velocity:$velocityX + $velocityY", Toast.LENGTH_SHORT).show()
            if (Math.abs(velocityY) > 10000) {
                dismiss()
            }
            return true
        }

        /**
         * 拖动 最终会走向onFling()
         */
        override fun onScroll(e1: MotionEvent?, e2: MotionEvent?, distanceX: Float, distanceY: Float): Boolean {
            Toast.makeText(context, "$TAG onScroll ${e1?.x!! - e2?.x!!} + distanceX$distanceX", Toast.LENGTH_SHORT).show()
            return false
        }

        /**
         * 长按
         */
        override fun onLongPress(e: MotionEvent?) {
            Toast.makeText(context, "$TAG onLongPress", Toast.LENGTH_SHORT).show()
        }
    }
}