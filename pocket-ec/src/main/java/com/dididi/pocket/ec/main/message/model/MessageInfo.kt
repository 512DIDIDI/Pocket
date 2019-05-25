package com.dididi.pocket.ec.main.message.model

import android.graphics.Bitmap

import com.dididi.pocket.core.entity.Message

import java.io.Serializable

class MessageInfo : Serializable, Comparable<MessageInfo> {

    /**
     * 消息未读数
     */
    var unRead: Int = 0
    /**
     * 会话ID，目前与peer保持一致
     */
    var sessionId: String? = null
    /**
     * 会话标识，C2C为对方用户ID，群聊为群组ID
     */
    var peer: String? = null
    /**
     * 会话头像url
     */
    var iconUrl: String? = null
    /**
     * 会话标题
     */
    var title: String? = null

    /**
     * 会话头像
     */
    var icon: Bitmap? = null
    /**
     * 是否为群会话
     */
    var isGroup: Boolean = false
    /**
     * 是否为置顶会话
     */
    var isTop: Boolean = false
    /**
     * 最后一条消息时间
     */
    var lastMessageTime: Long = 0
    /**
     * 最后一条消息，MessageInfo对象
     */
    var lastMessage: Message? = null

    override fun compareTo(other: MessageInfo): Int {
        return if (this.lastMessageTime > other.lastMessageTime) -1 else 1
    }
}
