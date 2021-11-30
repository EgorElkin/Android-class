package com.example.zulipapp.presentation.channels.adapter

interface ChannelsItem {

    val viewType: ChannelsItemViewType
    val name: String
    val id: Int

    class StreamItem(
        override val viewType: ChannelsItemViewType,
        override val name: String,
        override val id: Int,
        var expanded: Boolean = false,
        val description: String,
        val firstMessageId: Int,
        val color: Int,
        var topics: List<TopicItem>
    ) : ChannelsItem

    class TopicItem(
        override val viewType: ChannelsItemViewType,
        override val name: String,
        override val id: Int,
        val lastMessageId: Int,
        var messagesCount: Int,
        var streamName: String
    ) : ChannelsItem
}

enum class ChannelsItemViewType(val type: Int){
    STREAM(0),
    TOPIC(1)
}