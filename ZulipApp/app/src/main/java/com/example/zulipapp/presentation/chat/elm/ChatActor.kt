package com.example.zulipapp.presentation.chat.elm

import com.example.zulipapp.domain.usecase.GetMessagesUseCase
import com.example.zulipapp.domain.usecase.SendMessageUseCase
import com.example.zulipapp.presentation.chat.adapter.MessageToItemMapper
import io.reactivex.Observable
import vivid.money.elmslie.core.ActorCompat

class ChatActor(
    private val getMessagesUseCase: GetMessagesUseCase,
    private val sendMessageUseCase: SendMessageUseCase
) : ActorCompat<ChatCommand, ChatEvent>{

    companion object{
        const val PAGE_SIZE = 30
    }

    override fun execute(command: ChatCommand): Observable<ChatEvent> {
        when(command){
            is ChatCommand.LoadFirstPage -> {
                return getMessagesUseCase.getMessages("newest", PAGE_SIZE, 0, command.narrows)
                    .map(MessageToItemMapper())
                    .map {
                        ChatEvent.Internal.FirstPageLoaded(it) as ChatEvent
                    }
                    .doOnError {
                        println("debug: ChatActor: Error=$it")
                        ChatEvent.Internal.ErrorLoading(it)
                    }
                    .toObservable()
            }
            is ChatCommand.LoadPage -> {
                return getMessagesUseCase.getMessages(command.anchor, PAGE_SIZE, 0, command.narrows)
                    .map(MessageToItemMapper())
                    .map {
                        ChatEvent.Internal.PageLoaded(it) as ChatEvent
                    }
                    .doOnError {
                        println("debug: ChatActor: Error=$it")
                        ChatEvent.Internal.ErrorLoading(it)
                    }
                    .toObservable()
            }
            is ChatCommand.SendMessage -> {
                return sendMessageUseCase(command.type, command.streamName, command.topicName, command.message)
                    .map {
                        ChatEvent.Internal.SuccessSending(it) as ChatEvent
                    }
                    .doOnSuccess {
                        println("debug: ChatActor: Sending SUCCESS")
                    }
                    .doOnError {
                        println("debug: ChatActor: Error=$it")
                    }
                    .toObservable()
            }
        }
    }
}