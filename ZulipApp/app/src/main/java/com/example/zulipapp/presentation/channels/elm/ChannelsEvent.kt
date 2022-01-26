package com.example.zulipapp.presentation.channels.elm

import com.example.zulipapp.presentation.channels.adapter.ChannelsItem

sealed class ChannelsEvent {

    sealed class Ui : ChannelsEvent() {

        object Init : Ui()
        class SearchQueryChanged(val position: Int, val searchQuery: String) : Ui()
        class SearchButtonClicked(val position: Int, val searchQuery: String) : Ui()
        class TabChanged(val newPosition: Int, val searchQuery: String) : Ui()

        class TopicSelected(val topic: ChannelsItem.TopicItem) : Ui()
        class StreamSelected(val stream: ChannelsItem.StreamItem) : Ui()
//        class SubscribedItemExpanded(index: Int) : Ui()
//        class AllItemExpanded(index: Int) : Ui()

//        class ExpandedClicked(tabPosition: Int) : Ui()

    }

    sealed class Internal : ChannelsEvent() {

        class SubscribedLoadingSuccess(val streams: List<ChannelsItem.StreamItem>) : Internal()
        class SubscribedLoadingError(val error: Throwable) : Internal()
        class SubscribedSearchSuccess(val streams: List<ChannelsItem>, val searchQuery: String) : Internal()

        class AllLoadingSuccess(val streams: List<ChannelsItem.StreamItem>) : Internal()
        class AllLoadingError(val error: Throwable) : Internal()
        class AllSearchSuccess(val streams: List<ChannelsItem>, val searchQuery: String) : Internal()

    }
}
