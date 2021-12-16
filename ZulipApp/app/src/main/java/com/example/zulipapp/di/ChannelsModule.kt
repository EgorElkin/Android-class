package com.example.zulipapp.di

import com.example.zulipapp.domain.repository.StreamRepository
import com.example.zulipapp.domain.usecase.*
import dagger.Module
import dagger.Provides
import javax.inject.Qualifier

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
    @SearchAllQualifier
    @FragmentScope
    fun provideSearchAllStreamUseCase(streamRepository: StreamRepository): SearchStreamUseCase = SearchAllStreamUseCaseImpl(streamRepository)

    @Provides
    @SearchSubscribedQualifier
    @FragmentScope
    fun provideSearchSubscribedStreamUseCase(streamRepository: StreamRepository): SearchStreamUseCase = SearchSubscribedStreamUseCaseImpl(streamRepository)

    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class SearchAllQualifier

    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class SearchSubscribedQualifier
}