package com.example.zulipapp.domain.repository

import com.example.zulipapp.data.api.entity.message.Narrow
import com.example.zulipapp.domain.entity.Message
import io.reactivex.Single

interface MessageRepository {

    fun sendMessage(type: String, streamName: String, topicName: String?, content: String): Single<Int>

    fun fetchMessagesRange(anchor: Int, numBefore: Int, numAfter: Int, narrows: List<Narrow>): Single<List<Message>>

    fun fetchMessagesRange(anchor: String, numBefore: Int, numAfter: Int, narrows: List<Narrow>): Single<List<Message>>

    fun fetchMessage(messageId: Int, narrows: List<Narrow>): Single<Message>

    fun addReaction(messageId: Int, emojiName: String, emojiCode: String): Single<Boolean>

    fun removeReaction(messageId: Int, emojiName: String, emojiCode: String): Single<Boolean>
}