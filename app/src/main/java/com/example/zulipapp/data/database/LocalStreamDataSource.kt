package com.example.zulipapp.data.database

import android.content.Context
import com.example.zulipapp.data.database.entity.StreamEntity
import com.example.zulipapp.data.database.mapper.EntityToStreamMapper
import com.example.zulipapp.domain.entity.Stream
import io.reactivex.Completable
import io.reactivex.Single

class LocalStreamDataSource(applicationContext: Context) {

    private val appDatabase = RoomBuilder.provideAppDatabase(applicationContext)

    fun addAllStreams(streams: List<StreamEntity>): Completable = appDatabase.StreamDao().insertAll(streams)

    fun getAllStreams(): Single<List<Stream>> = appDatabase.StreamDao().getAllStreams().map(EntityToStreamMapper())

    fun getSubscribedStreams(): Single<List<Stream>> = appDatabase.StreamDao().getSubscribedStreams().map(EntityToStreamMapper())
}