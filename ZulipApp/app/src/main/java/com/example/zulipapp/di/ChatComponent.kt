package com.example.zulipapp.di

import dagger.Component

@FragmentScope
@Component(dependencies = [ActivityComponent::class], modules = [ChatModule::class])
interface ChatComponent {

    //fun inject(chatFragment: ChatFragment)

    @Component.Factory
    interface Factory {
        fun create(activityComponent: ActivityComponent): ChatComponent
    }
}