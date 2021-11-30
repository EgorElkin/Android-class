package com.example.zulipapp.presentation.chat.adapter

class ReactionItem(
    val emojiName: String,
    val emojiCode: String,
    val reactionType: String,
    val userIds: MutableList<Int>
)