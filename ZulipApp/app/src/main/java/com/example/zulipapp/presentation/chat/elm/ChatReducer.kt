package com.example.zulipapp.presentation.chat.elm

import com.example.zulipapp.presentation.chat.adapter.ChatItem
import vivid.money.elmslie.core.store.dsl_reducer.ScreenDslReducer

class ChatReducer :
    ScreenDslReducer<ChatEvent, ChatEvent.Ui, ChatEvent.Internal, ChatState, ChatEffect, ChatCommand>(
        ChatEvent.Ui::class,
        ChatEvent.Internal::class
) {

    private var newestMessageId = -1
    private var anchor = -1

    override fun Result.internal(event: ChatEvent.Internal) = when(event){
        is ChatEvent.Internal.FirstPageLoaded -> {
            anchor = (event.messages.first() as ChatItem.MessageItem).messageId
            newestMessageId = (event.messages.last() as ChatItem.MessageItem).messageId
            state { copy(isLoading = false, newMessages = event.messages) }
        }
        is ChatEvent.Internal.PageLoaded -> {
            anchor = (event.messages.first() as ChatItem.MessageItem).messageId
            if(event.messages.size == 1){
                state { copy(isFullyLoaded = true, isLoading = false, newMessages = event.messages.dropLast(1)) }
            } else {
                state { copy(isLoading = false, newMessages = event.messages.dropLast(1)) }
            }
        }
        is ChatEvent.Internal.ErrorLoading -> {
            effects { +ChatEffect.ShowLoadingError }
        }
        is ChatEvent.Internal.SuccessSending -> {
            effects { +ChatEffect.ClearInput }
            commands { +ChatCommand.RefreshChat(newestMessageId, event.narrows) }
        }
        is ChatEvent.Internal.ErrorSending -> {
            effects { +ChatEffect.SendingError }
        }
        is ChatEvent.Internal.AddReactionSuccess -> {
            if(event.isSuccessful){
                commands { +ChatCommand.RefreshMessage(event.messageId, event.narrows, event.position) }
            } else {
                effects { +ChatEffect.AddReactionError }
            }
        }
        is ChatEvent.Internal.AddReactionError -> {
            effects { +ChatEffect.AddReactionError }
        }
        is ChatEvent.Internal.RemoveReactionSuccess -> {
            if(event.isSuccessful){
                commands { +ChatCommand.RefreshMessage(event.messageId, event.narrows, event.position) }
            } else {
                effects { +ChatEffect.RemoveReactionError }
            }
        }
        is ChatEvent.Internal.RemoveReactionError -> {
            effects { +ChatEffect.RemoveReactionError }
        }
        is ChatEvent.Internal.RefreshMessage -> {
            effects { +ChatEffect.RefreshMessage(event.message, event.position) }
        }
        is ChatEvent.Internal.RefreshChat -> {
            if(event.messages.isNotEmpty()){
                newestMessageId = (event.messages.last() as ChatItem.MessageItem).messageId
                if(event.messages.size == ChatActor.REFRESH_PAGE_SIZE){
                    commands { +ChatCommand.RefreshChat(newestMessageId, event.narrows) }
                }
            }
            effects { +ChatEffect.RefreshChat(event.messages) }
        }
    }

    override fun Result.ui(event: ChatEvent.Ui) = when(event){
        is ChatEvent.Ui.Init -> {}
        is ChatEvent.Ui.LoadFirstPage -> {
            state { copy(isLoading = true, newMessages = emptyList()) }
            commands { +ChatCommand.LoadFirstPage(event.narrows) }
        }
        is ChatEvent.Ui.LoadNextPage -> {
            state { copy(isLoading = true, newMessages = emptyList()) }
            commands { +ChatCommand.LoadPage(anchor, event.narrows) }
        }
        is ChatEvent.Ui.SendButtonClicked -> {
            if(event.message.isEmpty()){
                effects { +ChatEffect.EmptyMessage }
            } else {
                commands { +ChatCommand.SendMessage(event.type, event.streamName, event.topicName, event.message, event.narrows) }
            }
        }
        is ChatEvent.Ui.ReactionSelected -> {
            commands { +ChatCommand.AddReaction(event.messageId, event.emojiName, event.emojiCode, event.position, event.narrows) }
        }
        is ChatEvent.Ui.ReactionRemoved -> {
            commands { +ChatCommand.RemoveReaction(event.messageId, event.emojiName, event.emojiCode, event.position, event.narrows) }
        }
    }
}