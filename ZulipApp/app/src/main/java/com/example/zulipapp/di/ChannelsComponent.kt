package com.example.zulipapp.di

import com.example.zulipapp.presentation.channels.elm.ChannelsCommand
import com.example.zulipapp.presentation.channels.elm.ChannelsEffect
import com.example.zulipapp.presentation.channels.elm.ChannelsEvent
import com.example.zulipapp.presentation.channels.elm.ChannelsState
import dagger.Component
import vivid.money.elmslie.core.ElmStoreCompat

@FragmentScope
@Component(dependencies = [ActivityComponent::class], modules = [ChannelsModule::class])
interface ChannelsComponent {

    val channelsStore: ElmStoreCompat<ChannelsEvent, ChannelsState, ChannelsEffect, ChannelsCommand>

    @Component.Factory
    interface Factory{
        fun create(activityComponent: ActivityComponent): ChannelsComponent
    }
}