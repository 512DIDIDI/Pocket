package com.dididi.pocket.ec.main.message.chat.adapter

import android.os.Looper
import android.support.annotation.MainThread
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.dididi.pocket.core.entity.Message
import com.dididi.pocket.core.ui.GlideApp
import com.dididi.pocket.core.util.LogUtil
import com.dididi.pocket.ec.R
import com.tencent.imsdk.TIMTextElem

/**
 * @author dididi
 * @describe 聊天页面adapter
 * @since 21/09/2018
 */

class ChatAdapter(var layoutResId: Int, var messageList: ArrayList<Message>?) :
        BaseQuickAdapter<Message, BaseViewHolder>(layoutResId, messageList) {

    override fun convert(helper: BaseViewHolder, item: Message) {
        LogUtil.d("chatMessage", "content:${item.extra} | " +
                "targetUser:${item.targetUser.name} | fromUser:${item.fromUser.name} | " +
                "isSelf:${item.isSelf} | type:${item.type}")
        //根据消息类型的不同决定隐藏显示哪边布局
        if (!item.isSelf) {
            //接收消息
            helper.getView<View>(R.id.item_message_chat_received_layout).visibility = View.VISIBLE
            helper.getView<View>(R.id.item_message_chat_send_layout).visibility = View.GONE
            GlideApp.with(mContext)
                    .load(Integer.parseInt(item.targetUser.avatar))
                    .into(helper.getView<View>(R.id.item_message_chat_received_avatar) as ImageView)
            when (item.type) {
                //文本消息
                Message.MSG_TYPE_TEXT -> {
                    showText(helper, item.isSelf)
                    helper.setText(R.id.item_message_chat_received_msg, item.extra.toString())
                            .addOnClickListener(R.id.item_message_chat_received_avatar)
                            .addOnLongClickListener(R.id.item_message_chat_received_layout)
                }
                //图片消息
                Message.MSG_TYPE_IMAGE -> {
                    showImage(helper, item.isSelf)
                    GlideApp.with(mContext)
                            .load(item.dataPath)
                            .override(400, 400)
                            .fitCenter()
                            .into(helper.getView<View>(R.id.item_message_chat_received_picture) as ImageView)
                }
                //自定义表情消息
                Message.MSG_TYPE_CUSTOM_FACE -> {

                }
                //语音消息
                Message.MSG_TYPE_AUDIO -> {

                }
                //文件消息
                Message.MSG_TYPE_FILE -> {

                }
                //视频消息
                Message.MSG_TYPE_VIDEO -> {

                }
                else -> {

                }
            }
        } else {
            //发送消息
            helper.getView<View>(R.id.item_message_chat_send_layout).visibility = View.VISIBLE
            helper.getView<View>(R.id.item_message_chat_received_layout).visibility = View.GONE
            GlideApp.with(mContext)
                    .load(Integer.parseInt(item.fromUser.avatar))
                    .into(helper.getView<View>(R.id.item_message_chat_send_avatar) as ImageView)
            when (item.type) {
                //文本消息
                Message.MSG_TYPE_TEXT -> {
                    showText(helper, item.isSelf)
                    val text: String = if (item.extra is TIMTextElem) {
                        (item.extra as TIMTextElem).text
                    } else {
                        item.extra as String
                    }
                    helper.setText(R.id.item_message_chat_send_msg, text)
                            .addOnClickListener(R.id.item_message_chat_send_avatar)
                            .addOnLongClickListener(R.id.item_message_chat_send_layout)
                }
                //图片消息
                Message.MSG_TYPE_IMAGE -> {
                    showImage(helper, item.isSelf)
                    GlideApp.with(mContext)
                            .load(item.dataUri)
                            .override(400, 400)
                            .fitCenter()
                            .into(helper.getView<View>(R.id.item_message_chat_send_picture) as ImageView)
                }
                //自定义表情消息
                Message.MSG_TYPE_CUSTOM_FACE -> {

                }
                //语音消息
                Message.MSG_TYPE_AUDIO -> {

                }
                //文件消息
                Message.MSG_TYPE_FILE -> {

                }
                //视频消息
                Message.MSG_TYPE_VIDEO -> {

                }
                else -> {

                }
            }
        }
    }

    fun addMessage(message: Message) {
        messageList?.add(message)
        notifyDataSetChanged()
    }

    fun updateMessage(messages: List<Message>) {
        messageList?.addAll(0, messages)
        LogUtil.d("chatMessage", "isUIThread :${Looper.getMainLooper().thread}")
        notifyDataSetChanged()
    }

    /**
     * 显示文字消息
     */
    private fun showText(helper: BaseViewHolder, isSelf: Boolean) {
        if (isSelf) {
            helper.getView<View>(R.id.item_message_chat_send_text_layout).visibility = View.VISIBLE
            helper.getView<View>(R.id.item_message_chat_send_picture).visibility = View.GONE
        } else {
            helper.getView<View>(R.id.item_message_chat_received_text_layout).visibility = View.VISIBLE
            helper.getView<View>(R.id.item_message_chat_received_picture).visibility = View.GONE
        }
    }

    /**
     * 显示图片消息
     */
    private fun showImage(helper: BaseViewHolder, isSelf: Boolean) {
        if (isSelf) {
            helper.getView<View>(R.id.item_message_chat_send_text_layout).visibility = View.GONE
            helper.getView<View>(R.id.item_message_chat_send_picture).visibility = View.VISIBLE
        } else {
            helper.getView<View>(R.id.item_message_chat_received_text_layout).visibility = View.GONE
            helper.getView<View>(R.id.item_message_chat_received_picture).visibility = View.VISIBLE
        }
    }

}
