package com.dididi.pocket.core.im


/**
 * @author dididi(叶超)
 * @email yc512yc@163.com
 * @since 23/05/2019
 * @describe IM回调
 */

interface IIMCallback {
    /**
     * 成功
     * @param data 返回的消息
     */
    fun onSuccess(data: Any?)

    /**
     * 失败
     * @param module 标志位
     * @param errCode 错误码
     * @param errMsg 错误信息
     */
    fun onError(module: String, errCode: Int, errMsg: String?)

}