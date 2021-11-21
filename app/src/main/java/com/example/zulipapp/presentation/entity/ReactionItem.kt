package com.example.zulipapp.presentation.entity

class ReactionItem(
    val emojiName: String,
    val emojiCode: String,
    val reactionType: String,
    val userIds: MutableList<Int>
)