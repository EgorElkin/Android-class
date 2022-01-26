package com.example.zulipapp.presentation.people.elm

import com.example.zulipapp.domain.usecase.GetPeopleUseCase
import com.example.zulipapp.presentation.people.adapter.UserToItemMapper
import io.reactivex.Observable
import vivid.money.elmslie.core.ActorCompat

class PeopleActor(private val getPeopleUseCase: GetPeopleUseCase) : ActorCompat<PeopleCommand, PeopleEvent> {

    override fun execute(command: PeopleCommand): Observable<PeopleEvent> {
        return when (command) {
            is PeopleCommand.LoadPeople -> {
                getPeopleUseCase()
                    .map(UserToItemMapper())
//                    .doOnNext {
//                        println("debug: People: NEXT: ${it.size}")
//                    }
                    .map {
//                        allPeople = it
                        if (it.isEmpty()){

                            PeopleEvent.Internal.LoadingError
                        }else{
                            PeopleEvent.Internal.PeopleLoadingSuccess(it) as PeopleEvent
                        }
                    }
                    .doOnComplete {
                        println("debug: People: COMPLETE")
                    }
                    .doOnError {
                        println("debug: People: ERROR: $it")
//                        PeopleEvent.Internal.LoadingError
                    }
            }
            is PeopleCommand.SearchPeople -> {
                val newList = command.people
                    .filter {
                        it.userName.contains(command.searchQuery, ignoreCase = true)
                    }
                Observable.just(PeopleEvent.Internal.PeopleSearchingSuccess(newList))
            }
        }
    }

}