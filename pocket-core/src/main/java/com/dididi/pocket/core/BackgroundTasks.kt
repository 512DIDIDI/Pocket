/**
 * @file
 * @brief 后台任务管理器
 * @author rjlee
 * @date 2014年12月6日
 * Modify List:
 * 2014年12月6日 rjlee create it.
 */
package com.dididi.pocket.core

import android.os.Handler


class BackgroundTasks {

    val handler = Handler()


    fun runOnUiThread(runnable: Runnable) {
        handler.post(runnable)
    }

    fun postDelayed(r: Runnable, delayMillis: Long): Boolean {
        return handler.postDelayed(r, delayMillis)
    }

    companion object {

        var instance: BackgroundTasks? = null
            private set


        // 需要在主线程中初始化
        fun initInstance() {
            instance = BackgroundTasks()
        }
    }


}
