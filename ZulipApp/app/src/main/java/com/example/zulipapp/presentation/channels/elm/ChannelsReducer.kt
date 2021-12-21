package com.example.zulipapp.presentation.channels.elm

import com.example.zulipapp.presentation.channels.ChannelsViewPagerAdapter
import com.example.zulipapp.presentation.channels.adapter.ChannelsItem
import vivid.money.elmslie.core.store.dsl_reducer.ScreenDslReducer

class ChannelsReducer :
    ScreenDslReducer<ChannelsEvent, ChannelsEvent.Ui, ChannelsEvent.Internal, ChannelsState, ChannelsEffect, ChannelsCommand>(
        ChannelsEvent.Ui::class,
        ChannelsEvent.Internal::class
    ) {

    private var subscribedStreams: List<ChannelsItem.StreamItem>? = null
    private var allStreams: List<ChannelsItem.StreamItem>? = null
    private var subscribedSearchQuery: String = ""
    private var allSearchQuery: String = ""

    override fun Result.internal(event: ChannelsEvent.Internal) = when (event) {

        is ChannelsEvent.Internal.SubscribedLoadingSuccess -> {
            subscribedStreams = event.streams
            state { copy(isSubscribedLoading = false, searchedSubscribedStreams = emptyList()) }
            commands { +ChannelsCommand.SearchSubscribedStreams(event.streams, subscribedSearchQuery) }
        }
        is ChannelsEvent.Internal.SubscribedSearchingSuccess -> {
            state { copy(isSubscribedLoading = false, searchedSubscribedStreams = event.streams) }
        }
        is ChannelsEvent.Internal.SubscribedLoadingError -> {
            state { copy(isSubscribedLoading = false) }
            effects { +ChannelsEffect.ShowLoadingError }
        }

        is ChannelsEvent.Internal.AllLoadingSuccess -> {
            allStreams = event.streams
            state { copy(isAllLoading = false, searchedAllStreams = emptyList()) }
            commands { +ChannelsCommand.SearchAllStreams(event.streams, allSearchQuery) }
        }
        is ChannelsEvent.Internal.AllSearchingSuccess -> {
            state { copy(isAllLoading = false, searchedAllStreams = event.streams) }
        }
        is ChannelsEvent.Internal.AllLoadingError -> {
            state { copy(isAllLoading = false) }
            effects { +ChannelsEffect.ShowLoadingError }
        }
    }

    override fun Result.ui(event: ChannelsEvent.Ui) = when (event) {
        is ChannelsEvent.Ui.Init -> {
            state { copy(isSubscribedLoading = true, isAllLoading = true) }
            commands { +ChannelsCommand.LoadSubscribedStreams }
            commands { +ChannelsCommand.LoadAllStreams }
        }
        is ChannelsEvent.Ui.SearchQueryChanged -> {
            when(event.position){
                ChannelsViewPagerAdapter.SUBSCRIBED_FRAGMENT_POSITION -> {
                    if (!state.isSubscribedLoading && subscribedStreams != null){
                        subscribedSearchQuery = event.searchQuery
                        state { copy(isSubscribedLoading = true, searchedSubscribedStreams = emptyList()) }
                        commands { +ChannelsCommand.SearchSubscribedStreams(subscribedStreams!!, event.searchQuery) }
                    } else {
                        state { copy() }
                    }
                }
                ChannelsViewPagerAdapter.ALL_STREAMS_FRAGMENT_POSITION -> {
                    if (!state.isAllLoading && allStreams != null){
                        allSearchQuery = event.searchQuery
                        state { copy(isAllLoading = true, searchedAllStreams = emptyList()) }
                        commands { +ChannelsCommand.SearchAllStreams(allStreams!!, event.searchQuery) }
                    } else {
                        state { copy() }
                    }
                }
                else -> {
                    throw IllegalArgumentException("ChannelsReducer: Tab position does not exist")
                }
            }
        }
        is ChannelsEvent.Ui.SearchButtonClicked -> {
            when(event.position){
                ChannelsViewPagerAdapter.SUBSCRIBED_FRAGMENT_POSITION -> {
                    if (!state.isSubscribedLoading && subscribedStreams != null){
                        subscribedSearchQuery = event.searchQuery
                        state { copy(isSubscribedLoading = true, searchedSubscribedStreams = emptyList()) }
                        commands { +ChannelsCommand.SearchSubscribedStreams(subscribedStreams!!, event.searchQuery) }
                    } else {
                        state { copy() }
                    }
                }
                ChannelsViewPagerAdapter.ALL_STREAMS_FRAGMENT_POSITION -> {
                    if (!state.isAllLoading && allStreams != null){
                        allSearchQuery = event.searchQuery
                        state { copy(isAllLoading = true, searchedAllStreams = emptyList()) }
                        commands { +ChannelsCommand.SearchAllStreams(allStreams!!, event.searchQuery) }
                    } else {
                        state { copy() }
                    }
                }
                else -> {
                    throw IllegalArgumentException("ChannelsReducer: Tab position does not exist")
                }
            }
        }
        is ChannelsEvent.Ui.TabChanged -> {
            when(event.newPosition){
                ChannelsViewPagerAdapter.SUBSCRIBED_FRAGMENT_POSITION -> {
                    if(!state.isSubscribedLoading){
                        if(event.searchQuery != subscribedSearchQuery && subscribedStreams != null){
                            subscribedSearchQuery = event.searchQuery
                            state { copy(isSubscribedLoading = true, searchedSubscribedStreams = emptyList()) }
                            commands { +ChannelsCommand.SearchSubscribedStreams(subscribedStreams!!, event.searchQuery) }
                        } else {
                            state{ copy() }
                        }
                    } else {
                        state{ copy() }
                    }
                }
                ChannelsViewPagerAdapter.ALL_STREAMS_FRAGMENT_POSITION -> {
                    if(!state.isAllLoading){
                        if(event.searchQuery != allSearchQuery && allStreams != null){
                            allSearchQuery = event.searchQuery
                            state{ copy(isAllLoading = true, searchedAllStreams = emptyList()) }
                            commands { +ChannelsCommand.SearchAllStreams(allStreams!!, event.searchQuery) }
                        } else {
                            state{ copy() }
                        }
                    } else {
                        state{ copy() }
                    }
                }
                else -> {
                    throw IllegalArgumentException("Reducer: Tab position does not exist")
                }
            }
        }
        is ChannelsEvent.Ui.StreamSelected -> {
            effects { +ChannelsEffect.NavigateToStream(event.stream) }
        }
        is ChannelsEvent.Ui.TopicSelected -> {
            effects { +ChannelsEffect.NavigateToTopic(event.topic) }
        }
    }


}