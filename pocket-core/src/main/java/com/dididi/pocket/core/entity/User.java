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
    private int phone;
    /** 用户地址 */
    private String address;
    /** 用户性别 */
    private String gender;

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
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
        dest.writeInt(this.phone);
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
        this.phone = in.readInt();
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
