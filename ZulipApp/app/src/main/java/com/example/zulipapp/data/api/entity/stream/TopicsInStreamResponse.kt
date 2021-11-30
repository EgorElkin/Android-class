package com.example.zulipapp.data.api.entity.stream

import com.google.gson.annotations.SerializedName

class TopicsInStreamResponse(
    @SerializedName("result")
    val result: String,
    @SerializedName("topics")
    val topics: List<TopicResponse>
)