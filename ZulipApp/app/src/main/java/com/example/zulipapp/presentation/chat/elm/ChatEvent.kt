package com.example.zulipapp.presentation.chat.elm

import com.example.zulipapp.data.api.entity.message.Narrow
import com.example.zulipapp.presentation.chat.adapter.ChatItem

sealed class ChatEvent {

    sealed class Ui : ChatEvent(){
        object Init : Ui()
        class LoadFirstPage(val narrows: List<Narrow>) : Ui()
        class LoadNextPage(val narrows: List<Narrow>) : Ui()
        class SendButtonClicked(val type: String, val streamName: String, val topicName: String?, val message: String) : Ui()
    }

    sealed class Internal : ChatEvent(){
        class FirstPageLoaded(val messages: List<ChatItem>) : Internal()
        class PageLoaded(val messages: List<ChatItem>) : Internal()
        class ErrorLoading(val error: Throwable) : Internal()
        class ErrorSending(val error: Throwable) : Internal()
        class SuccessSending(val messageId: Int) : Internal()
    }
}