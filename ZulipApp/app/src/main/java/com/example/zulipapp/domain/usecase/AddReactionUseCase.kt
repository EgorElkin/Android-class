package com.example.zulipapp.domain.usecase

import com.example.zulipapp.domain.repository.MessageRepository
import io.reactivex.Single

interface AddReactionUseCase : (Int, String, String) -> Single<Boolean>

class AddReactionUseCaseImpl(private val messageRepository: MessageRepository) : AddReactionUseCase{

    override fun invoke(messageId: Int, emojiName: String, emojiCode: String): Single<Boolean> {
        return messageRepository.addReaction(messageId, emojiName, emojiCode)
    }

}