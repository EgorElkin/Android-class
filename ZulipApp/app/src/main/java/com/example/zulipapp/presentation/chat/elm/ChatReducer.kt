package com.example.zulipapp.presentation.chat.elm

import android.media.effect.Effect
import com.example.zulipapp.presentation.chat.adapter.ChatItem
import vivid.money.elmslie.core.store.dsl_reducer.ScreenDslReducer

class ChatReducer :
    ScreenDslReducer<ChatEvent, ChatEvent.Ui, ChatEvent.Internal, ChatState, ChatEffect, ChatCommand>(
        ChatEvent.Ui::class,
        ChatEvent.Internal::class
) {

    private var anchor = -1

    override fun Result.internal(event: ChatEvent.Internal) = when(event){
        is ChatEvent.Internal.FirstPageLoaded -> {
            anchor = (event.messages.first() as ChatItem.MessageItem).messageId
            println("debug: ChatReducer: FirstLoaded:${event.messages.size}: id=$anchor text=${(event.messages.first() as ChatItem.MessageItem).message}")
            state { copy(isLoading = false, newMessages = event.messages) }
        }
        is ChatEvent.Internal.PageLoaded -> {
            anchor = (event.messages.first() as ChatItem.MessageItem).messageId
            println("debug: ChatReducer: NextLoaded:${event.messages.size}: id=$anchor text=${(event.messages.first() as ChatItem.MessageItem).message}")
            if(event.messages.size == 1){
                state { copy(isFullyLoaded = true, isLoading = false, newMessages = event.messages.dropLast(1)) }
                effects { +ChatEffect.FullyLoaded }
            } else {
                state { copy(isLoading = false, newMessages = event.messages.dropLast(1)) }
            }
        }
        is ChatEvent.Internal.ErrorLoading -> {
            effects { +ChatEffect.ShowLoadingError }
        }
        is ChatEvent.Internal.SuccessSending -> {
            println("debug: ChatReducer: SuccessSending: id=${event.messageId}")
        }
        is ChatEvent.Internal.ErrorSending -> {
            println("debug: ChatReducer: ErrorSending: error=${event.error}")
        }
    }

    override fun Result.ui(event: ChatEvent.Ui) = when(event){
        is ChatEvent.Ui.Init -> {

        }
        is ChatEvent.Ui.LoadFirstPage -> {
            println("debug: ChatReducer: LoadFirsPage")
            state { copy(isLoading = true, newMessages = emptyList()) }
            commands { +ChatCommand.LoadFirstPage(event.narrows) }
        }
        is ChatEvent.Ui.LoadNextPage -> {
            println("debug: ChatReducer: LoadNestPage")
            state { copy(isLoading = true, newMessages = emptyList()) }
            commands { +ChatCommand.LoadPage(anchor, event.narrows) }
        }
        is ChatEvent.Ui.SendButtonClicked -> {
            if(event.message.isEmpty()){
                effects { +ChatEffect.EmptyMessage }
            } else {
//                state { copy() }
                commands { +ChatCommand.SendMessage(event.type, event.streamName, event.topicName, event.message) }
            }
        }
    }
}