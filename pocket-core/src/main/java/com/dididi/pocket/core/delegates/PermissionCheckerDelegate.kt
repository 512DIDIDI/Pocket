package com.dididi.pocket.core.delegates

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.support.v4.content.ContextCompat
import android.support.v4.content.FileProvider
import android.widget.ImageView
import java.io.File

/**
 * Created by dididi
 * on 18/07/2018 .
 * @describe 权限管理
 */

abstract class PermissionCheckerDelegate : BaseDelegate() {

    private var photo:File? = null
    private var imageView:ImageView? = null
    protected lateinit var photoUri:Uri

    companion object {
        const val WRITE_EXTERNAL_STORAGE = 1
        const val OPEN_CAMERA = 2
        const val OPEN_ALBUM = 3
    }

    /**
     * 权限申请
     */
    fun applyPermission(context: Context) {
        val permissions = listOf(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        //android6.0之后，需要动态申请读写权限
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //读写是否已经授权
            val check = ContextCompat.checkSelfPermission(context, permissions[0])
            if (check == PackageManager.PERMISSION_GRANTED) {
                openCamera(context)
            } else {
                //如果未发现授权，则请求权限
                requestPermissions(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), WRITE_EXTERNAL_STORAGE)
            }
        } else {
            openCamera(context)
        }
    }

    /**
     * 打开相机
     */
    fun openCamera(context: Context) {
        //实例化相机意图
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        photo = File(Environment.getExternalStorageDirectory().absolutePath,
                "/pocketPicture/" + System.currentTimeMillis() + ".jpg")
        photo!!.parentFile.mkdirs()
        photoUri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            //android7.0之后，不再允许app透露file://Uri给其他app
            //转而使用FileProvider来生成content://Uri取代file://Uri
            FileProvider
                    .getUriForFile(context, "com.dididi.pocket.provider", photo!!)
        } else {
            //7.0之前 直接获取Uri
            Uri.fromFile(photo)
        }
        //将uri存进intent，供相机回调使用 data.getData中获取
        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
        startActivityForResult(intent, OPEN_CAMERA)
    }

}

