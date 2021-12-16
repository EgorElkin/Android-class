package com.example.zulipapp.data.database

import com.example.zulipapp.data.database.dao.MessageDao
import com.example.zulipapp.domain.entity.Message
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

class LocalMessageDataSource(private val messageDao: MessageDao) {

//    fun getFromStream(streamId: Int): Single<List<Message>> = messageDao.getFromStream()

}