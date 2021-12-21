package com.example.zulipapp.presentation.chat.elm

import com.example.zulipapp.domain.usecase.AddReactionUseCase
import com.example.zulipapp.domain.usecase.GetMessagesUseCase
import com.example.zulipapp.domain.usecase.RemoveReactionUseCase
import com.example.zulipapp.domain.usecase.SendMessageUseCase
import com.example.zulipapp.presentation.chat.adapter.MessageToItemMapper
import io.reactivex.Observable
import vivid.money.elmslie.core.ActorCompat

class ChatActor(
    private val getMessagesUseCase: GetMessagesUseCase,
    private val sendMessageUseCase: SendMessageUseCase,
    private val addReactionUseCase: AddReactionUseCase,
    private val removeReactionUseCase: RemoveReactionUseCase
) : ActorCompat<ChatCommand, ChatEvent>{

    companion object{
        const val PAGE_SIZE = 40
        const val REFRESH_PAGE_SIZE = 10
    }

    override fun execute(command: ChatCommand): Observable<ChatEvent> {
        when(command){
            is ChatCommand.LoadFirstPage -> {
                return getMessagesUseCase.getMessages("newest", PAGE_SIZE, 0, command.narrows)
                    .map(MessageToItemMapper())
                    .map { ChatEvent.Internal.FirstPageLoaded(it) as ChatEvent }
                    .doOnError { ChatEvent.Internal.ErrorLoading }
                    .toObservable()
            }
            is ChatCommand.LoadPage -> {
                return getMessagesUseCase.getMessages(command.anchor, PAGE_SIZE, 0, command.narrows)
                    .map(MessageToItemMapper())
                    .map { ChatEvent.Internal.PageLoaded(it) as ChatEvent }
                    .doOnError { ChatEvent.Internal.ErrorLoading }
                    .toObservable()
            }
            is ChatCommand.SendMessage -> {
                return sendMessageUseCase(command.type, command.streamName, command.topicName, command.message)
                    .map { ChatEvent.Internal.SuccessSending(it, command.narrows) as ChatEvent }
                    .doOnError { ChatEvent.Internal.ErrorSending }
                    .toObservable()
            }
            is ChatCommand.AddReaction -> {
                return addReactionUseCase(command.messageId, command.emojiName, command.emojiCode)
                    .map {
                        ChatEvent.Internal.AddReactionSuccess(it, command.messageId, command.narrows, command.position) as ChatEvent
                    }
                    .doOnError { ChatEvent.Internal.AddReactionError }
                    .toObservable()
            }
            is ChatCommand.RemoveReaction -> {
                return removeReactionUseCase(command.messageId, command.emojiName, command.emojiCode)
                    .map {
                        ChatEvent.Internal.RemoveReactionSuccess(it, command.messageId, command.narrows, command.position) as ChatEvent
                    }
                    .doOnError { ChatEvent.Internal.RemoveReactionError }
                    .toObservable()
            }
            is ChatCommand.RefreshMessage -> {
                return getMessagesUseCase.getMessages(command.messageId, 0, 0, command.narrows)
                    .map(MessageToItemMapper())
                    .map { ChatEvent.Internal.RefreshMessage(it.first(), command.position) as ChatEvent }
                    .doOnError { ChatEvent.Internal.AddReactionError }
                    .toObservable()
            }
            is ChatCommand.RefreshChat -> {
                return getMessagesUseCase.getMessages(command.currentNewestMessageId, 0, REFRESH_PAGE_SIZE, command.narrows)
                    .map(MessageToItemMapper())
                    .map {
                        ChatEvent.Internal.RefreshChat(it.drop(1), command.narrows) as ChatEvent
                    }
                    .doOnError { ChatEvent.Internal.ErrorSending }
                    .toObservable()
            }
        }
    }
}