package com.example.zulipapp.presentation.channels.elm

import vivid.money.elmslie.core.store.dsl_reducer.ScreenDslReducer

class ChannelsReducer :
    ScreenDslReducer<ChannelsEvent, ChannelsEvent.Ui, ChannelsEvent.Internal, ChannelsState, ChannelsEffect, ChannelsCommand>(
        ChannelsEvent.Ui::class, ChannelsEvent.Internal::class
    ){

    override fun Result.internal(event: ChannelsEvent.Internal): Any {
        return when(event) {
            is ChannelsEvent.Internal.SubscribedLoadingSuccess -> {
                state { copy(subscribedFullList = event.streams) }
                commands { +ChannelsCommand.SearchSubscribedStreams(event.streams, INITIAL_SEARCH) } // just start searching
            }
            is ChannelsEvent.Internal.SubscribedLoadingError -> {
                effects { +ChannelsEffect.ShowLoadingError }
                state { copy(subscribedIsLoading = false) }
            }
            is ChannelsEvent.Internal.SubscribedSearchSuccess -> {
                state { copy(
                    subscribedIsLoading = false,
                    subscribedSearchQuery = event.searchQuery,
                    subscribedSearchedList = event.streams
                ) }
            }

            is ChannelsEvent.Internal.AllLoadingSuccess -> {
                state { copy(allFullList = event.streams) }
                commands { +ChannelsCommand.SearchAllStreams(event.streams, INITIAL_SEARCH) } // just start searching
            }
            is ChannelsEvent.Internal.AllLoadingError -> {
                effects { +ChannelsEffect.ShowLoadingError }
                state { copy(allIsLoading = false) }
            }
            is ChannelsEvent.Internal.AllSearchSuccess -> {
                state { copy(
                    allIsLoading = false,
                    allSearchQuery = event.searchQuery,
                    allSearchedList = event.streams
                ) }
            }
        }
    }

    override fun Result.ui(event: ChannelsEvent.Ui): Any {
        return when(event){
            is ChannelsEvent.Ui.Init -> {
                state { copy(subscribedIsLoading = true, allIsLoading = true) }
                commands { +ChannelsCommand.LoadSubscribedStreams }
                commands { +ChannelsCommand.LoadAllStreams }
            }

            is ChannelsEvent.Ui.SearchQueryChanged -> {
                when(event.position){
                    0 -> {
                        if(!state.subscribedIsLoading && !state.subscribedFullList.isNullOrEmpty()){
                            commands { +ChannelsCommand.SearchSubscribedStreams(state.subscribedFullList!!, event.searchQuery) }
                        } else {
                            Any()
                        }
                    }
                    1 -> {
                        if(!state.allIsLoading && !state.allFullList.isNullOrEmpty()){
                            commands { +ChannelsCommand.SearchAllStreams(state.allFullList!!, event.searchQuery) }
                        } else {
                            Any()
                        }
                    }
                    else -> {
                        Any()
                    }
                }
            }

            is ChannelsEvent.Ui.SearchButtonClicked -> {
                when(event.position){
                    0 -> {
                        if(!state.subscribedIsLoading && !state.subscribedFullList.isNullOrEmpty() && state.subscribedSearchQuery != event.searchQuery){
                            commands { +ChannelsCommand.SearchSubscribedStreams(state.subscribedFullList!!, event.searchQuery) }
                        } else {
                            Any()
                        }
                    }
                    1 -> {
                        if(!state.allIsLoading && !state.allFullList.isNullOrEmpty() && state.allSearchQuery != event.searchQuery){
                            commands { +ChannelsCommand.SearchAllStreams(state.allFullList!!, event.searchQuery) }
                        } else {
                            Any()
                        }
                    }
                    else -> {
                        Any()
                    }
                }
            }

            is ChannelsEvent.Ui.TabChanged -> {
                if(state.currentPosition != event.newPosition){
                    when(event.newPosition){
                        0 -> {
                            if(!state.subscribedIsLoading && !state.subscribedFullList.isNullOrEmpty()){
                                if(event.searchQuery != state.subscribedSearchQuery){
                                    commands { +ChannelsCommand.SearchSubscribedStreams(state.subscribedFullList!!, event.searchQuery) }
                                } else {
                                    Any()
                                }
                            } else {
                                Any()
                            }
                            state { copy(currentPosition = event.newPosition) }
                        }
                        1 -> {
                            if(state.allIsLoading){
                                Any()
                            } else {
                                if(event.searchQuery != state.allSearchQuery){
                                    commands { +ChannelsCommand.SearchAllStreams(state.allFullList!!, event.searchQuery) }
                                } else {
                                    Any()
                                }
                            }
                            state { copy(currentPosition = event.newPosition) }
                        }
                        else -> {
                            Any()
                        }
                    }
                } else {
                    Any()
                }
            }

            is ChannelsEvent.Ui.TopicSelected -> {
                effects { +ChannelsEffect.NavigateToTopic(event.topic) }
            }

            is ChannelsEvent.Ui.StreamSelected -> {
                effects { +ChannelsEffect.NavigateToStream(event.stream) }
            }
        }
    }

    companion object{
        const val INITIAL_SEARCH = ""
    }
}