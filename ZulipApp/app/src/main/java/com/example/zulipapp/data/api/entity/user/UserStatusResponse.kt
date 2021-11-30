package com.example.zulipapp.data.api.entity.user

import com.google.gson.annotations.SerializedName

class PresenceResponse(
    @SerializedName("aggregated")
    val aggregated: UserStatusResponse
)

class UserStatusResponse(
    @SerializedName("status")
    val status: String,
    @SerializedName("timestamp")
    val timeStamp: Int
)