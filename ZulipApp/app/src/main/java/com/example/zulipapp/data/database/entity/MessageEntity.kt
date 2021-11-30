package com.example.zulipapp.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "messages_table")
class MessageEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "type")
    val type: String, // stream/private
    @ColumnInfo(name = "stream_id")
    val streamId: Int,
    @ColumnInfo(name = "topic")
    val topic: String,
    @ColumnInfo(name = "sender_id")
    val senderId: Int,
    @ColumnInfo(name = "sender_name")
    val senderFullName: String,
    @ColumnInfo(name = "sender_avatar")
    val senderAvatar: String,
    @ColumnInfo(name = "content_type")
    val contentType: String, // text/html
    @ColumnInfo(name = "content")
    val content: String,
    @ColumnInfo(name = "time_stamp")
    val timeStamp: Int,
    @ColumnInfo(name = "reactions")
    val reactions: List<ReactionEntity>
)