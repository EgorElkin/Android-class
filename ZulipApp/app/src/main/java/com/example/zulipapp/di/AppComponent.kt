package com.example.zulipapp.di

import android.content.Context
import com.example.zulipapp.data.api.NetworkMessageDataSource
import com.example.zulipapp.data.api.NetworkStreamDataSource
import com.example.zulipapp.data.api.NetworkUserDataSource
import com.example.zulipapp.data.database.LocalMessageDataSource
import com.example.zulipapp.data.database.LocalStreamDataSource
import com.example.zulipapp.data.database.LocalUserDataSource
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class, DatabaseModule::class])
interface AppComponent {

    fun networkStreamDataSource(): NetworkStreamDataSource
    fun networkUserDataSource(): NetworkUserDataSource
    fun networkMessageDataSource(): NetworkMessageDataSource

    fun localStreamDataSource(): LocalStreamDataSource
    fun localUserDataSource(): LocalUserDataSource
    fun localMessageDataSource(): LocalMessageDataSource

    @Component.Factory
    interface Factory{
        fun create(@BindsInstance context: Context): AppComponent
    }
}