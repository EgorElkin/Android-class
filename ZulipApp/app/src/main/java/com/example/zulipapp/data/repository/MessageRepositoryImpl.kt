package com.example.zulipapp.data.repository

import com.example.zulipapp.data.api.NetworkMessageDataSource
import com.example.zulipapp.data.api.entity.message.Narrow
import com.example.zulipapp.data.api.mapper.ResponseToMessageMapper
import com.example.zulipapp.domain.entity.Message
import com.example.zulipapp.domain.repository.MessageRepository
import io.reactivex.Single

class MessageRepositoryImpl(
    private val networkDataSource: NetworkMessageDataSource,
) : MessageRepository {

    override fun sendMessage(type: String, streamName: String, topicName: String?, content: String): Single<Int> {
        return networkDataSource.sendMessage(type, streamName, topicName, content).map { it.id }
    }

    override fun fetchMessagesRange(anchor: Int, numBefore: Int, numAfter: Int, narrows: List<Narrow>): Single<List<Message>> {
        return networkDataSource.fetchMessagesRange(anchor, numBefore, numAfter, narrows).map(ResponseToMessageMapper())
    }

    override fun fetchMessagesRange(anchor: String, numBefore: Int, numAfter: Int, narrows: List<Narrow>): Single<List<Message>> {
        return networkDataSource.fetchMessagesRange(anchor, numBefore, numAfter, narrows).map(ResponseToMessageMapper())
    }

    override fun fetchMessage(messageId: Int, narrows: List<Narrow>): Single<Message> {
        return networkDataSource.fetchMessagesRange(messageId, 0, 0, narrows)
            .map(ResponseToMessageMapper())
            .map {
                it.first()
            }
    }

    override fun addReaction(messageId: Int, emojiName: String, emojiCode: String): Single<Boolean> {
        return networkDataSource.addReaction(messageId, emojiName, emojiCode)
            .map {
                it.result == "success"
            }
    }

    override fun removeReaction(messageId: Int, emojiName: String, emojiCode: String): Single<Boolean> {
        return networkDataSource.removeReaction(messageId, emojiName, emojiCode)
            .map {
                it.result == "success"
            }
    }
}