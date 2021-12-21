package com.example.zulipapp.presentation.chat.elm

import com.example.zulipapp.data.api.entity.message.Narrow
import com.example.zulipapp.presentation.chat.adapter.ChatItem

sealed class ChatEvent {

    sealed class Ui : ChatEvent(){
        object Init : Ui()
        class LoadFirstPage(val narrows: List<Narrow>) : Ui()
        class LoadNextPage(val narrows: List<Narrow>) : Ui()
        class SendButtonClicked(val type: String, val streamName: String, val topicName: String?, val message: String, val narrows: List<Narrow>) : Ui()
        class ReactionSelected(val messageId: Int, val emojiName: String, val emojiCode: String, val position: Int, val narrows: List<Narrow>) : Ui()
        class ReactionRemoved(val messageId: Int, val emojiName: String, val emojiCode: String, val position: Int, val narrows: List<Narrow>) : Ui()
    }

    sealed class Internal : ChatEvent(){
        class FirstPageLoaded(val messages: List<ChatItem>) : Internal()
        class PageLoaded(val messages: List<ChatItem>) : Internal()
        object ErrorLoading : Internal()
        object ErrorSending : Internal()
        class SuccessSending(val messageId: Int, val narrows: List<Narrow>) : Internal()
        class AddReactionSuccess(val isSuccessful: Boolean, val messageId: Int, val narrows: List<Narrow>, val position: Int) : Internal()
        object AddReactionError : Internal()
        class RemoveReactionSuccess(val isSuccessful: Boolean, val messageId: Int, val narrows: List<Narrow>, val position: Int) : Internal()
        object RemoveReactionError : Internal()
        class RefreshMessage(val message: ChatItem, val position: Int) : Internal()
        class RefreshChat(val messages: List<ChatItem>, val narrows: List<Narrow>) : Internal()
    }
}