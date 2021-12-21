package com.example.zulipapp.presentation.channels.elm

import com.example.zulipapp.presentation.channels.adapter.ChannelsItem

data class ChannelsState(
    val isSubscribedLoading: Boolean = false,
    val searchedSubscribedStreams: List<ChannelsItem.StreamItem>? = null,
    val isAllLoading: Boolean = false,
    val searchedAllStreams: List<ChannelsItem.StreamItem>? = null,
)