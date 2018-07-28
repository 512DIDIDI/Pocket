package com.dididi.pocket.ec.main.message.entity;


/**
 * Created by dididi
 * on 26/07/2018 .
 */

public class Message {

    private int headImg;
    private String userName;
    private String msgContent;
    private String date;

    public Message(int headImg, String userName, String msgContent, String date) {
        this.headImg = headImg;
        this.userName = userName;
        this.msgContent = msgContent;
        this.date = date;
    }

    public int getHeadImg() {
        return headImg;
    }

    public String getUserName() {
        return userName;
    }

    public String getMsgContent() {
        return msgContent;
    }

    public String getDate() {
        return date;
    }

}
