package com.example.zulipapp.domain.entity

class Stream(
    val streamId: Int,
    val name: String,
    val description: String,
    val firstMessageId: Int,
    val color: Int = 0x999999
)