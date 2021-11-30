package com.example.zulipapp.presentation.chat.adapter

sealed interface ChatItem{

    val viewType: ChatItemViewType

    class MessageItem(
        override val viewType: ChatItemViewType,
        val messageId: Int,
        val avatarUrl: String,
        val userName: String,
        val message: String,
        val reactionItems: MutableList<ReactionItem>,
        val timeStamp: Int
    ) : ChatItem

    class DateItem(
        override val viewType: ChatItemViewType,
        val date: String
    ) : ChatItem
}

enum class ChatItemViewType(val type: Int){
    INCOMING_MESSAGE(0),
    OUTGOING_MESSAGE(1),
    DATE_ITEM(2)
}