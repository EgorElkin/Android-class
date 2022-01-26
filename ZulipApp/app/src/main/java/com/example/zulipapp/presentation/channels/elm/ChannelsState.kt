package com.example.zulipapp.presentation.channels.elm

import com.example.zulipapp.presentation.channels.adapter.ChannelsItem

data class ChannelsState(
    val currentPosition: Int = 0,
    val searchQuery: String = "",

    val subscribedSearchQuery: String = "",
    val subscribedIsLoading: Boolean = false,
    val subscribedFullList: List<ChannelsItem>? = null,
    val subscribedSearchedList: List<ChannelsItem>? = null,
    val subscribedIsEmpty: Boolean = false,

    val allSearchQuery: String = "",
    val allIsLoading: Boolean = false,
    val allFullList: List<ChannelsItem>? = null,
    val allSearchedList: List<ChannelsItem>? = null,
    val allIsEmpty: Boolean = false,
)