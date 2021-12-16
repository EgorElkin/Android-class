package com.example.zulipapp.di

import android.content.Context
import androidx.room.Room
import com.example.zulipapp.data.database.AppDatabase
import com.example.zulipapp.data.database.LocalMessageDataSource
import com.example.zulipapp.data.database.LocalStreamDataSource
import com.example.zulipapp.data.database.LocalUserDataSource
import com.example.zulipapp.data.database.dao.MessageDao
import com.example.zulipapp.data.database.dao.StreamDao
import com.example.zulipapp.data.database.dao.UserDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(context: Context): AppDatabase{
        return Room.databaseBuilder(context, AppDatabase::class.java, "Zulip Database")
            .fallbackToDestructiveMigration()
            .build()
    }


    @Provides
    @Singleton
    fun provideStreamDao(database: AppDatabase): StreamDao = database.StreamDao()

    @Provides
    @Singleton
    fun provideUserDao(database: AppDatabase): UserDao = database.UserDao()

    @Provides
    @Singleton
    fun provideMessageDao(database: AppDatabase) = database.MessageDao()

    @Provides
    @Singleton
    fun provideLocalStreamDataSource(streamDao: StreamDao): LocalStreamDataSource = LocalStreamDataSource(streamDao)

    @Provides
    @Singleton
    fun provideLocalUserDataSource(userDao: UserDao): LocalUserDataSource = LocalUserDataSource(userDao)

    @Provides
    @Singleton
    fun provideLocalMessageDataSource(messageDao: MessageDao): LocalMessageDataSource = LocalMessageDataSource(messageDao)
}