package com.dididi.pocket.core.im

import android.os.Parcelable
import com.tencent.imsdk.TIMConversationType


/**
 * @author dididi(叶超)
 * @email yc512yc@163.com
 * @since 23/05/2019
 * @describe 聊天信息
 */

abstract class BaseChatInfo:Parcelable{
    /**
     * 聊天标题
     */
    var chatName: String? = null
    /**
     * 聊天类型
     */
    var type: TIMConversationType? = null
    /**
     * 聊天标识(与谁聊天)
     */
    var peer: String? = null
}