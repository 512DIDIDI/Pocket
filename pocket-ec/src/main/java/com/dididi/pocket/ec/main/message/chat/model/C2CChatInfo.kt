package com.dididi.pocket.ec.main.message.chat.model

import android.os.Parcel
import android.os.Parcelable
import com.dididi.pocket.core.im.BaseChatInfo
import com.tencent.imsdk.TIMConversationType


/**
 * @author dididi(叶超)
 * @email yc512yc@163.com
 * @since 23/05/2019
 * @describe 单人聊天信息
 */

class C2CChatInfo() :BaseChatInfo() {
    constructor(parcel: Parcel) : this(){
        chatName = parcel.readString()
        peer = parcel.readString()
    }

    init {
        type = TIMConversationType.C2C
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(chatName)
        parcel.writeString(peer)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<C2CChatInfo> {
        override fun createFromParcel(parcel: Parcel): C2CChatInfo {
            return C2CChatInfo(parcel)
        }

        override fun newArray(size: Int): Array<C2CChatInfo?> {
            return arrayOfNulls(size)
        }
    }


}