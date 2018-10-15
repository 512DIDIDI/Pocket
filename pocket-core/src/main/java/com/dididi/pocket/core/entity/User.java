package com.dididi.pocket.core.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by dididi
 * on 24/07/2018 .
 */
public class User implements Parcelable {

    /** 用户id */
    private long id;
    /** 用户邮箱 */
    private String email;
    /** 用户姓名 */
    private String name;
    /** 用户密码 */
    private String password;
    /** 用户头像 */
    private String avatar;
    /** 用户手机 */
    private String phone;
    /** 用户地址 */
    private String address;
    /** 用户性别 */
    private String gender;

    public String getGender() {
        return gender;
    }

    public User setGender(String gender) {
        this.gender = gender;
        return this;
    }

    public long getId() {
        return id;
    }

    public User setId(long id) {
        this.id = id;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public User setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getName() {
        return name;
    }

    public User setName(String name) {
        this.name = name;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public User setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public User setAddress(String address) {
        this.address = address;
        return this;
    }

    public String getAvatar() {
        return avatar;
    }

    public User setAvatar(String avatar) {
        this.avatar = avatar;
        return this;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.email);
        dest.writeString(this.name);
        dest.writeString(this.password);
        dest.writeString(this.avatar);
        dest.writeString(this.phone);
        dest.writeString(this.address);
        dest.writeString(this.gender);
    }

    public User() {
    }

    protected User(Parcel in) {
        this.id = in.readLong();
        this.email = in.readString();
        this.name = in.readString();
        this.password = in.readString();
        this.avatar = in.readString();
        this.phone = in.readString();
        this.address = in.readString();
        this.gender = in.readString();
    }

    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        @Override
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
