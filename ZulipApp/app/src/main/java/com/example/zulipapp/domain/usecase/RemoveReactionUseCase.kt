package com.example.zulipapp.domain.usecase

import com.example.zulipapp.domain.repository.MessageRepository
import io.reactivex.Single

interface RemoveReactionUseCase : (Int, String, String) -> Single<Boolean>

class RemoveReactionUseCaseImpl(private val messageRepository: MessageRepository) : RemoveReactionUseCase{

    override fun invoke(messageId: Int, emojiName: String, emojiCode: String): Single<Boolean> {
        return messageRepository.removeReaction(messageId, emojiName, emojiCode)
    }

}