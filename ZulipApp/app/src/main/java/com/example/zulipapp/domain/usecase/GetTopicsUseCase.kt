package com.example.zulipapp.domain.usecase

import com.example.zulipapp.domain.entity.Topic
import com.example.zulipapp.domain.repository.StreamRepository
import io.reactivex.Observable

interface GetTopicsUseCase : (Int) -> Observable<List<Topic>>

class GetTopicsUseCaseImpl(private val streamRepository: StreamRepository) : GetTopicsUseCase{

    override fun invoke(streamId: Int): Observable<List<Topic>> {
        return streamRepository.getTopicsByStreamId(streamId).toObservable()
    }

}