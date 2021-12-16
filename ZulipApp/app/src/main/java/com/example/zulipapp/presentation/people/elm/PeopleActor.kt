package com.example.zulipapp.presentation.people.elm

import com.example.zulipapp.domain.usecase.GetPeopleUseCase
import com.example.zulipapp.presentation.people.adapter.UserItem
import com.example.zulipapp.presentation.people.adapter.UserToItemMapper
import io.reactivex.Observable
import vivid.money.elmslie.core.ActorCompat

class PeopleActor(private val getPeopleUseCase: GetPeopleUseCase) : ActorCompat<PeopleCommand, PeopleEvent> {

    private var allPeople: List<UserItem> = emptyList()

    override fun execute(command: PeopleCommand): Observable<PeopleEvent> {
        return when (command) {
            is PeopleCommand.LoadPeople -> {
                getPeopleUseCase()
                    .map(UserToItemMapper())
                    .map {
                        allPeople = it
                        PeopleEvent.Internal.PeopleLoadingSuccess(it) as PeopleEvent
                    }
                    .doOnError {
                        PeopleEvent.Internal.LoadingError(it) as PeopleEvent
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