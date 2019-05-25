package com.dididi.pocket.core.entity;

import android.net.Uri;

import com.tencent.imsdk.TIMMessage;

import java.io.Serializable;
import java.util.UUID;

/**
 * Created by dididi
 * on 25/08/2018 .
 */

public class Message implements Serializable {
    /**
     * 文本类型消息
     */
    public static final int MSG_TYPE_TEXT = 0x00;
    /**
     * 图片类型消息
     */
    public static final int MSG_TYPE_IMAGE = 0x20;
    /**
     * 语音类型消息
     */
    public static final int MSG_TYPE_AUDIO = 0x30;
    /**
     * 视频类型消息
     */
    public static final int MSG_TYPE_VIDEO = 0x40;
    /**
     * 文件类型消息
     */
    public static final int MSG_TYPE_FILE = 0x50;
    /**
     * 位置类型消息
     */
    public static final int MSG_TYPE_LOCATION = 0x60;

    /**
     * 自定义图片类型消息
     */
    public static final int MSG_TYPE_CUSTOM_FACE = 0x70;

    /**
     * 提示类信息
     */
    public static final int MSG_TYPE_TIPS = 0x100;
    /**
     * 群创建提示消息
     */
    public static final int MSG_TYPE_GROUP_CREATE = 0x101;
    /**
     * 群创建提示消息
     */
    public static final int MSG_TYPE_GROUP_DELETE = 0x102;
    /**
     * 群成员加入提示消息
     */
    public static final int MSG_TYPE_GROUP_JOIN = 0x103;
    /**
     * 群成员退群提示消息
     */
    public static final int MSG_TYPE_GROUP_QUITE = 0x104;
    /**
     * 群成员被踢出群提示消息
     */
    public static final int MSG_TYPE_GROUP_KICK = 0x105;
    /**
     * 群名称修改提示消息
     */
    public static final int MSG_TYPE_GROUP_MODIFY_NAME = 0x106;
    /**
     * 群通知更新提示消息
     */
    public static final int MSG_TYPE_GROUP_MODIFY_NOTICE = 0x107;

    /**
     * 消息未读状态
     */
    public static final int MSG_STATUS_READ = 0x111;
    /**
     * 消息删除状态
     */
    public static final int MSG_STATUS_DELETE = 0x112;
    /**
     * 消息撤回状态
     */
    public static final int MSG_STATUS_REVOKE = 0x113;
    /**
     * 消息正常状态
     */
    public static final int MSG_STATUS_NORMAL = 0;
    /**
     * 消息发送中状态
     */
    public static final int MSG_STATUS_SENDING = 1;
    /**
     * 消息发送成功状态
     */
    public static final int MSG_STATUS_SEND_SUCCESS = 2;
    /**
     * 消息发送失败状态
     */
    public static final int MSG_STATUS_SEND_FAIL = 3;
    /**
     * 消息内容下载中状态
     */
    public static final int MSG_STATUS_DOWNLOADING = 4;
    /**
     * 消息内容未下载状态
     */
    public static final int MSG_STATUS_UN_DOWNLOAD = 5;
    /**
     * 消息内容已下载状态
     */
    public static final int MSG_STATUS_DOWNLOADED = 6;
    /**
     * 接收此消息的用户
     */
    private User targetUser;
    /**
     * 发送消息的用户
     */
    private User fromUser;
    /**
     * 消息id
     */
    private String msgId = UUID.randomUUID().toString();
    /**
     * 消息类型
     */
    private int type;
    /**
     * 消息状态
     */
    private int status = MSG_STATUS_NORMAL;
    /**
     * 自己发送的消息标志位
     */
    private boolean self;
    /**
     * 已读标志位
     */
    private boolean read;
    /**
     * 群聊标志位
     */
    private boolean group;
    /**
     * 文件、相片等Uri
     */
    private Uri dataUri;
    /**
     * 路径
     */
    private String dataPath;
    /**
     * 消息内容
     */
    private Object extra;
    /**
     * 图片宽
     */
    private int imgWidth;
    /**
     * 图片高
     */
    private int imgHeight;
    /**
     * 消息日期
     */
    private long date;
    /**
     * 腾讯云通信message
     */
    private TIMMessage timMessage;

    public User getTargetUser() {
        return targetUser;
    }

    public Message setTargetUser(User targetUser) {
        this.targetUser = targetUser;
        return this;
    }

    public User getFromUser() {
        return fromUser;
    }

    public Message setFromUser(User fromUser) {
        this.fromUser = fromUser;
        return this;
    }

    public String getMsgId() {
        return msgId;
    }

    public Message setMsgId(String msgId) {
        this.msgId = msgId;
        return this;
    }

    public int getType() {
        return type;
    }

    public Message setType(int type) {
        this.type = type;
        return this;
    }

    public int getStatus() {
        return status;
    }

    public Message setStatus(int status) {
        this.status = status;
        return this;
    }

    public boolean isSelf() {
        return self;
    }

    public Message setSelf(boolean self) {
        this.self = self;
        return this;
    }

    public boolean isRead() {
        return read;
    }

    public Message setRead(boolean read) {
        this.read = read;
        return this;
    }

    public boolean isGroup() {
        return group;
    }

    public Message setGroup(boolean group) {
        this.group = group;
        return this;
    }

    public Uri getDataUri() {
        return dataUri;
    }

    public Message setDataUri(Uri dataUri) {
        this.dataUri = dataUri;
        return this;
    }

    public String getDataPath() {
        return dataPath;
    }

    public Message setDataPath(String dataPath) {
        this.dataPath = dataPath;
        return this;
    }

    public Object getExtra() {
        return extra;
    }

    public Message setExtra(Object extra) {
        this.extra = extra;
        return this;
    }

    public int getImgWidth() {
        return imgWidth;
    }

    public Message setImgWidth(int imgWidth) {
        this.imgWidth = imgWidth;
        return this;
    }

    public int getImgHeight() {
        return imgHeight;
    }

    public Message setImgHeight(int imgHeight) {
        this.imgHeight = imgHeight;
        return this;
    }

    public long getDate() {
        return date;
    }

    public Message setDate(long date) {
        this.date = date;
        return this;
    }

    public TIMMessage getTimMessage() {
        return timMessage;
    }

    public Message setTimMessage(TIMMessage timMessage) {
        this.timMessage = timMessage;
        return this;
    }

    /**
     * 判断是否相同
     */
    public boolean isSame(Message other) {
        if (this.timMessage != null && other.timMessage != null) {
            if (this.timMessage.getMsgId().equals(other.timMessage.getMsgId())) {
                return true;
            } else {
                return timMessage.timestamp() == other.timMessage.timestamp();
            }
        }
        return false;
    }

}
