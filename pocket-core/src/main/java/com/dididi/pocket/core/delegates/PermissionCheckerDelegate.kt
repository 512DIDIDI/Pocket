package com.dididi.pocket.core.delegates

import android.Manifest
import android.annotation.TargetApi
import android.content.ContentUris
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.support.v4.content.ContextCompat
import android.support.v4.content.FileProvider
import android.widget.Toast
import java.io.File

/**
 * Created by dididi
 * on 18/07/2018 .
 * @describe 权限管理
 */

abstract class PermissionCheckerDelegate : BaseDelegate() {

    //给子类用于显示的相片地址
    private lateinit var photoUri: Uri
    private lateinit var imagePath: String
    //供裁剪使用
    private lateinit var oriUri: Uri
    private val cropFile = File(Environment.getExternalStorageDirectory().absolutePath,
            "/pocket/picture/" + "crop_photo.jpg")

    companion object {
        const val WRITE_EXTERNAL_STORAGE = 1
        const val OPEN_CAMERA = 2
        const val OPEN_ALBUM = 3
        const val CROP_IMAGE = 4
    }

    /**
     * 相机读写权限申请
     */
    fun applyCameraPermission() {
        applyWritePermission(OPEN_CAMERA) {
            openCamera()
        }
    }

    /**
     * 相册读写权限申请
     */
    fun applyOpenAlbumPermission() {
        applyWritePermission(OPEN_ALBUM) {
            openAlbum()
        }
    }

    /**
     * 获取相机拍下的uri并转为bitmap
     */
    fun getBitmapByCamera() = BitmapFactory
            .decodeStream(context!!.contentResolver.openInputStream(photoUri))!!

