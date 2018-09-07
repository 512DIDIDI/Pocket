package com.dididi.pocket_core.Entity;

import java.io.Serializable;

/**
 * Created by dididi
 * on 25/08/2018 .
 */

public class Message implements Serializable {
    //收到的消息
    public static final int TYPE_RECEIVED = 0;
    //发出的消息
    public static final int TYPE_SENT = 1;
    //消息内容
    private String content;
    //消息类型
    private int type;
    //发送此消息的用户id
    private int sendUserId;
    //接收此消息的用户id
    private int receivedUserId;
    //发送此消息的用户名
    private String sendUserName;
    //发送此消息的用户名
    private String receivedUserName;
    //发送此消息的用户头像
    private String sendUserAvatar;
    //接收此消息的用户头像
    private String receivedUserAvatar;
    //消息日期
    private String date;

    public Message(String content, int type,
                   int sendUserId, int receivedUserId,
                   String sendUserName, String receivedUserName,
                   String sendUserAvatar, String receivedUserAvatar,
                   String date) {
        this.content = content;
        this.type = type;
        this.sendUserId = sendUserId;
        this.receivedUserId = receivedUserId;
        this.sendUserName = sendUserName;
        this.receivedUserName = receivedUserName;
        this.sendUserAvatar = sendUserAvatar;
        this.receivedUserAvatar = receivedUserAvatar;
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public int getType() {
        return type;
    }

    public int getSendUserId() {
        return sendUserId;
    }

    public int getReceivedUserId() {
        return receivedUserId;
    }

    public String getSendUserAvatar() {
        return sendUserAvatar;
    }

    public String getDate() {
        return date;
    }

    public String getSendUserName() {
        return sendUserName;
    }

    public String getReceivedUserName() {
        return receivedUserName;
    }

    public String getReceivedUserAvatar() {
        return receivedUserAvatar;
    }
}
