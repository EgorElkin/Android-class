package com.example.zulipapp.data.api.entity.stream

import com.google.gson.annotations.SerializedName

class StreamResponse(
    @SerializedName("name")
    val name: String,
    @SerializedName("stream_id")
    val streamId: Int,
    @SerializedName("description")
    val description: String,
    @SerializedName("first_message_id")
    val firstMessageId: Int,
    @SerializedName("color")
    private val _color: String?
){
    val color: String
        get()= _color ?: "#2A9D8F"
}