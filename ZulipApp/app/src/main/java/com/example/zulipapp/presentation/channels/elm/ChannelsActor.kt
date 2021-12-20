package com.example.zulipapp.presentation.channels.elm

import com.example.zulipapp.domain.usecase.GetAllStreamsUseCase
import com.example.zulipapp.domain.usecase.GetSubscribedStreamsUseCase
import com.example.zulipapp.domain.usecase.GetTopicsUseCase
import com.example.zulipapp.presentation.channels.adapter.StreamToItemMapper
import com.example.zulipapp.presentation.channels.adapter.TopicToItemMapper
import io.reactivex.Observable
import vivid.money.elmslie.core.ActorCompat

class ChannelsActor(
    private val getSubscribedStreamsUseCase: GetSubscribedStreamsUseCase,
    private val getAllStreamsUseCase: GetAllStreamsUseCase,
    private val getTopicsUseCase: GetTopicsUseCase
) : ActorCompat<ChannelsCommand, ChannelsEvent> {

    override fun execute(command: ChannelsCommand): Observable<ChannelsEvent> {
        println("debug: ChannelsActor: execute()")
        when (command) {
            is ChannelsCommand.LoadSubscribedStreams -> {
                println("debug: ChannelsActor: loadSubscribed")
                return getSubscribedStreamsUseCase()
                    .map(StreamToItemMapper())
                    .flatMap { streams ->
                        Observable.fromIterable(streams)
                    }
                    .flatMap({ stream ->
//                        println("debug: stream -> topics: ${stream.name}")
                        getTopicsUseCase(stream.id).map(TopicToItemMapper())
                    }, { stream, topics ->
                        topics.map { it.streamName = stream.name }
                        stream.topics = topics
                        stream
                    })
                    .toList()
                    .map {
                        ChannelsEvent.Internal.SubscribedLoadingSuccess(it) as ChannelsEvent
                    }
                    .doOnSuccess {
                        println("debug: ChannelsActor: SUCCESS Sub: EventSize=${(it as ChannelsEvent.Internal.SubscribedLoadingSuccess).streams.size}")
                    }
                    .doOnError {
                        println("debug: ChannelsActor: SUB Error=$it")
                        ChannelsEvent.Internal.SubscribedLoadingError(it) as ChannelsEvent
                    }
                    .toObservable()
            }
            is ChannelsCommand.SearchSubscribedStreams -> {
                println("debug: ChannelsActor: searchSubscribed")
                val newList = command.streams
                    .filter { stream ->
                        stream.name.contains(command.searchQuery, ignoreCase = true) ||
                                stream.topics.any { topic ->
                                    topic.name.contains(command.searchQuery, ignoreCase = true)
                                }
                    }
                return Observable.just(ChannelsEvent.Internal.SubscribedSearchingSuccess(newList))
            }
            is ChannelsCommand.LoadAllStreams -> {
                println("debug: ChannelsActor: loadAll")
                return getAllStreamsUseCase()
                    .map(StreamToItemMapper())
                    .flatMap { streams ->
                        Observable.fromIterable(streams)
                    }
                    .flatMap({ stream ->
//                        println("debug: stream -> topics: ${stream.name}")
                        getTopicsUseCase(stream.id).map(TopicToItemMapper())
                    }, { stream, topics ->
                        topics.map { it.streamName = stream.name }
                        stream.topics = topics
                        stream
                    })
                    .toList()
                    .map {
                        ChannelsEvent.Internal.AllLoadingSuccess(it) as ChannelsEvent
                    }
                    .doOnSuccess {
                        println("debug: ChannelsActor: SUCCESS All: EventSize=${(it as ChannelsEvent.Internal.AllLoadingSuccess).streams.size}")
                    }
                    .doOnError {
                        println("debug: ChannelsActor: ALL Error=$it")
                        ChannelsEvent.Internal.AllLoadingError(it) as ChannelsEvent
                    }
                    .toObservable()
            }
            is ChannelsCommand.SearchAllStreams -> {
                println("debug: ChannelsActor: searchAll")
                val newList = command.streams
                    .filter { stream ->
                        stream.name.contains(command.searchQuery, ignoreCase = true) ||
                                stream.topics.any { topic ->
                                    topic.name.contains(command.searchQuery, ignoreCase = true)
                                }
                    }
                return Observable.just(ChannelsEvent.Internal.AllSearchingSuccess(newList))
            }
        }
    }
}