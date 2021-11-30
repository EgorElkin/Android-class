package com.example.zulipapp.data.api.entity.message

import com.google.gson.annotations.SerializedName

class ReactionOperationResponse(
    @SerializedName("result")
    val result: String,
    @SerializedName("msg")
    val message: String,
    @SerializedName("code")
    val code: String = ""
)