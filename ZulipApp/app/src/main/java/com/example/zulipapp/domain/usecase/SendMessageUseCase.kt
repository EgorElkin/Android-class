package com.example.zulipapp.domain.usecase

import com.example.zulipapp.domain.repository.MessageRepository
import io.reactivex.Single

interface SendMessageUseCase : (String, String, String?, String) -> Single<Int>

class SendMessageUseCaseImpl(private val messageRepository: MessageRepository) : SendMessageUseCase{

    override fun invoke(type: String, streamName: String, topicName: String?, content: String): Single<Int> {
        return messageRepository.sendMessage(type, streamName, topicName, content)
    }

}