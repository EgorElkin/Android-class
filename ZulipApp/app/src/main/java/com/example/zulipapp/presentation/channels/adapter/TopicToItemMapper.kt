package com.example.zulipapp.presentation.channels.adapter

import com.example.zulipapp.domain.entity.Topic

class TopicToItemMapper : (List<Topic>) -> List<ChannelsItem.TopicItem> {

    override fun invoke(topics: List<Topic>): List<ChannelsItem.TopicItem> {
        return topics.map{
            ChannelsItem.TopicItem(
                ChannelsItemViewType.TOPIC,
                it.name,
                1,
                it.lastMessageId,
                1,
                ""
            )
        }
    }

}