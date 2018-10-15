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
    private User sendUser;
    /** 接收此消息的用户id(即对方用户) */
    private User receivedUser;
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

    public Message(String content, int type, User sendUser, User receivedUser, String date) {
        this.content = content;
        this.type = type;
        this.sendUser = sendUser;
        this.receivedUser = receivedUser;
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public int getType() {
        return type;
    }

    public User getSendUser() {
        return sendUser;
    }

    public User getReceivedUser() {
        return receivedUser;
    }

    public String getSendUserName() {
        return sendUser.getName();
    }

    public String getReceivedUserName() {
        return receivedUser.getName();
    }

    public String getSendUserAvatar() {
        return sendUser.getAvatar();
    }

    public String getReceivedUserAvatar() {
        return receivedUser.getAvatar();
    }

    public String getDate() {
        return date;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.content);
        dest.writeInt(this.type);
        dest.writeParcelable(this.sendUser, flags);
        dest.writeParcelable(this.receivedUser, flags);
        dest.writeString(this.sendUserName);
        dest.writeString(this.receivedUserName);
        dest.writeString(this.sendUserAvatar);
        dest.writeString(this.receivedUserAvatar);
        dest.writeString(this.date);
    }

    public Message() {
    }

    protected Message(Parcel in) {
        this.content = in.readString();
        this.type = in.readInt();
        this.sendUser = in.readParcelable(User.class.getClassLoader());
        this.receivedUser = in.readParcelable(User.class.getClassLoader());
        this.sendUserName = in.readString();
        this.receivedUserName = in.readString();
        this.sendUserAvatar = in.readString();
        this.receivedUserAvatar = in.readString();
        this.date = in.readString();
    }

    public static final Creator<Message> CREATOR = new Creator<Message>() {
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
