package com.dididi.pocket.core.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by dididi
 * on 29/07/2018 .
 */

public class News implements Parcelable {

    private String avatar;
    private String userName;
    private String content;
    private int imageGroupId;
    private int imageGroup1Id;
    private int imageGroup1Img1;
    private int imageGroup1Img2;
    private int imageGroup1Img3;
    private int imageGroup2Id;
    private int imageGroup2Img1;
    private int imageGroup2Img2;
    private int imageGroup2Img3;
    private int imageGroup3Id;
    private int imageGroup3Img1;
    private int imageGroup3Img2;
    private int imageGroup3Img3;
    private String date;
    private int comment;

    /** 构造函数必须传入这些值 */
    public News(String avatar, String userName,
                String content, String date) {
        this.avatar = avatar;
        this.userName = userName;
        this.content = content;
        this.date = date;
    }

    public News setAvatar(String avatar) {
        this.avatar = avatar;
        return this;
    }

    public News setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public News setContent(String content) {
        this.content = content;
        return this;
    }

    public News setImageGroupId(int imageGroupId) {
        this.imageGroupId = imageGroupId;
        return this;
    }

    public News setImageGroup1Id(int imageGroup1Id) {
        this.imageGroup1Id = imageGroup1Id;
        return this;
    }

    public News setImageGroup1Img1(int imageGroup1Img1) {
        this.imageGroup1Img1 = imageGroup1Img1;
        return this;
    }

    public News setImageGroup1Img2(int imageGroup1Img2) {
        this.imageGroup1Img2 = imageGroup1Img2;
        return this;
    }

    public News setImageGroup1Img3(int imageGroup1Img3) {
        this.imageGroup1Img3 = imageGroup1Img3;
        return this;
    }

    public News setImageGroup2Id(int imageGroup2Id) {
        this.imageGroup2Id = imageGroup2Id;
        return this;
    }

    public News setImageGroup2Img1(int imageGroup2Img1) {
        this.imageGroup2Img1 = imageGroup2Img1;
        return this;
    }

    public News setImageGroup2Img2(int imageGroup2Img2) {
        this.imageGroup2Img2 = imageGroup2Img2;
        return this;
    }

    public News setImageGroup2Img3(int imageGroup2Img3) {
        this.imageGroup2Img3 = imageGroup2Img3;
        return this;
    }

    public News setImageGroup3Id(int imageGroup3Id) {
        this.imageGroup3Id = imageGroup3Id;
        return this;
    }

    public News setImageGroup3Img1(int imageGroup3Img1) {
        this.imageGroup3Img1 = imageGroup3Img1;
        return this;
    }

    public News setImageGroup3Img2(int imageGroup3Img2) {
        this.imageGroup3Img2 = imageGroup3Img2;
        return this;
    }

    public News setImageGroup3Img3(int imageGroup3Img3) {
        this.imageGroup3Img3 = imageGroup3Img3;
        return this;
    }

    public News setDate(String date) {
        this.date = date;
        return this;
    }

    public News setComment(int comment) {
        this.comment = comment;
        return this;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getUserName() {
        return userName;
    }

    public String getContent() {
        return content;
    }

    public int getImageGroupId() {
        return imageGroupId;
    }

    public int getImageGroup1Id() {
        return imageGroup1Id;
    }

    public int getImageGroup1Img1() {
        return imageGroup1Img1;
    }

    public int getImageGroup1Img2() {
        return imageGroup1Img2;
    }

    public int getImageGroup1Img3() {
        return imageGroup1Img3;
    }

    public int getImageGroup2Id() {
        return imageGroup2Id;
    }

    public int getImageGroup2Img1() {
        return imageGroup2Img1;
    }

    public int getImageGroup2Img2() {
        return imageGroup2Img2;
    }

    public int getImageGroup2Img3() {
        return imageGroup2Img3;
    }

    public int getImageGroup3Id() {
        return imageGroup3Id;
    }

    public int getImageGroup3Img1() {
        return imageGroup3Img1;
    }

    public int getImageGroup3Img2() {
        return imageGroup3Img2;
    }

    public int getImageGroup3Img3() {
        return imageGroup3Img3;
    }

    public String getDate() {
        return date;
    }

    public int getComment() {
        return comment;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.avatar);
        dest.writeString(this.userName);
        dest.writeString(this.content);
        dest.writeInt(this.imageGroupId);
        dest.writeInt(this.imageGroup1Id);
        dest.writeInt(this.imageGroup1Img1);
        dest.writeInt(this.imageGroup1Img2);
        dest.writeInt(this.imageGroup1Img3);
        dest.writeInt(this.imageGroup2Id);
        dest.writeInt(this.imageGroup2Img1);
        dest.writeInt(this.imageGroup2Img2);
        dest.writeInt(this.imageGroup2Img3);
        dest.writeInt(this.imageGroup3Id);
        dest.writeInt(this.imageGroup3Img1);
        dest.writeInt(this.imageGroup3Img2);
        dest.writeInt(this.imageGroup3Img3);
        dest.writeString(this.date);
        dest.writeInt(this.comment);
    }

    protected News(Parcel in) {
        this.avatar = in.readString();
        this.userName = in.readString();
        this.content = in.readString();
        this.imageGroupId = in.readInt();
        this.imageGroup1Id = in.readInt();
        this.imageGroup1Img1 = in.readInt();
        this.imageGroup1Img2 = in.readInt();
        this.imageGroup1Img3 = in.readInt();
        this.imageGroup2Id = in.readInt();
        this.imageGroup2Img1 = in.readInt();
        this.imageGroup2Img2 = in.readInt();
        this.imageGroup2Img3 = in.readInt();
        this.imageGroup3Id = in.readInt();
        this.imageGroup3Img1 = in.readInt();
        this.imageGroup3Img2 = in.readInt();
        this.imageGroup3Img3 = in.readInt();
        this.date = in.readString();
        this.comment = in.readInt();
    }

    public static final Parcelable.Creator<News> CREATOR = new Parcelable.Creator<News>() {
        @Override
        public News createFromParcel(Parcel source) {
            return new News(source);
        }

        @Override
        public News[] newArray(int size) {
            return new News[size];
        }
    };
}
