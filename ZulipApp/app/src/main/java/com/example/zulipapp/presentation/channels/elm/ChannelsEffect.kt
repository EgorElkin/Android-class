package com.example.zulipapp.presentation.channels.elm

import com.example.zulipapp.presentation.channels.adapter.ChannelsItem

sealed class ChannelsEffect {

    object ShowLoadingError : ChannelsEffect()
    class NavigateToStream(val stream: ChannelsItem.StreamItem) : ChannelsEffect()
    class NavigateToTopic(val topic: ChannelsItem.TopicItem) : ChannelsEffect()
}