package com.example.zulipapp.presentation.channels.elm

import com.example.zulipapp.presentation.channels.ChannelsViewPagerAdapter
import com.example.zulipapp.presentation.channels.adapter.ChannelsItem

data class ChannelsState(
    val isSubscribedLoading: Boolean = false,
//    val subscribedStreams: List<ChannelsItem.StreamItem>? = null,
    val searchedSubscribedStreams: List<ChannelsItem.StreamItem>? = null,
    val isNewSubscribed: Boolean = false,

    val isAllLoading: Boolean = false,
//    val allStreams: List<ChannelsItem.StreamItem>? = null,
    val searchedAllStreams: List<ChannelsItem.StreamItem>? = null,
    val isNewAll: Boolean = false,

    val currentTab: Int = ChannelsViewPagerAdapter.SUBSCRIBED_FRAGMENT_POSITION,

    val currentSubscribedSearchQuery: String = "",
    val currentAllSearchQuery: String = ""

//    val emptySubscribedList: Boolean = false,
//    val emptySubscribedSearchResult: Boolean = false,
//    val emptyAllList: Boolean = false,
//    val emptyAllSearchResult: Boolean = false
)