package com.example.zulipapp.domain.usecase

import com.example.zulipapp.data.api.entity.message.Narrow
import com.example.zulipapp.domain.entity.Message
import com.example.zulipapp.domain.repository.MessageRepository
import io.reactivex.Single

interface GetMessagesUseCase{

    fun getMessages(anchor: String, numBefore: Int, numAfter: Int, narrows: List<Narrow>): Single<List<Message>>

    fun getMessages(anchor: Int, numBefore: Int, numAfter: Int, narrows: List<Narrow>): Single<List<Message>>
}

class GetMessagesUseCaseImpl(private val messageRepository: MessageRepository) : GetMessagesUseCase{

    override fun getMessages(anchor: String, numBefore: Int, numAfter: Int, narrows: List<Narrow>): Single<List<Message>> {
        return messageRepository.fetchMessagesRange(anchor, numBefore, numAfter, narrows)
    }

    override fun getMessages(anchor: Int, numBefore: Int, numAfter: Int, narrows: List<Narrow>): Single<List<Message>> {
        return messageRepository.fetchMessagesRange(anchor, numBefore, numAfter, narrows)
    }
}