package com.example.zulipapp.domain.entity

class Message(
    val id: Int,
    val senderId: Int,
    val content: String,
    val timeStamp: Int,
    val topic: String,
    val senderFullName: String,
    val streamId: Int = 0,
    val senderEmail: String,
    val senderAvatar: String,
    val type: String, // stream/private
    val contentType: String, // text/html
    val reactions: List<Reaction>
)