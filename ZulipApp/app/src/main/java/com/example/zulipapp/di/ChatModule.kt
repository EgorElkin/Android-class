package com.example.zulipapp.di

import com.example.zulipapp.domain.repository.MessageRepository
import com.example.zulipapp.domain.usecase.*
import com.example.zulipapp.presentation.chat.elm.*
import com.example.zulipapp.presentation.people.elm.*
import dagger.Module
import dagger.Provides
import vivid.money.elmslie.core.ElmStoreCompat

@Module
class ChatModule {

    @Provides
    @FragmentScope
    fun provideGetMessagesUseCase(messageRepository: MessageRepository): GetMessagesUseCase = GetMessagesUseCaseImpl(messageRepository)

    @Provides
    @FragmentScope
    fun provideSendMessageUseCase(messageRepository: MessageRepository): SendMessageUseCase = SendMessageUseCaseImpl(messageRepository)

    @Provides
    @FragmentScope
    fun provideChatState(): ChatState {
        return ChatState()
    }

    @Provides
    @FragmentScope
    fun provideChatActor(getMessagesUseCase: GetMessagesUseCase, sendMessageUseCase: SendMessageUseCase): ChatActor {
        return ChatActor(getMessagesUseCase, sendMessageUseCase)
    }

    @Provides
    @FragmentScope
    fun provideChatReducer(): ChatReducer {
        return ChatReducer()
    }

    @Provides
    @FragmentScope
    fun provideChatStoreFactory(
        peopleState: ChatState,
        peopleReducer: ChatReducer,
        peopleActor: ChatActor
    ): ElmStoreCompat<ChatEvent, ChatState, ChatEffect, ChatCommand> = ElmStoreCompat(
        initialState = peopleState,
        reducer = peopleReducer,
        actor = peopleActor
    )
}