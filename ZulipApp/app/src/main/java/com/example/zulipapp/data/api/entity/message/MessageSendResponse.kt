package com.example.zulipapp.data.api.entity.message

import com.google.gson.annotations.SerializedName

class MessageSendResponse(
    @SerializedName("result")
    val result: String,
    @SerializedName("msg")
    val message: String,
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("code")
    val code: String = ""
)