package com.example.zulipapp.domain.usecase

import com.example.zulipapp.domain.entity.Stream
import com.example.zulipapp.domain.repository.StreamRepository
import io.reactivex.Observable

interface GetAllStreamsUseCase : () -> Observable<List<Stream>>

class GetAllStreamsUseCaseImpl(private val streamRepository: StreamRepository) : GetAllStreamsUseCase{

    override fun invoke(): Observable<List<Stream>> {
        return streamRepository.getAllStreams().toObservable()
    }

}