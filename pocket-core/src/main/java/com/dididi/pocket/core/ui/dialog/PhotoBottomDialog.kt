package com.dididi.pocket.core.ui.dialog

import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.dididi.pocket.core.R
import com.dididi.pocket.core.delegates.PermissionCheckerDelegate


/**
 * @author dididi(叶超)
 * @email yc512yc@163.com
 * @since 02/04/2019
 * @describe 底部相册相机选择栏
 */
class PhotoBottomDialog : BaseDialog() {

    override val dialogTag: String
        get() = "PhotoBottomDialog"
    private var delegate: PermissionCheckerDelegate? = null

    /**
     * 创建dialog必须要调用此方法
     * 否则拿不到相机权限
     */
    fun create(delegate: PermissionCheckerDelegate): PhotoBottomDialog {
        this.delegate = delegate
        return this
    }

    override fun getLayoutRes() = R.layout.dialog_photo_bottom

    override fun bindView(view: View) {
        gravity = Gravity.BOTTOM
        width = ViewGroup.LayoutParams.MATCH_PARENT
        val takePhoto = view.findViewById<TextView>(R.id.dialog_photo_bottom_take_photo)
        val openAlbum = view.findViewById<TextView>(R.id.dialog_photo_bottom_open_album)
        takePhoto.setOnClickListener {
            delegate?.applyCameraPermission()
        }
        openAlbum.setOnClickListener {
            delegate?.applyOpenAlbumPermission()
        }
    }
}