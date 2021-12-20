package com.example.zulipapp.presentation.channels.elm

import com.example.zulipapp.presentation.channels.adapter.ChannelsItem

sealed class ChannelsCommand {

    object LoadSubscribedStreams : ChannelsCommand()
    class SearchSubscribedStreams(val streams: List<ChannelsItem.StreamItem>, val searchQuery: String) : ChannelsCommand()
    object LoadAllStreams : ChannelsCommand()
    class SearchAllStreams(val streams: List<ChannelsItem.StreamItem>, val searchQuery: String) : ChannelsCommand()
}