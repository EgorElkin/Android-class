package com.example.zulipapp.presentation.channels.elm

import com.example.zulipapp.presentation.channels.adapter.ChannelsItem

sealed class ChannelsCommand{

    object LoadSubscribedStreams : ChannelsCommand()
    object LoadAllStreams : ChannelsCommand()

    class SearchSubscribedStreams(val streams: List<ChannelsItem>, val searchQuery: String) : ChannelsCommand()
    class SearchAllStreams(val streams: List<ChannelsItem>, val searchQuery: String) : ChannelsCommand()
}
