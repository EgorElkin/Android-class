package com.example.zulipapp.data.api.mapper

import com.example.zulipapp.data.api.entity.message.MessageResponse
import com.example.zulipapp.data.api.entity.message.ReactionResponse
import com.example.zulipapp.domain.entity.Message
import com.example.zulipapp.domain.entity.Reaction

class ResponseToMessageMapper : (List<MessageResponse>) -> List<Message>{
    override fun invoke(messages: List<MessageResponse>): List<Message> {
        return messages.map {
            Message(
                it.id,
                it.senderId,
                it.content,
                it.timeStamp,
                it.topic,
                it.senderFullName,
                it.streamId,
                it.senderEmail,
                it.senderAvatar,
                it.type,
                it.contentType,
                ResponseToReactionMapper().invoke(it.reactions)
            )
        }
    }

}

class ResponseToReactionMapper : (List<ReactionResponse>) -> List<Reaction>{
    override fun invoke(responses: List<ReactionResponse>): List<Reaction> {
        return responses.map {
            Reaction(
                it.emojiName,
                it.emojiCode,
                it.reactionType,
                it.userId
            )
        }
    }

}