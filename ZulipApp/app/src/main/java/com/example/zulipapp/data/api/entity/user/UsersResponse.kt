package com.example.zulipapp.data.api.entity.user

import com.google.gson.annotations.SerializedName

class UsersResponse(
    @SerializedName("result")
    val result: String,
    @SerializedName("user")
    val userById: UserResponse,
    @SerializedName("members")
    val allUsers: List<UserResponse> = emptyList(),
    @SerializedName("presence")
    val presence: PresenceResponse
)