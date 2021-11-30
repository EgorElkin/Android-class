package com.example.zulipapp.data.api.entity.stream

import com.google.gson.annotations.SerializedName

class StreamsResponse(
    @SerializedName("result")
    val result: String,
    @SerializedName("msg")
    val message: String,
    @SerializedName("streams")
    val allStreams: List<StreamResponse> = emptyList(),
    @SerializedName("subscriptions")
    val subscribedStreams: List<StreamResponse> = emptyList()
)