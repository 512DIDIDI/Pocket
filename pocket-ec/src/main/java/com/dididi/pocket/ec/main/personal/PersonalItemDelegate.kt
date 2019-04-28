package com.dididi.pocket.ec.main.personal

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import com.dididi.pocket.core.delegates.bottom.BottomItemDelegate
import com.dididi.pocket.core.ui.dialog.PhotoBottomDialog
import com.dididi.pocket.ec.R
import com.gyf.immersionbar.ktx.immersionBar
import kotlinx.android.synthetic.main.delegate_personal_personal.*
import me.yokeyword.fragmentation.ISupportFragment


/**
 * Created by dididi
 * on 24/07/2018 .
 */

class PersonalItemDelegate : BottomItemDelegate() {

    private lateinit var photoBottomDialog: PhotoBottomDialog

    override fun setLayout(): Any {
        return R.layout.delegate_personal_personal
    }

    override fun onBindChildView(savedInstanceState: Bundle?, rootView: View?) {
    }

    override fun onBindView(savedInstanceState: Bundle?, rootView: View?) {
        //点击头像弹出从相册或相机更换头像
        delegate_personal_personal_avatar.setOnLongClickListener {
            photoBottomDialog = PhotoBottomDialog().create(this)
            photoBottomDialog.show(fragmentManager!!)
            true
        }
    }

    override fun getTitleBarId() = R.id.delegate_personal_personal_toolbar

    override fun initImmersionBar() {
        immersionBar {
            flymeOSStatusBarFontColor(R.color.textColorWhite)
            keyboardEnable(true)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            OPEN_CAMERA -> {
                if (resultCode == ISupportFragment.RESULT_OK) {
                    cropPhoto(OPEN_CAMERA, width = 400, height = 400)
                }
            }
            OPEN_ALBUM -> {
                if (resultCode == ISupportFragment.RESULT_OK) {
                    cropPhoto(OPEN_ALBUM, data, width = 400, height = 400)
                }
            }
            CROP_IMAGE -> {
                delegate_personal_personal_avatar.setImageBitmap(getBitmapByCrop())
                photoBottomDialog.dismiss()
            }
            else -> {
            }
        }
    }

    override fun onScrollToTop() {

    }

    override fun onRefresh() {

    }

    override fun isTop(): Boolean {
        return false
    }
}
