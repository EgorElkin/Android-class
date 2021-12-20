package com.example.zulipapp.presentation.chat.elm

sealed class ChatEffect {

    object ShowLoadingError : ChatEffect()
    object FullyLoaded : ChatEffect()
    object EmptyMessage : ChatEffect()
}