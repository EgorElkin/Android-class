package com.example.zulipapp.di

import com.example.zulipapp.data.api.NetworkMessageDataSource
import com.example.zulipapp.data.api.NetworkStreamDataSource
import com.example.zulipapp.data.api.NetworkUserDataSource
import com.example.zulipapp.data.database.LocalUserDataSource
import com.example.zulipapp.data.repository.MessageRepositoryImpl
import com.example.zulipapp.data.repository.StreamRepositoryImpl
import com.example.zulipapp.data.repository.UserRepositoryImpl
import com.example.zulipapp.domain.repository.MessageRepository
import com.example.zulipapp.domain.repository.StreamRepository
import com.example.zulipapp.domain.repository.UserRepository
import dagger.Module
import dagger.Provides

@Module
class RepositoryModule {

    @Provides
    @ActivityScope
    fun provideStreamRepository(networkStreamDataSource: NetworkStreamDataSource): StreamRepository{
        return StreamRepositoryImpl(networkStreamDataSource)
    }

    @Provides
    @ActivityScope
    fun provideUserRepository(networkUserDataSource: NetworkUserDataSource, localUserDataSource: LocalUserDataSource): UserRepository{
        return UserRepositoryImpl(networkUserDataSource, localUserDataSource)
    }

    @Provides
    @ActivityScope
    fun provideMessageRepository(networkMessageDataSource: NetworkMessageDataSource): MessageRepository{
        return MessageRepositoryImpl(networkMessageDataSource)
    }
}