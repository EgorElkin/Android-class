package com.example.zulipapp.domain.usecase

import com.example.zulipapp.domain.entity.Stream
import com.example.zulipapp.domain.repository.StreamRepository
import io.reactivex.Observable

interface GetSubscribedStreamsUseCase : () -> Observable<List<Stream>>

class GetSubscribedStreamsUseCaseImpl(private val streamRepository: StreamRepository) : GetSubscribedStreamsUseCase{

    override fun invoke(): Observable<List<Stream>> {
        return streamRepository.getSubscribedStreams().toObservable()
    }

}