package com.example.zulipapp.data.api.entity.message

import com.google.gson.annotations.SerializedName

class MessageResponse(
    @SerializedName("id")
    val id: Int,
    @SerializedName("sender_id")
    val senderId: Int,
    @SerializedName("content")
    val content: String,
    @SerializedName("timestamp")
    val timeStamp: Int,
    @SerializedName("subject")
    val topic: String,
    @SerializedName("sender_full_name")
    val senderFullName: String,
    @SerializedName("stream_id")
    val streamId: Int = 0,
    @SerializedName("sender_email")
    val senderEmail: String,
    @SerializedName("avatar_url")
    val senderAvatar: String,
    @SerializedName("type")
    val type: String, // stream/private
    @SerializedName("content_type")
    val contentType: String, // text/html
    @SerializedName("reactions")
    val reactions: List<ReactionResponse>
)