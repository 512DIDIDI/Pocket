package com.dididi.pocket.ec.main.mall.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by dididi
 * on 29/07/2018 .
 */

public class News {

    private int headId;
    private String userName;
    private String content;
    private int imageGroupId;
    private int imageGroup1Id;
    private int imageGroup1_1;
    private int imageGroup1_2;
    private int imageGroup1_3;
    private int imageGroup2Id;
    private int imageGroup2_1;
    private int imageGroup2_2;
    private int imageGroup2_3;
    private int imageGroup3Id;
    private int imageGroup3_1;
    private int imageGroup3_2;
    private int imageGroup3_3;
    private String date;
    private int comment;

    //构造函数必须传入这些值
    public News(int headId, String userName,
                String content, String date) {
        this.headId = headId;
        this.userName = userName;
        this.content = content;
        this.date = date;
    }

    public News setHeadId(int headId) {
        this.headId = headId;
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

    public News setImageGroup1_1(int imageGroup1_1) {
        this.imageGroup1_1 = imageGroup1_1;
        return this;
    }

    public News setImageGroup1_2(int imageGroup1_2) {
        this.imageGroup1_2 = imageGroup1_2;
        return this;
    }

    public News setImageGroup1_3(int imageGroup1_3) {
        this.imageGroup1_3 = imageGroup1_3;
        return this;
    }

    public News setImageGroup2Id(int imageGroup2Id) {
        this.imageGroup2Id = imageGroup2Id;
        return this;
    }

    public News setImageGroup2_1(int imageGroup2_1) {
        this.imageGroup2_1 = imageGroup2_1;
        return this;
    }

    public News setImageGroup2_2(int imageGroup2_2) {
        this.imageGroup2_2 = imageGroup2_2;
        return this;
    }

    public News setImageGroup2_3(int imageGroup2_3) {
        this.imageGroup2_3 = imageGroup2_3;
        return this;
    }

    public News setImageGroup3Id(int imageGroup3Id) {
        this.imageGroup3Id = imageGroup3Id;
        return this;
    }

    public News setImageGroup3_1(int imageGroup3_1) {
        this.imageGroup3_1 = imageGroup3_1;
        return this;
    }

    public News setImageGroup3_2(int imageGroup3_2) {
        this.imageGroup3_2 = imageGroup3_2;
        return this;
    }

    public News setImageGroup3_3(int imageGroup3_3) {
        this.imageGroup3_3 = imageGroup3_3;
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

    public int getHeadId() {
        return headId;
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

    public int getImageGroup1_1() {
        return imageGroup1_1;
    }

    public int getImageGroup1_2() {
        return imageGroup1_2;
    }

    public int getImageGroup1_3() {
        return imageGroup1_3;
    }

    public int getImageGroup2Id() {
        return imageGroup2Id;
    }

    public int getImageGroup2_1() {
        return imageGroup2_1;
    }

    public int getImageGroup2_2() {
        return imageGroup2_2;
    }

    public int getImageGroup2_3() {
        return imageGroup2_3;
    }

    public int getImageGroup3Id() {
        return imageGroup3Id;
    }

    public int getImageGroup3_1() {
        return imageGroup3_1;
    }

    public int getImageGroup3_2() {
        return imageGroup3_2;
    }

    public int getImageGroup3_3() {
        return imageGroup3_3;
    }

    public String getDate() {
        return date;
    }

    public int getComment() {
        return comment;
    }
}
