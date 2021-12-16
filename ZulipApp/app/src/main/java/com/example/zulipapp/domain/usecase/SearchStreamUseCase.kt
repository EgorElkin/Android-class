package com.example.zulipapp.domain.usecase

import com.example.zulipapp.domain.entity.Stream
import com.example.zulipapp.domain.repository.StreamRepository
import io.reactivex.Observable

interface SearchStreamUseCase : (String) -> Observable<List<Stream>>

class SearchAllStreamUseCaseImpl(private val streamRepository: StreamRepository) : SearchStreamUseCase{

    override fun invoke(searchQuery: String): Observable<List<Stream>> {
        return streamRepository.getAllStreams().map { list ->
            list.filter { stream ->
                stream.name.contains(searchQuery, ignoreCase = true)
            }
        }.toObservable()
    }
}

class SearchSubscribedStreamUseCaseImpl(private val streamRepository: StreamRepository) : SearchStreamUseCase{

    override fun invoke(searchQuery: String): Observable<List<Stream>> {
        return streamRepository.getSubscribedStreams().map { list ->
            list.filter { stream ->
                stream.name.contains(searchQuery, ignoreCase = true)
            }
        }.toObservable()
    }
}