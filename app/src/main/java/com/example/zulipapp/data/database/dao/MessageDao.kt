package com.example.zulipapp.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.zulipapp.data.database.entity.MessageEntity
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface MessageDao {

    @Query("SELECT * FROM messages_table WHERE stream_id = :streamId")
    fun getFromStream(streamId: Int): Single<List<MessageEntity>>

    @Query("SELECT * FROM messages_table WHERE stream_id = :streamId AND topic = :topicName")
    fun getFromTopic(streamId: Int, topicName: String): Single<List<MessageEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(messages: List<MessageEntity>): Completable
}