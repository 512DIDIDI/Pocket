package com.dididi.pocket.ec.sign

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.Toast
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.SizeUtils
import com.dididi.pocket.core.app.Pocket
import com.dididi.pocket.core.delegates.PocketDelegate
import com.dididi.pocket.core.net.RestClient
import com.dididi.pocket.core.ui.animation.PocketAnimation
import com.dididi.pocket.core.util.AutoBarUtil
import com.dididi.pocket.core.util.LogUtil
import com.dididi.pocket.ec.R
import com.dididi.pocket.ec.main.PocketBottomDelegate
import kotlinx.android.synthetic.main.delegate_sign.*

/**
 * @author dididi(yechao)
 * @describe
 * @since 29/10/2018
 */

class SignDelegate : PocketDelegate(), View.OnClickListener {

    private var mSignListener: ISignListener? = null

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is ISignListener){
            mSignListener = context
        }
    }

    override fun setLayout(): Any {
        return R.layout.delegate_sign
    }

    override fun onBindChildView(savedInstanceState: Bundle?, rootView: View?) {
    }

    override fun onBindView(savedInstanceState: Bundle?, rootView: View?) {
        delegate_sign_background_layout_enter_sign!!.setOnClickListener(this)
        delegate_sign_sign_in_layout_sign_up!!.setOnClickListener(this)
        delegate_sign_sign_in_layout_forget_password!!.setOnClickListener(this)
        delegate_sign_forget_password_layout_back!!.setOnClickListener(this)
        delegate_sign_sign_up_layout_back!!.setOnClickListener(this)
        delegate_sign_forget_password_layout_next!!.setOnClickListener(this)
        delegate_sign_sign_in_layout_sign_in!!.setOnClickListener(this)
        delegate_sign_sign_up_layout_sign_up!!.setOnClickListener(this)
        delegate_sign_forget_password_layout_send_verify!!.setOnClickListener(this)
        delegate_sign_reset_password_layout_login!!.setOnClickListener(this)
        delegate_sign_reset_password_layout_back!!.setOnClickListener(this)
        if (activity != null) {
            Log.d("changeColor", "onBindChildView: ")
            AutoBarUtil.changeBarColor(activity!!, AutoBarUtil.COLOR_GRAY.toInt())
        }
    }

    override fun onClick(v: View) {
        if (v.id == R.id.delegate_sign_background_layout_enter_sign) {
            getSignAnimation()
        } else if (v.id == R.id.delegate_sign_sign_in_layout_sign_up) {
            getSignUpAnimation()
        } else if (v.id == R.id.delegate_sign_sign_in_layout_forget_password) {
            getForgetPasswordAnimation()
        } else if (v.id == R.id.delegate_sign_sign_up_layout_back) {
            getSignUpBackAnimation()
        } else if (v.id == R.id.delegate_sign_forget_password_layout_back) {
            getForgetPasswordBackAnimation()
        } else if (v.id == R.id.delegate_sign_forget_password_layout_next) {
            getForgetPasswordNextAnimation()
        } else if (v.id == R.id.delegate_sign_sign_up_layout_sign_up) {
            //注册事件
            if (checkRegisterInputValid()) {
                RestClient.builder()
                        .url("https://www.wanandroid.com/user/register")
                        .params("username", delegate_sign_sign_up_layout_account!!.text!!.toString())
                        .params("password", delegate_sign_sign_up_layout_password!!.text!!.toString())
                        .params("repassword", delegate_sign_sign_up_layout_reenter_password!!.text!!.toString())
                        .onSuccess { response -> Toast.makeText(context, "注册成功$response", Toast.LENGTH_SHORT).show() }
                        .onError { _, msg -> Toast.makeText(context, msg, Toast.LENGTH_SHORT).show() }
                        .build()
                        .post()
            }
        } else if (v.id == R.id.delegate_sign_sign_in_layout_sign_in) {
            //登录事件
            if (checkLoginInputValid()) {
                RestClient.builder()
                        .url("https://www.wanandroid.com/user/login")
                        .params("username", delegate_sign_sign_in_layout_account!!.text!!.toString())
                        .params("password", delegate_sign_sign_in_layout_password!!.text!!.toString())
                        .onSuccess { response -> LogUtil.d("loginResponse", response) }
                        .onError { _, msg -> Toast.makeText(context, msg, Toast.LENGTH_SHORT).show() }
                        .build()
                        .post()
            } else {
                supportDelegate.startWithPop(PocketBottomDelegate())
            }
        } else if (v.id == R.id.delegate_sign_reset_password_layout_login) {
            //重设密码事件
            if (checkResetPasswordInputValid()) {
                RestClient.builder()
                        .url("")
                        .params("", delegate_sign_reset_password_layout_password!!.text!!.toString())
                        .params("", delegate_sign_reset_password_layout_reenter_password!!.text!!.toString())
                        .onSuccess { response ->
                            if (response.contains("\"code\":1")) {
                                LogUtils.d(response)
                                SignHandler.onSignIn(response, mSignListener!!)
                                supportDelegate.startWithPop(PocketBottomDelegate())
                            } else {
                                LogUtils.d(response)
                                Toast.makeText(Pocket.getApplicationContext(),
                                        "登录失败,请重新输入用户名和密码", Toast.LENGTH_SHORT).show()
                            }
                        }
                        .onError { code, msg -> LogUtil.d("response:", code.toString() + msg) }
                        .build()
                        .get()
            }
        } else if (v.id == R.id.delegate_sign_forget_password_layout_send_verify) {
            if (checkForgetPasswordInputValid()) {
                //todo:发送验证码事件
            }
        } else if (v.id == R.id.delegate_sign_reset_password_layout_back) {
            getResetPasswordBackAnimation()
        }
    }

    /**
     * SignLayout动画封装
     */
    private fun getSignAnimation() {
        delegate_sign_background_layout_enter_sign!!.visibility = View.INVISIBLE
        getEnterSignAnimation()
    }

    /**
     * 注册页面动画切换
     */
    private fun getSignUpAnimation() {
        PocketAnimation.setFlipAnimation(context, delegate_sign_sign_layout, delegate_sign_sign_in_layout, delegate_sign_sign_up_layout)
    }

    /**
     * 注册页面返回按钮动画切换
     */
    private fun getSignUpBackAnimation() {
        PocketAnimation.setFlipAnimation(context, delegate_sign_sign_layout, delegate_sign_sign_up_layout, delegate_sign_sign_in_layout)
    }

    /**
     * 找回密码页面动画切换
     */
    private fun getForgetPasswordAnimation() {
        PocketAnimation.setFlipAnimation(context,
                delegate_sign_sign_layout, delegate_sign_sign_in_layout, delegate_sign_forget_password_layout)
    }

    /**
     * 找回页面返回按钮动画切换
     */
    private fun getForgetPasswordBackAnimation() {
        PocketAnimation.setFlipAnimation(context,
                delegate_sign_sign_layout, delegate_sign_forget_password_layout, delegate_sign_sign_in_layout)
    }

    /**
     * 找回密码下一步动画切换
     */
    private fun getForgetPasswordNextAnimation() {
        PocketAnimation.setFlipAnimation(context,
                delegate_sign_sign_layout, delegate_sign_forget_password_layout, delegate_sign_reset_password_layout)
    }

    /**
     * 重设密码返回动画切换
     */
    private fun getResetPasswordBackAnimation() {
        PocketAnimation.setFlipAnimation(context,
                delegate_sign_sign_layout, delegate_sign_reset_password_layout, delegate_sign_forget_password_layout)
    }

    /**
     * 点击进入Sign页面动画
     */
    private fun getEnterSignAnimation() {
        //logo出现
        //这里用一个隐藏的logo用来定位最后logo应该位移多少
        //安卓中定位原点在左上角
        //故位移距离计算公式 =（隐藏的logo位置-backgroundLogo位置）
        PocketAnimation.setTranslationAnimation(delegate_sign_background_layout_logo,
                PocketAnimation.TRANSLATION_Y, delegate_sign_background_layout_logo!!.translationY,
                delegate_sign_logo!!.y - delegate_sign_background_layout_logo!!.y,
                0, 500, false)
        PocketAnimation.setTranslationAnimation(delegate_sign_background_layout_name,
                PocketAnimation.TRANSLATION_Y, delegate_sign_background_layout_name!!.translationY,
                delegate_sign_name!!.y - delegate_sign_background_layout_name!!.y,
                0, 500, false)
        //Sign页面及个人标识出现
        PocketAnimation.setMoveDelayAnimation(PocketAnimation.TRANSLATION_Y,
                SizeUtils.dp2px(200f).toFloat(), 0F, 1F, true,
                500, 100, 500, delegate_sign_sign_layout, delegate_sign_dididi_studio)
        //登录信息页面依次从右往左出现
        PocketAnimation.setMoveDelayAnimation(PocketAnimation.TRANSLATION_X,
                SizeUtils.dp2px(100f).toFloat(), 0F, 1F, true,
                1000, 100, 500,
                delegate_sign_sign_in_layout_login, delegate_sign_sign_in_layout_account, delegate_sign_sign_in_layout_password,
                delegate_sign_sign_in_layout_sign_in, delegate_sign_sign_in_layout_sign_up, delegate_sign_sign_in_layout_forget_password)
    }

    /**
     * 验证登录页面的内容输入是否正确
     */
    private fun checkLoginInputValid(): Boolean {
        var isValid = true
        if (delegate_sign_sign_in_layout_account!!.text == null
                || "" == delegate_sign_sign_in_layout_account!!.text!!.toString()
                || delegate_sign_sign_in_layout_password!!.text == null
                || "" == delegate_sign_sign_in_layout_password!!.text!!.toString()) {
            Toast.makeText(context, "不能输入为空", Toast.LENGTH_SHORT).show()
            isValid = false
        } else {
            val email = delegate_sign_sign_in_layout_account!!.text!!.toString()
            val password = delegate_sign_sign_in_layout_password!!.text!!.toString()
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                isValid = false
                delegate_sign_sign_in_layout_account!!.error = "错误的邮箱格式"
            } else {
                delegate_sign_sign_in_layout_account!!.error = null
            }
            if (password.length <= 6) {
                isValid = false
                delegate_sign_sign_in_layout_password!!.error = "密码错误"
            } else {
                delegate_sign_sign_in_layout_password!!.error = null
            }
        }
        return isValid
    }

    /**
     * 验证注册信息是否输入正确
     */
    private fun checkRegisterInputValid(): Boolean {
        var isValid = true
        if (delegate_sign_sign_up_layout_account!!.text == null
                || delegate_sign_sign_up_layout_reenter_password!!.text == null
                || delegate_sign_sign_up_layout_password!!.text == null) {
            isValid = false
        } else {
            val email = delegate_sign_sign_up_layout_account!!.text!!.toString()
            val password = delegate_sign_sign_up_layout_password!!.text!!.toString()
            val rePassword = delegate_sign_sign_up_layout_reenter_password!!.text!!.toString()
            if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                isValid = false
                delegate_sign_sign_up_layout_account!!.error = "错误的邮箱格式"
            } else {
                delegate_sign_sign_up_layout_account!!.error = null
            }
            if (password.isEmpty() || password.length <= 6) {
                isValid = false
                delegate_sign_sign_up_layout_password!!.error = "密码位数不能低于6位"
            } else {
                delegate_sign_sign_up_layout_password!!.error = null
            }
            if (rePassword != password) {
                isValid = false
                delegate_sign_sign_up_layout_reenter_password!!.error = "密码不一致"
            } else {
                delegate_sign_sign_up_layout_reenter_password!!.error = null
            }
        }
        return isValid
    }

    /**
     * 验证找回密码页面的内容输入是否正确
     */
    private fun checkForgetPasswordInputValid(): Boolean {
        var isValid = true
        if (delegate_sign_forget_password_layout_account!!.text == null
                || "" == delegate_sign_forget_password_layout_account!!.text!!.toString()
                || delegate_sign_forget_password_layout_verify!!.text == null
                || "" == delegate_sign_forget_password_layout_verify!!.text!!.toString()) {
            Toast.makeText(context, "不能输入为空", Toast.LENGTH_SHORT).show()
            isValid = false
        } else {
            val email = delegate_sign_forget_password_layout_account!!.text!!.toString()
            val verify = delegate_sign_forget_password_layout_verify!!.text!!.toString()
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                isValid = false
                delegate_sign_forget_password_layout_account!!.error = "错误的邮箱格式"
            } else {
                delegate_sign_forget_password_layout_account!!.error = null
            }
            if (verify.length <= 4) {
                isValid = false
                delegate_sign_forget_password_layout_verify!!.error = "验证码输入错误"
            } else {
                delegate_sign_forget_password_layout_verify!!.error = null
            }
        }
        return isValid
    }

    /**
     * 验证重设密码页面的内容输入是否正确
     */
    private fun checkResetPasswordInputValid(): Boolean {
        var isValid = true
        if (delegate_sign_reset_password_layout_password!!.text == null
                || "" == delegate_sign_reset_password_layout_password!!.text!!.toString()
                || delegate_sign_reset_password_layout_reenter_password!!.text == null
                || "" == delegate_sign_reset_password_layout_reenter_password!!.text!!.toString()) {
            Toast.makeText(context, "不能输入为空", Toast.LENGTH_SHORT).show()
            isValid = false
        } else {
            val password = delegate_sign_reset_password_layout_password!!.text!!.toString()
            val rePassword = delegate_sign_reset_password_layout_reenter_password!!.text!!.toString()
            if (password.isEmpty() || password.length <= 6) {
                isValid = false
                delegate_sign_reset_password_layout_password!!.error = "密码位数不能低于6位"
            } else {
                delegate_sign_reset_password_layout_password!!.error = null
            }
            if (rePassword != password) {
                isValid = false
                delegate_sign_reset_password_layout_reenter_password!!.error = "密码不一致"
            } else {
                delegate_sign_reset_password_layout_reenter_password!!.error = null
            }
        }
        return isValid
    }
}
