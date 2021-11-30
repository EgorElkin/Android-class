package com.example.zulipapp.data.database.dao

import androidx.room.*
import com.example.zulipapp.data.database.entity.StreamEntity
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface StreamDao {

    @Query("SELECT * FROM streams_table")
    fun getAllStreams(): Single<List<StreamEntity>>

    @Query("SELECT * FROM STREAMS_TABLE WHERE subscription = 1")
    fun getSubscribedStreams(): Single<List<StreamEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(stream: StreamEntity): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(streams: List<StreamEntity>): Completable

    @Delete
    fun delete(stream: StreamEntity): Completable
}