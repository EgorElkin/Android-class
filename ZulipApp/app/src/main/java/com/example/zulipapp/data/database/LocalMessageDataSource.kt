package com.example.zulipapp.data.database

import com.example.zulipapp.data.database.dao.MessageDao
import com.example.zulipapp.data.database.mapper.EntitiesToMessagesMapper
import com.example.zulipapp.domain.entity.Message
import io.reactivex.Single

class LocalMessageDataSource(private val messageDao: MessageDao) {

    fun getFromStream(streamId: Int): Single<List<Message>> = messageDao.getFromStream(streamId).map(EntitiesToMessagesMapper())
}