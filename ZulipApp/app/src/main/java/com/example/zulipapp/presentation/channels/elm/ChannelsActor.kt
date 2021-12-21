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
        when (command) {
            is ChannelsCommand.LoadSubscribedStreams -> {
                return getSubscribedStreamsUseCase()
                    .map(StreamToItemMapper())
                    .flatMap { streams ->
                        Observable.fromIterable(streams)
                    }
                    .flatMap({ stream ->
                        getTopicsUseCase(stream.id).map(TopicToItemMapper())
                    }, { stream, topics ->
                        topics.map { it.streamName = stream.name }
                        stream.topics = topics
                        stream
                    })
                    .toList()
                    .map { ChannelsEvent.Internal.SubscribedLoadingSuccess(it) as ChannelsEvent }
                    .doOnError { ChannelsEvent.Internal.SubscribedLoadingError(it) }
                    .toObservable()
            }
            is ChannelsCommand.SearchSubscribedStreams -> {
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
                return getAllStreamsUseCase()
                    .map(StreamToItemMapper())
                    .flatMap { streams ->
                        Observable.fromIterable(streams)
                    }
                    .flatMap({ stream ->
                        getTopicsUseCase(stream.id).map(TopicToItemMapper())
                    }, { stream, topics ->
                        topics.map { it.streamName = stream.name }
                        stream.topics = topics
                        stream
                    })
                    .toList()
                    .map { ChannelsEvent.Internal.AllLoadingSuccess(it) as ChannelsEvent }
                    .doOnError { ChannelsEvent.Internal.AllLoadingError(it) }
                    .toObservable()
            }
            is ChannelsCommand.SearchAllStreams -> {
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