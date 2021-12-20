package com.example.zulipapp.presentation.chat.elm

import com.example.zulipapp.presentation.chat.adapter.ChatItem

data class ChatState(
    val isFullyLoaded: Boolean = false,
    val isLoading: Boolean = false,
    val newMessages: List<ChatItem> = emptyList(),
    val messageText: String = ""
)