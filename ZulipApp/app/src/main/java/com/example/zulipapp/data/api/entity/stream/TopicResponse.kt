package com.example.zulipapp.data.api.entity.stream

import com.google.gson.annotations.SerializedName

class TopicResponse(
    @SerializedName("name")
    val name: String,
    @SerializedName("max_id")
    val lastMessageId: Int
)