    /**
     * 获取相册的图片转为bitmap
     */
    fun getBitmapByAlbum(data: Intent): Bitmap {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            handleImageAfterKitKat(data)
        } else {
            handleImageBeforeKitKat(data)
        }
        oriUri = Uri.parse(imagePath)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            //7.0适配
            oriUri = FileProvider.getUriForFile(context!!, "com.dididi.pocket.provider", File(imagePath))
        }
        return MediaStore.Images.Media.getBitmap(context!!.contentResolver, oriUri)
    }

    /**
     * 打开相机
     */
    private fun openCamera() {
        //创建file于sdcard/pocketPicture/ 以当前时间命名的jpg图像
        File(Environment.getExternalStorageDirectory().absolutePath,
                "/pocket/picture/" + System.currentTimeMillis() + ".jpg").apply {
            parentFile.mkdirs()
            photoUri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                //android7.0之后，不再允许app透露file://Uri给其他app
                //转而使用FileProvider来生成content://Uri取代file://Uri
                FileProvider
                        .getUriForFile(context!!, "com.dididi.pocket.provider", this)
            } else {
                //7.0之前 直接获取Uri
                Uri.fromFile(this)
            }
        }
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).apply {
            //将uri存进intent，供相机回调使用 data.getData中获取
            putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
            startActivityForResult(this, OPEN_CAMERA)
        }
    }

    /**
     * 打开相册
     */
    private fun openAlbum() {
        Intent(Intent.ACTION_GET_CONTENT).apply {
            type = "image/*"
            startActivityForResult(this, OPEN_ALBUM)
        }
    }

    /**
     * 裁剪Uri
     * @param oriUri 原始Uri
     * @param desUri 目标Uri
     */
    fun cropImageUri(oriUri: Uri, desUri: Uri, aspectX: Int, aspectY: Int, width: Int, height: Int) {
        Intent("com.android.camera.action.CROP").apply {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            }
            setDataAndType(oriUri, "image/*")
            putExtra("crop", "true")
            putExtra("aspectX", aspectX)
            putExtra("aspectY", aspectY)
            putExtra("outputX", width)
            putExtra("outputY", height)
            putExtra("scale", true)
            //将剪切的图片保存到目标Uri中
            putExtra(MediaStore.EXTRA_OUTPUT, desUri)
            putExtra("return-data", false)
            putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString())
            putExtra("noFaceDetection", true)
            this@PermissionCheckerDelegate.startActivityForResult(this, CROP_IMAGE)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<out String>,
                                            grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        //权限请求结果
        when (requestCode) {
            WRITE_EXTERNAL_STORAGE -> {
                permissionHint(grantResults, "没有读写权限") {}
            }
            OPEN_CAMERA -> {
                permissionHint(grantResults, "没有读写权限") {
                    openCamera()
                }
            }
            OPEN_ALBUM -> {
                permissionHint(grantResults, "没有读写权限") {
                    openAlbum()
                }
            }
            else -> {
                Toast.makeText(context, "没有权限", Toast.LENGTH_SHORT).show()
            }
        }
    }

    /**
     * 权限结果处理lambda函数
     * @param grantResults 请求结果
     * @param msg toast内容
     * @param target 权限拿到要做什么
     */
    private fun permissionHint(grantResults: IntArray, msg: String, target: () -> Unit) {
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            target()
        } else {
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * 请求读写权限
     * @param requestCode 请求码
     * @param target 要做什么
     */
    private fun applyWritePermission(requestCode: Int, target: () -> Unit) {
        val permissions = listOf(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        //android6.0之后，需要动态申请读写权限
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //读写是否已经授权
            val check = ContextCompat.checkSelfPermission(context!!, permissions[0])
            if (check == PackageManager.PERMISSION_GRANTED) {
                target()
            } else {
                //如果未发现授权，则请求权限
                requestPermissions(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                        requestCode)
            }
        } else {
            target()
        }
    }

    /**
     * android4.4之后，需要解析获取图片真实路径
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    private fun handleImageAfterKitKat(data: Intent) {
        val uri = data.data
        //document类型的Uri
        when {
            DocumentsContract.isDocumentUri(context, uri) -> {
                //通过documentId处理
                val docId = DocumentsContract.getDocumentId(uri)
                when (uri?.authority) {
                    "com.android.externalstorage.documents" -> {
                        val type = docId.split(":")[0]
                        if ("primary".equals(type, ignoreCase = true)) {
                            imagePath = Environment.getExternalStorageDirectory()
                                    .toString() + "/" + docId.split(":")[1]
                        }
                    }
                    //media类型解析
                    "com.android.providers.media.documents" -> {
                        val id = docId.split(":")[1]
                        val type = docId.split(":")[0]
                        val contentUri: Uri? = when (type) {
                            "image" -> MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                            "video" -> MediaStore.Video.Media.EXTERNAL_CONTENT_URI
                            "audio" -> MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
                            else -> null
                        }
                        val selection = "_id=?"
                        val selectionArgs: Array<String> = arrayOf(id)
                        imagePath = getImagePath(contentUri!!, selection, selectionArgs)!!
                    }
                    //downloads文件解析
                    "com.android.providers.downloads.documents" -> {
                        ContentUris.withAppendedId(
                                Uri.parse("content://downloads/public_downloads"), docId.toLong()
                        ).apply {
                            imagePath = getImagePath(this, null, null)!!
                        }
                    }
                    else -> {
                    }
                }
            }
            "content".equals(uri?.scheme, ignoreCase = true) ->
                //content类型数据不需要解析，直接传入生成即可
                imagePath = getImagePath(uri!!, null, null)!!
            "file".equals(uri?.scheme, ignoreCase = true) ->
                //file类型的uri直接获取图片路径即可
                imagePath = uri!!.path!!
        }
    }

    /**
     * android4.4之前可直接获取图片真实uri
     */
    private fun handleImageBeforeKitKat(data: Intent) {
        val uri = data.data
        imagePath = getImagePath(uri!!, null, null)!!
    }

    /**
     * 解析uri及selection
     * 获取图片真实路径
     */
    private fun getImagePath(uri: Uri, selection: String?, selectionArgs: Array<String>?): String? {
        var cursor: Cursor? = null
        try {
            cursor = context!!.contentResolver.query(uri, null, selection, selectionArgs, null)
            if (cursor?.moveToFirst()!!) {
                return cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA))
            }
        } finally {
            cursor?.close()
        }
        return null
    }
}

