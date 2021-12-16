package com.example.zulipapp.di

import com.example.zulipapp.domain.repository.MessageRepository
import com.example.zulipapp.domain.usecase.GetMessagesUseCase
import com.example.zulipapp.domain.usecase.GetMessagesUseCaseImpl
import dagger.Module
import dagger.Provides

@Module
class ChatModule {

    @Provides
    @FragmentScope
    fun provideGetMessagesUseCase(messageRepository: MessageRepository): GetMessagesUseCase = GetMessagesUseCaseImpl(messageRepository)
}