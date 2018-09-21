package com.dididi.pocket.core.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by dididi
 * on 25/08/2018 .
 */

public class Message implements Parcelable {
    /** 收到的消息 */
    public static final int TYPE_RECEIVED = 0;
    /** 发出的消息 */
    public static final int TYPE_SENT = 1;
    /** 消息内容 */
    private String content;
    /** 消息类型 */
    private int type;
    /** 发送此消息的用户id(即本用户) */
    private int sendUserId;
    /** 接收此消息的用户id(即对方用户) */
    private int receivedUserId;
    /** 发送此消息的用户名(即本用户) */
    private String sendUserName;
    /** 接受此消息的用户名(即对方用户) */
    private String receivedUserName;
    /** 发送此消息的用户头像(即本用户) */
    private String sendUserAvatar;
    /** 接收此消息的用户头像(即对方用户) */
    private String receivedUserAvatar;
    /** 消息日期 */
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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.content);
        dest.writeInt(this.type);
        dest.writeInt(this.sendUserId);
        dest.writeInt(this.receivedUserId);
        dest.writeString(this.sendUserName);
        dest.writeString(this.receivedUserName);
        dest.writeString(this.sendUserAvatar);
        dest.writeString(this.receivedUserAvatar);
        dest.writeString(this.date);
    }

    protected Message(Parcel in) {
        this.content = in.readString();
        this.type = in.readInt();
        this.sendUserId = in.readInt();
        this.receivedUserId = in.readInt();
        this.sendUserName = in.readString();
        this.receivedUserName = in.readString();
        this.sendUserAvatar = in.readString();
        this.receivedUserAvatar = in.readString();
        this.date = in.readString();
    }

    public static final Parcelable.Creator<Message> CREATOR = new Parcelable.Creator<Message>() {
        @Override
        public Message createFromParcel(Parcel source) {
            return new Message(source);
        }

        @Override
        public Message[] newArray(int size) {
            return new Message[size];
        }
    };
}
