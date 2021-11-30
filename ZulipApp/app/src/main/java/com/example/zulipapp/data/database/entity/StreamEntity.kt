package com.example.zulipapp.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "streams_table")
class StreamEntity(
    @PrimaryKey
    @ColumnInfo(name = "stream_id")
    val streamId: Int,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "description")
    val description: String,
    @ColumnInfo(name = "first_message_id")
    val firstMessageId: Int,
    @ColumnInfo(name = "color")
    val color: String,
    @ColumnInfo(name = "topics")
    val topics: List<TopicEntity>,
    @ColumnInfo(name = "subscription")
    val subscription: Boolean
)