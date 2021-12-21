package com.example.zulipapp.di

import com.example.zulipapp.domain.repository.MessageRepository
import com.example.zulipapp.domain.repository.StreamRepository
import com.example.zulipapp.domain.repository.UserRepository
import com.example.zulipapp.presentation.main.MainActivity
import dagger.Component

@ActivityScope
@Component(dependencies = [AppComponent::class], modules = [RepositoryModule::class])
interface ActivityComponent {

    fun inject(activity: MainActivity)

    fun streamRepository(): StreamRepository
    fun userRepository(): UserRepository
    fun messageRepository(): MessageRepository

    @Component.Factory
    interface Factory{
        fun create(appComponent: AppComponent): ActivityComponent
    }
}