package com.example.zulipapp.data.api.entity.user

import com.google.gson.annotations.SerializedName

class UserResponse(
    @SerializedName("email")
    val email: String,
    @SerializedName("user_id")
    val id: Int,
    @SerializedName("full_name")
    val name: String,
    @SerializedName("avatar_url")
    val avatarUrl: String
)