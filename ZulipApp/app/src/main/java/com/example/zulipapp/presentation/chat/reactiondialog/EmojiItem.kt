package com.example.zulipapp.presentation.chat.reactiondialog

import android.os.Parcel
import android.os.Parcelable

class EmojiItem(
    val emojiName: String,
    val emojiCode: String,
    val emojiUniCode: String
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: ""
    )

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(out: Parcel, flags: Int) {
        out.writeString(emojiName)
        out.writeString(emojiCode)
        out.writeString(emojiUniCode)
    }

    companion object CREATOR : Parcelable.Creator<EmojiItem> {
        override fun createFromParcel(parcel: Parcel): EmojiItem {
            return EmojiItem(parcel)
        }

        override fun newArray(size: Int): Array<EmojiItem?> {
            return arrayOfNulls(size)
        }
    }

}