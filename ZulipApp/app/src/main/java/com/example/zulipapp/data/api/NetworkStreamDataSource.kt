package com.example.zulipapp.data.api

import com.example.zulipapp.data.api.mapper.*
import com.example.zulipapp.domain.entity.Stream
import com.example.zulipapp.domain.entity.Topic
import com.example.zulipapp.domain.entity.User
import com.example.zulipapp.domain.entity.UserStatus
import io.reactivex.Single

class NetworkStreamDataSource(private val streamApiService: StreamApiService) {

    fun getAllStreams(): Single<List<Stream>> {
        return streamApiService.getAllStreams().map(AllStreamsResponseMapper())
    }

    fun getSubscribedStreams(): Single<List<Stream>> {
        return streamApiService.getSubscribedStreams().map(SubscribedStreamsResponseMapper())
    }

    fun getTopicsByStreamId(streamId: Int): Single<List<Topic>> {
        return streamApiService.getTopics(streamId.toString()).map(TopicsResponseMapper())
    }
}