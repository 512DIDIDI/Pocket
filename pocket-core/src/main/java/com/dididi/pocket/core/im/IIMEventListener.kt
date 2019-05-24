package com.dididi.pocket.core.im

import com.dididi.pocket.core.util.LogUtil
import com.tencent.imsdk.TIMConversation
import com.tencent.imsdk.TIMGroupTipsElem
import com.tencent.imsdk.TIMMessage


/**
 * @author dididi(叶超)
 * @email yc512yc@163.com
 * @since 22/05/2019
 * @describe IM事件监听
 */

interface IIMEventListener {

    companion object {
        private const val TAG = "IIMEventListener"
    }

    /**
     * 用户被踢下线
     */
    fun onForceOffline() {
        LogUtil.d(TAG, "onForceOffline")
    }

    /**
     * 用户票据过期
     */
    fun onUserSignExpired() {
        LogUtil.d(TAG, "onUserSignExpired")
    }

    /**
     * 连接建立
     */
    fun onConnected() {
        LogUtil.d(TAG, "onConnected")
    }

    /**
     * 连接断开
     * @param code 错误码
     * @param code 错误描述
     */
    fun onDisconnected(code: Int, desc: String) {
        LogUtil.d(TAG, "onDisconnected , code:$code | desc:$desc")
    }

    /**
     * 验证wifi
     * @param name wifi名称
     */
    fun onWifiNeedAuth(name: String) {
        LogUtil.d(TAG, "onWifiNeedAuth , wifi name:$name")
    }

    /**
     * 部分会话刷新（多终端已读上报同步）
     * @param conversations 需要刷新的会话列表
     */
    fun onRefreshConversation(conversations: List<TIMConversation>) {
        LogUtil.d(TAG, "onRefreshConversation , size:${conversations.size}")
    }

    /**
     * 收到新消息回调
     * @param messages 收到的新消息
     */
    fun onNewMessage(messages: List<TIMMessage>) {
        LogUtil.d(TAG, "onNewMessage , size:${messages.size}")
    }

    /**
     * 群Tips事件通知回调
     * @param elem 群tips消息
     */
    fun onGroupTipsEvent(elem: TIMGroupTipsElem) {
        LogUtil.d(TAG,"onGroupTipsEvent , groupId:${elem.groupId} | type:${elem.tipsType}")
    }
}