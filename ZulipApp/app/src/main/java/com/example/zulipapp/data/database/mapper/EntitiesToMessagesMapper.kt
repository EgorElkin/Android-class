package com.example.zulipapp.data.database.mapper

import com.example.zulipapp.data.database.entity.MessageEntity
import com.example.zulipapp.domain.entity.Message
import com.example.zulipapp.domain.entity.Reaction

class EntitiesToMessagesMapper : (List<MessageEntity>) -> List<Message> {
    override fun invoke(entities: List<MessageEntity>): List<Message> {
        return entities.map {
            Message(
                id = it.id,
                senderId = it.senderId,
                content = it.content,
                timeStamp = it.timeStamp,
                topic = it.topic,
                senderFullName = it.senderFullName,
                streamId = it.streamId,
                senderEmail = "",
                senderAvatar = it.senderAvatar,
                type = it.type,
                contentType = it.contentType,
                reactions = it.reactions.map { reactionEntity ->
                    Reaction(
                        emojiName = reactionEntity.emojiName,
                        emojiCode = reactionEntity.emojiCode,
                        reactionType = reactionEntity.reactionType,
                        userId = reactionEntity.userId
                    )
                }
            )
        }
    }
}