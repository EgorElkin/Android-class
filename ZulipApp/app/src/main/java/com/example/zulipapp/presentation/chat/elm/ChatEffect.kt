package com.example.zulipapp.presentation.chat.elm

import com.example.zulipapp.presentation.chat.adapter.ChatItem

sealed class ChatEffect {

    object ShowLoadingError : ChatEffect()
    object EmptyMessage : ChatEffect()
    object ClearInput : ChatEffect()
    class RefreshMessage(val message: ChatItem, val position: Int) : ChatEffect( )
    class RefreshChat(val messages: List<ChatItem>) : ChatEffect( )
    object SendingError : ChatEffect()
    object AddReactionError : ChatEffect()
    object RemoveReactionError : ChatEffect()
}