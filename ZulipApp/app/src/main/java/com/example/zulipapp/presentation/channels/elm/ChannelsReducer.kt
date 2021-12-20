package com.example.zulipapp.presentation.channels.elm

import com.example.zulipapp.presentation.channels.ChannelsViewPagerAdapter
import com.example.zulipapp.presentation.channels.adapter.ChannelsItem
import io.reactivex.subjects.PublishSubject
import vivid.money.elmslie.core.store.dsl_reducer.ScreenDslReducer
import java.util.concurrent.TimeUnit

class ChannelsReducer :
    ScreenDslReducer<ChannelsEvent, ChannelsEvent.Ui, ChannelsEvent.Internal, ChannelsState, ChannelsEffect, ChannelsCommand>(
        ChannelsEvent.Ui::class,
        ChannelsEvent.Internal::class
    ) {

    private var subscribedStreams: List<ChannelsItem.StreamItem>? = null
    private var searchedSubscribedStreams: List<ChannelsItem.StreamItem>? = null
    private var allStreams: List<ChannelsItem.StreamItem>? = null
    private var searchedAllStreams: List<ChannelsItem.StreamItem>? = null
    private var subscribedSearchQuery: String = ""
    private var allSearchQuery: String = ""

    private val subscribedSubject: PublishSubject<String> = PublishSubject.create()
    private val allSubject: PublishSubject<String> = PublishSubject.create()

    override fun Result.internal(event: ChannelsEvent.Internal) = when (event) {
        is ChannelsEvent.Internal.SubscribedLoadingSuccess -> {
            println("debug: Reducer: Internal.subscribedSuccess")
            subscribedStreams = event.streams
            state { copy(isSubscribedLoading = false, searchedSubscribedStreams = event.streams, isNewSubscribed = true, isNewAll = false) }
        }
        is ChannelsEvent.Internal.SubscribedSearchingSuccess -> {
            println("debug: Reducer: Internal.subscribedSearch")
            searchedSubscribedStreams = event.streams
            state { copy(isSubscribedLoading = false, searchedSubscribedStreams = event.streams, isNewSubscribed = true, isNewAll = false) }
        }
        is ChannelsEvent.Internal.SubscribedLoadingError -> {
            println("debug: Reducer: Internal.subscribedError")
            state { copy(isSubscribedLoading = false) }
            effects { +ChannelsEffect.ShowLoadingError }
        }
        is ChannelsEvent.Internal.AllLoadingSuccess -> {
            println("debug: Reducer: Internal.allSuccess")
            allStreams = event.streams
            state { copy(isAllLoading = false, searchedAllStreams = event.streams, isNewSubscribed = false, isNewAll = true) }
        }
        is ChannelsEvent.Internal.AllSearchingSuccess -> {
            println("debug: Reducer: Internal.allSearch")
            searchedAllStreams = event.streams
            state { copy(isAllLoading = false, searchedAllStreams = event.streams, isNewSubscribed = false, isNewAll = true) }
        }
        is ChannelsEvent.Internal.AllLoadingError -> {
            println("debug: Reducer: Internal.allError")
            state { copy(isAllLoading = false) }
            effects { +ChannelsEffect.ShowLoadingError }
        }
    }

    override fun Result.ui(event: ChannelsEvent.Ui) = when (event) {
        is ChannelsEvent.Ui.Init -> {
            println("debug: Reducer: Ui.Init()")
            state { copy(isSubscribedLoading = true, isAllLoading = true, isNewSubscribed = false, isNewAll = false) }
            if(subscribedStreams == null)
                commands { +ChannelsCommand.LoadSubscribedStreams }
            if(allStreams == null){
                commands { +ChannelsCommand.LoadAllStreams }
            } else {

            }
        }
        is ChannelsEvent.Ui.SearchButtonClicked -> {
            println("debug: Reducer: Ui.Button()")
            when(state.currentTab){
                ChannelsViewPagerAdapter.SUBSCRIBED_FRAGMENT_POSITION -> {
                    if (!state.isSubscribedLoading && subscribedStreams != null){
                        subscribedSearchQuery = event.searchQuery
                        state { copy(isSubscribedLoading = true, searchedSubscribedStreams = emptyList(), isNewSubscribed = false, currentSubscribedSearchQuery = event.searchQuery) }
                        commands { +ChannelsCommand.SearchSubscribedStreams(subscribedStreams!!, event.searchQuery) }
                    } else {
                        state { copy() }
                    }
                }
                ChannelsViewPagerAdapter.ALL_STREAMS_FRAGMENT_POSITION -> {
                    if (!state.isAllLoading && allStreams != null){
                        allSearchQuery = event.searchQuery
                        state { copy(isAllLoading = true, searchedAllStreams = emptyList(), isNewAll = false, currentAllSearchQuery = event.searchQuery) }
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
        is ChannelsEvent.Ui.SearchQueryChanged -> {
            println("debug: Reducer: Ui.EditText()")
            when(state.currentTab){
                ChannelsViewPagerAdapter.SUBSCRIBED_FRAGMENT_POSITION -> {
                    if (!state.isSubscribedLoading && subscribedStreams != null){
                        subscribedSearchQuery = event.searchQuery
                        state { copy(isSubscribedLoading = true, searchedSubscribedStreams = emptyList(), isNewSubscribed = false, currentSubscribedSearchQuery = event.searchQuery) }
                        commands { +ChannelsCommand.SearchSubscribedStreams(subscribedStreams!!, event.searchQuery) }
                    } else {
                        state { copy() }
                    }
                }
                ChannelsViewPagerAdapter.ALL_STREAMS_FRAGMENT_POSITION -> {
                    if (!state.isAllLoading && allStreams != null){
                        allSearchQuery = event.searchQuery
                        state { copy(isAllLoading = true, searchedAllStreams = emptyList(), isNewAll = false, currentAllSearchQuery = event.searchQuery) }
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
//            if(event.searchQuery != state.currentSearchQuery)

//            println("debug: Reducer: Ui.TabChanged()")
            when(event.newPosition){
                ChannelsViewPagerAdapter.SUBSCRIBED_FRAGMENT_POSITION -> {
                    if(!state.isSubscribedLoading){
                        if(event.searchQuery != subscribedSearchQuery && subscribedStreams != null){
                            println("debug: Reducer: Ui.TabChanged() SUB: IF IF")
                            subscribedSearchQuery = event.searchQuery
                            state { copy(isSubscribedLoading = true, searchedSubscribedStreams = emptyList(), isNewSubscribed = false, isNewAll = false) }
                            commands { +ChannelsCommand.SearchSubscribedStreams(subscribedStreams!!, event.searchQuery) }
                        } else {
                            println("debug: Reducer: Ui.TabChanged() SUB: IF ELSE")
                            state{ copy(isNewSubscribed = false, isNewAll = false) }
                        }
                    } else {
                        println("debug: Reducer: Ui.TabChanged() SUB: ELSE")
                    }
//                    if (event.searchQuery != subscribedSearchQuery && !state.isSubscribedLoading && subscribedStreams != null){
//                        println("debug: Reducer: Ui.TabChanged() IF")
//                        subscribedSearchQuery = event.searchQuery
//                        state { copy(isSubscribedLoading = true, searchedSubscribedStreams = emptyList(), currentSubscribedSearchQuery = event.searchQuery) }
//                        commands { +ChannelsCommand.SearchSubscribedStreams(subscribedStreams!!, event.searchQuery) }
//                    } else {
//                        println("debug: Reducer: Ui.TabChanged() ELSE")
//                        state { copy() }
//                    }
                }
                ChannelsViewPagerAdapter.ALL_STREAMS_FRAGMENT_POSITION -> {
                    if(!state.isAllLoading){
                        if(event.searchQuery != allSearchQuery && allStreams != null){
                            println("debug: Reducer: Ui.TabChanged() ALL: IF IF")
                            allSearchQuery = event.searchQuery
                            state{ copy(isAllLoading = true, searchedAllStreams = emptyList(), isNewSubscribed = false, isNewAll = false) }
                            commands { +ChannelsCommand.SearchAllStreams(allStreams!!, event.searchQuery) }
                        } else {
                            println("debug: Reducer: Ui.TabChanged() ALL: IF ELSE")
                            state{ copy(isNewSubscribed = false, isNewAll = false) }
                        }
                    } else {
                        println("debug: Reducer: Ui.TabChanged() ALL: ELSE")
                    }
//                    if (event.searchQuery != allSearchQuery && !state.isAllLoading && allStreams != null){
//                        println("debug: Reducer: Ui.TabChanged() IF")
//                        allSearchQuery = event.searchQuery
//                        state { copy(isAllLoading = true, searchedAllStreams = emptyList(), currentAllSearchQuery = event.searchQuery) }
//                        commands { +ChannelsCommand.SearchAllStreams(allStreams!!, event.searchQuery) }
//                    } else {
//                        println("debug: Reducer: Ui.TabChanged() ELSE")
//                        state { copy() }
//                    }
                }
                else -> {
                    throw IllegalArgumentException("ChannelsReducer: Tab position does not exist")
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