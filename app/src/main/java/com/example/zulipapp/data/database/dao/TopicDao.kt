package com.example.zulipapp.data.database.dao

import androidx.room.*
import com.example.zulipapp.data.database.entity.StreamEntity
import com.example.zulipapp.data.database.entity.TopicEntity
import io.reactivex.Completable

//@Dao
interface TopicDao {

//    @Query("SELECT * FROM TOPICS_TABLE WHERE stream_id = :streamId")
//    fun getTopicsInStream(streamId: Int): List<TopicEntity>
//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    fun insertAll(streams: List<TopicEntity>): Completable
//
//    @Delete
//    fun delete(topic: TopicEntity): Completable
}