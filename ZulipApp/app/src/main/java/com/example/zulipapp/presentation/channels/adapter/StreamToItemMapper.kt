package com.example.zulipapp.presentation.channels.adapter

import com.example.zulipapp.domain.entity.Stream

class StreamToItemMapper : (List<Stream>) -> List<ChannelsItem.StreamItem> {

    override fun invoke(streams: List<Stream>): List<ChannelsItem.StreamItem> {
        println("debug: stream mapper")
        return streams.map {
            ChannelsItem.StreamItem(
                ChannelsItemViewType.STREAM,
                it.name,
                it.streamId,
                false,
                it.description,
                it.firstMessageId,
                it.color,
                emptyList()
            )
        }
    }
}