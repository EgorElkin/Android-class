package com.example.zulipapp.data.database.entity

class ReactionEntity(
    val emojiName: String, //"silence"
    val emojiCode: String, //"1f910"
    val reactionType: String, //"unicode_emoji" "realm_emoji" "zulip_extra_emoji"
    val userId: Int
)