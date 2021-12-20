package com.example.zulipapp.presentation.channels.elm

import com.example.zulipapp.presentation.channels.adapter.ChannelsItem

sealed class ChannelsEvent {

    sealed class Ui : ChannelsEvent(){
        object Init : Ui()
        class SearchQueryChanged(val searchQuery: String) : Ui()
        class SearchButtonClicked(val searchQuery: String) : Ui()
        class TabChanged(val newPosition: Int, val searchQuery: String) : Ui()
        class TopicSelected(val streamName: String, val topic: ChannelsItem.TopicItem) : Ui()
        class StreamSelected(val stream: ChannelsItem.StreamItem) : Ui()
    }

    sealed class Internal : ChannelsEvent() {
        class SubscribedLoadingSuccess(val streams: List<ChannelsItem.StreamItem>) : Internal()
        class SubscribedSearchingSuccess(val streams: List<ChannelsItem.StreamItem>) : Internal()
        class SubscribedLoadingError(val error: Throwable) : Internal()
        class AllLoadingSuccess(val streams: List<ChannelsItem.StreamItem>) : Internal()
        class AllSearchingSuccess(val streams: List<ChannelsItem.StreamItem>) : Internal()
        class AllLoadingError(val error: Throwable) : Internal()
    }
}