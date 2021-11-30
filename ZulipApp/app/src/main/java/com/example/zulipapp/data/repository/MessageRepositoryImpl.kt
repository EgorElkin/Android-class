package com.example.zulipapp.data.repository

import com.example.zulipapp.data.api.NetworkMessageDataSource
import com.example.zulipapp.data.api.entity.message.Narrow
import com.example.zulipapp.data.api.mapper.ResponseToMessageMapper
import com.example.zulipapp.data.database.LocalMessageDataSource
import com.example.zulipapp.domain.entity.Message
import com.example.zulipapp.domain.repository.MessageRepository
import io.reactivex.Single

class MessageRepositoryImpl(
    private val networkDataSource: NetworkMessageDataSource,
//    private val localDataSource: LocalMessageDataSource
) : MessageRepository {

    override fun sendMessage(type: String, streamId: Int, topicName: String, content: String): Single<Int> {
        TODO("Not yet implemented")
    }

    override fun sendMessage(type: String, streamName: String, topicName: String, content: String): Single<Int> {
        TODO("Not yet implemented")
    }

    override fun fetchMessagesRange(anchor: Int, numBefore: Int, numAfter: Int, narrows: List<Narrow>): Single<List<Message>> {
        return networkDataSource.fetchMessagesRange(anchor, numBefore, numAfter, narrows).map(ResponseToMessageMapper())
    }

    override fun fetchMessagesRange(anchor: String, numBefore: Int, numAfter: Int, narrows: List<Narrow>): Single<List<Message>> {
        println("debug: fetchMessagesRange()")
        return networkDataSource.fetchMessagesRange(anchor, numBefore, numAfter, narrows).map(ResponseToMessageMapper())
    }

    override fun addReaction(messageId: Int, emojiName: String, emojiCode: String): Single<Boolean> {
        TODO("Not yet implemented")
    }

    override fun removeReaction(messageId: Int, emojiName: String, emojiCode: String): Single<Boolean> {
        TODO("Not yet implemented")
    }
}