package com.example.zulipapp.data.repository

import com.example.zulipapp.data.api.NetworkStreamDataSource
import com.example.zulipapp.domain.entity.Stream
import com.example.zulipapp.domain.entity.Topic
import com.example.zulipapp.domain.repository.StreamRepository
import io.reactivex.Single

class StreamRepositoryImpl(
    private val networkDataSource: NetworkStreamDataSource,
) : StreamRepository {

    override fun getAllStreams(): Single<List<Stream>> {
        return networkDataSource.getAllStreams()
    }

    override fun getSubscribedStreams(): Single<List<Stream>> {
        return networkDataSource.getSubscribedStreams()
    }

    override fun getTopicsByStreamId(streamId: Int): Single<List<Topic>> {
        return networkDataSource.getTopicsByStreamId(streamId)
    }
}