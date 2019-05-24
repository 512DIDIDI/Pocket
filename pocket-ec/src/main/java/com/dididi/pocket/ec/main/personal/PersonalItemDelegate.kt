package com.dididi.pocket.ec.main.personal

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.widget.AppCompatTextView
import android.view.View
import com.dididi.pocket.core.app.AccountManager
import com.dididi.pocket.core.app.IUserChecker
import com.dididi.pocket.core.delegates.PocketDelegate
import com.dididi.pocket.core.delegates.bottom.BottomItemDelegate
import com.dididi.pocket.core.fakedata.FakeUser
import com.dididi.pocket.core.ui.GlideApp
import com.dididi.pocket.core.ui.dialog.PhotoBottomDialog
import com.dididi.pocket.core.util.PocketPreferences
import com.dididi.pocket.ec.R
import com.dididi.pocket.ec.sign.SignDelegate
import com.gyf.immersionbar.ktx.immersionBar
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.delegate_personal_personal.*
import me.yokeyword.fragmentation.ISupportFragment


/**
 * Created by dididi
 * on 24/07/2018 .
 */

class PersonalItemDelegate : BottomItemDelegate() {

    private lateinit var photoBottomDialog: PhotoBottomDialog
    private var mEmail: AppCompatTextView? = null
    private var mName: AppCompatTextView? = null
    private var mAvatar: CircleImageView? = null

    override fun setLayout(): Any {
        return R.layout.delegate_personal_personal
    }

    override fun onBindChildView(savedInstanceState: Bundle?, rootView: View?) {
    }

    override fun onBindView(savedInstanceState: Bundle?, rootView: View?) {
        mEmail = rootView?.findViewById(R.id.delegate_personal_personal_email)
        mAvatar = rootView?.findViewById(R.id.delegate_personal_personal_avatar)
        mName = rootView?.findViewById(R.id.delegate_personal_personal_name)
        initUserIfo()
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

    private fun initUserIfo() {
        AccountManager.checkAccount(object : IUserChecker {
            override fun onSignIn() {
                //如果登录，从sp中获取用户信息
                val avatar = PocketPreferences
                        .getCustomPocketProfile("userAvatar")
                        .replace("\"", "")
                val name = PocketPreferences
                        .getCustomPocketProfile("userName")
                        .replace("\"", "")
                val email = PocketPreferences
                        .getCustomPocketProfile("userEmail")
                        .replace("\"", "")
                GlideApp.with(this@PersonalItemDelegate)
                        .load(avatar)
                        .into(mAvatar!!)
                mName!!.text = name
                mEmail!!.text = email
            }

            override fun onNotSignIn() {
                val me = FakeUser.getUser("1")
                GlideApp.with(this@PersonalItemDelegate)
                        .load(Integer.parseInt(me.avatar))
                        .into(mAvatar!!)
                mName!!.text = me.name
                mEmail!!.text = me.email
                mName!!.setOnClickListener {
                    getParentDelegate<PocketDelegate>().supportDelegate.startWithPop(SignDelegate())
                }
                mEmail!!.setOnClickListener {
                    getParentDelegate<PocketDelegate>().supportDelegate.startWithPop(SignDelegate())
                }
            }
        })
    }

    override fun onScrollToTop() {

    }

    override fun onRefresh() {

    }

    override fun isTop(): Boolean {
        return false
    }
}
