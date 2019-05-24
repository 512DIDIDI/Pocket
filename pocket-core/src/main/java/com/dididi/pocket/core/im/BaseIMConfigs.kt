package com.dididi.pocket.core.im

import com.tencent.imsdk.TIMSdkConfig


/**
 * @author dididi(叶超)
 * @email yc512yc@163.com
 * @since 22/05/2019
 * @describe
 */

class BaseIMConfigs {

    /**
     * 自定义IMSDK配置
     */
    var sdkConfig: TIMSdkConfig? = null

    /**
     * IM事件监听器
     */
    var imEventListener: IIMEventListener? = null

    /**
     * 配置 APP 保存图片/语音/文件/log等数据缓存的目录(一般配置在SD卡目录)
     * <p>
     * 默认为 /sdcard/{packageName}/
     */
    var appCacheDir: String? = null

    companion object {
        fun getDefaultConfgis(): BaseIMConfigs {
            return BaseIMConfigs()
        }
    }
}