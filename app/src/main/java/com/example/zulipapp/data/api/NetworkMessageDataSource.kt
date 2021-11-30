package com.example.zulipapp.data.api

import com.example.zulipapp.data.api.entity.message.MessageResponse
import com.example.zulipapp.data.api.entity.message.MessageSendResponse
import com.example.zulipapp.data.api.entity.message.Narrow
import com.example.zulipapp.data.api.entity.message.ReactionOperationResponse
import io.reactivex.Single

class NetworkMessageDataSource(private val messageApiService: MessageApiService) {

    fun sendMessage(type: String, streamId: Int, topicName: String, content: String): Single<MessageSendResponse>{
        return messageApiService.postMessage(type, streamId, topicName, content)
    }

    fun sendMessage(type: String, streamName: String, topicName: String, content: String): Single<MessageSendResponse>{
        return messageApiService.postMessage(type, streamName, topicName, content)
    }

    fun fetchMessagesRange(anchor: Int, numBefore: Int, numAfter: Int, narrows: List<Narrow>): Single<List<MessageResponse>>{
        return messageApiService.getMessagesRange(anchor, numBefore, numAfter, Narrow.toJson(narrows)).map {
            it.messages
        }
    }

    fun fetchMessagesRange(anchor: String, numBefore: Int, numAfter: Int, narrows: List<Narrow>): Single<List<MessageResponse>>{
        return messageApiService.getMessagesRange(anchor, numBefore, numAfter, Narrow.toJson(narrows)).map {
            it.messages
        }
    }

    fun addReaction(messageId: Int, emojiName: String, emojiCode: String): Single<ReactionOperationResponse>{
        return messageApiService.addReaction(messageId, emojiName, emojiCode)
    }

    fun removeReaction(messageId: Int, emojiName: String, emojiCode: String): Single<ReactionOperationResponse>{
        return messageApiService.removeReaction(messageId, emojiName, emojiCode)
    }
}