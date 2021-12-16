package com.example.zulipapp.data.database

import com.example.zulipapp.data.database.dao.StreamDao
import com.example.zulipapp.data.database.entity.StreamEntity
import com.example.zulipapp.data.database.mapper.EntityToStreamMapper
import com.example.zulipapp.domain.entity.Stream
import io.reactivex.Completable
import io.reactivex.Single

class LocalStreamDataSource(private val streamDao: StreamDao) {

    fun addAllStreams(streams: List<StreamEntity>): Completable = streamDao.insertAll(streams)

    fun getAllStreams(): Single<List<Stream>> = streamDao.getAllStreams().map(EntityToStreamMapper())

    fun getSubscribedStreams(): Single<List<Stream>> = streamDao.getSubscribedStreams().map(EntityToStreamMapper())
}