package com.example.zulipapp.domain.repository

import com.example.zulipapp.domain.entity.Stream
import com.example.zulipapp.domain.entity.Topic
import io.reactivex.Single

interface StreamRepository {

    fun getAllStreams(): Single<List<Stream>>

    fun getSubscribedStreams(): Single<List<Stream>>

    fun getTopicsByStreamId(streamId: Int): Single<List<Topic>>
}