package com.example.zulipapp.data.api.entity.message

import com.google.gson.annotations.SerializedName

class ReactionResponse(
    @SerializedName("emoji_name")
    val emojiName: String, //"silence"
    @SerializedName("emoji_code")
    val emojiCode: String, //"1f910"
    @SerializedName("reaction_type")
    val reactionType: String, //"unicode_emoji" "realm_emoji" "zulip_extra_emoji"
    @SerializedName("user_id")
    val userId: Int
)