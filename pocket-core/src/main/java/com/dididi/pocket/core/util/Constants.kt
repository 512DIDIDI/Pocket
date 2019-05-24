package com.dididi.pocket.core.util

import android.os.Environment
import com.dididi.pocket.core.app.Pocket


/**
 * @author dididi(叶超)
 * @email yc512yc@163.com
 * @since 22/05/2019
 * @describe
 */

class Constants {
    companion object {
        const val SDK_APP_ID = 1400211604
        const val TIM_ACCOUNT_TYPE = "36862"
        const val BASE_URL = "https://www.wanandroid.com"
        val SD_CARD_PATH = Environment.getExternalStorageDirectory().absolutePath
        val APP_DIR = "${Pocket.getBaseIMConfigs().appCacheDir
                ?: SD_CARD_PATH}/${Pocket.getApplicationContext().packageName}"
        //录音文件夹
        val RECORD_DIR = "$APP_DIR/record/"
        //录音下载文件夹
        val RECORD_DOWNLOAD_DIR = "$APP_DIR/record/download/"
        //视频下载文件夹
        val VIDEO_DOWNLOAD_DIR = "$APP_DIR/video/download/"
        //图片文件夹
        val IMAGE_BASE_DIR = "$APP_DIR/image/"
        //图片下载文件夹
        val IMAGE_DOWNLOAD_DIR = "${IMAGE_BASE_DIR}download/"
        //媒体文件夹
        val MEDIA_DIR = "$APP_DIR/media"
        //文件下载文件夹
        val FILE_DOWNLOAD_DIR = "$APP_DIR/file/download/"
        //异常日志文件夹
        val CRASH_LOG_DIR = "$APP_DIR/crash/"

        const val CAMERA_IMAGE_PATH = "camera_image_path"
        const val IMAGE_WIDTH = "image_width"
        const val IMAGE_HEIGHT = "image_height"
        const val VIDEO_TIME = "video_time"
        const val CAMERA_VIDEO_PATH = "camera_video_path"
        const val UI_PARAMS = "ilive_ui_params"
        const val SOFT_KEY_BOARD_HEIGHT = "soft_key_board_height"
        const val NAVIGATION_BAR_HEIGHT = "navigation_bar_height"


        const val IMAGE_DATA = "image_data"
        const val SELF_MESSAGE = "self_message"
        const val ITENT_DATA = "intent_data"
        const val GROUP_ID = "group_id"
        const val CAMERA_TYPE = "camera_type"
    }
}