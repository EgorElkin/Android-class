package com.example.zulipapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.zulipapp.data.database.dao.MessageDao
import com.example.zulipapp.data.database.dao.StreamDao
import com.example.zulipapp.data.database.dao.TopicDao
import com.example.zulipapp.data.database.dao.UserDao
import com.example.zulipapp.data.database.entity.*

@Database(
    entities = [StreamEntity::class, UserEntity::class, MessageEntity::class],
    version = 6
)
@TypeConverters(TopicConverter::class, ReactionConverter::class)
abstract class AppDatabase : RoomDatabase(){
    abstract fun StreamDao(): StreamDao
    abstract fun UserDao(): UserDao
    abstract fun MessageDao(): MessageDao
}