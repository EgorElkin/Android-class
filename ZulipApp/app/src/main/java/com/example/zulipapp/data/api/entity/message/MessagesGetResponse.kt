package com.example.zulipapp.data.api.entity.message

import com.google.gson.annotations.SerializedName

class MessagesGetResponse(
    @SerializedName("result")
    val result: String,
    @SerializedName("found_anchor")
    val foundAnchor: Boolean,
    @SerializedName("found_oldest")
    val foundOldest: Boolean,
    @SerializedName("found_newest")
    val foundNewest: Boolean,
    @SerializedName("history_limited")
    val historyLimited: Boolean,
    @SerializedName("messages")
    val messages: List<MessageResponse>
)