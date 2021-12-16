package com.example.zulipapp.di

import com.example.zulipapp.presentation.channels.ChannelsPresenter
import com.example.zulipapp.presentation.channels.ChannelsPresenterImpl
import dagger.Component

@FragmentScope
@Component(dependencies = [ActivityComponent::class], modules = [ChannelsModule::class])
interface ChannelsComponent {

    fun inject(channelsPresenter: ChannelsPresenter)
//    fun inject(channelsPresenter: ChannelsPresenterImpl)

    @Component.Factory
    interface Factory{
        fun create(activityComponent: ActivityComponent): ChannelsComponent
    }
}