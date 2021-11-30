package com.example.zulipapp.data.database

import android.content.Context
import androidx.room.Room

object  RoomBuilder {

    fun provideAppDatabase(applicationContext: Context): AppDatabase{
        return Room.databaseBuilder(applicationContext, AppDatabase::class.java, "Zulip Database")
            .fallbackToDestructiveMigration()
            .build()
    }
}