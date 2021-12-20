package com.example.zulipapp.di

import com.example.zulipapp.domain.repository.StreamRepository
import com.example.zulipapp.domain.usecase.*
import com.example.zulipapp.presentation.channels.elm.*
import dagger.Module
import dagger.Provides
import vivid.money.elmslie.core.ElmStoreCompat

@Module
class ChannelsModule {

    @Provides
    @FragmentScope
    fun provideGetAllStreamsUseCase(streamRepository: StreamRepository): GetAllStreamsUseCase = GetAllStreamsUseCaseImpl(streamRepository)

    @Provides
    @FragmentScope
    fun provideGetSubscribedStreamsUseCase(streamRepository: StreamRepository): GetSubscribedStreamsUseCase = GetSubscribedStreamsUseCaseImpl(streamRepository)

    @Provides
    @FragmentScope
    fun provideGetTopicsUseCase(streamRepository: StreamRepository): GetTopicsUseCase = GetTopicsUseCaseImpl(streamRepository)

    @Provides
    @FragmentScope
    fun provideChannelsState(): ChannelsState {
        return ChannelsState()
    }

    @Provides
    @FragmentScope
    fun provideChannelsActor(getSubscribedStreamsUseCase: GetSubscribedStreamsUseCase, getAllStreamsUseCase: GetAllStreamsUseCase, getTopicsUseCase: GetTopicsUseCase): ChannelsActor {
        return ChannelsActor(getSubscribedStreamsUseCase, getAllStreamsUseCase, getTopicsUseCase)
    }

    @Provides
    @FragmentScope
    fun provideChannelsReducer(): ChannelsReducer {
        return ChannelsReducer()
    }

    @Provides
    @FragmentScope
    fun provideChannelsStoreFactory(
        channelsState: ChannelsState,
        channelsReducer: ChannelsReducer,
        channelsActor: ChannelsActor
    ): ElmStoreCompat<ChannelsEvent, ChannelsState, ChannelsEffect, ChannelsCommand> = ElmStoreCompat(
        initialState = channelsState,
        reducer = channelsReducer,
        actor = channelsActor
    )
}