package com.example.zulipapp.data.api.mapper

import androidx.core.graphics.toColorInt
import com.example.zulipapp.data.api.entity.stream.StreamsResponse
import com.example.zulipapp.data.api.entity.stream.TopicsInStreamResponse
import com.example.zulipapp.domain.entity.Stream
import com.example.zulipapp.domain.entity.Topic

class SubscribedStreamsResponseMapper : (StreamsResponse) -> List<Stream>{
    override fun invoke(response: StreamsResponse): List<Stream> {
        return response.subscribedStreams.map {
            Stream(
                it.streamId,
                it.name,
                it.description,
                it.firstMessageId,
                it.color.toColorInt()
            )
        }
    }
}

class AllStreamsResponseMapper : (StreamsResponse) -> List<Stream>{
    override fun invoke(response: StreamsResponse): List<Stream> {
        return response.allStreams.map {
            Stream(
                it.streamId,
                it.name,
                it.description,
                it.firstMessageId,
                it.color.toColorInt()
            )
        }
    }
}

class TopicsResponseMapper : (TopicsInStreamResponse) -> List<Topic>{
    override fun invoke(response: TopicsInStreamResponse): List<Topic> {
        return response.topics.map {
            Topic(
                it.name,
                it.lastMessageId
            )
        }
    }
}