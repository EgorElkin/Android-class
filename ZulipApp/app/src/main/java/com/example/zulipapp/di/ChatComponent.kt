package com.example.zulipapp.di

import com.example.zulipapp.presentation.chat.elm.ChatCommand
import com.example.zulipapp.presentation.chat.elm.ChatEffect
import com.example.zulipapp.presentation.chat.elm.ChatEvent
import com.example.zulipapp.presentation.chat.elm.ChatState
import dagger.Component
import vivid.money.elmslie.core.ElmStoreCompat

@FragmentScope
@Component(dependencies = [ActivityComponent::class], modules = [ChatModule::class])
interface ChatComponent {

    val chatStore: ElmStoreCompat<ChatEvent, ChatState, ChatEffect, ChatCommand>

    @Component.Factory
    interface Factory {
        fun create(activityComponent: ActivityComponent): ChatComponent
    }
}