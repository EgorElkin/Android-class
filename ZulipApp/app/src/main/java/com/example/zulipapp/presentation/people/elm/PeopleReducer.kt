package com.example.zulipapp.presentation.people.elm

import com.example.zulipapp.presentation.people.adapter.UserItem
import vivid.money.elmslie.core.store.dsl_reducer.ScreenDslReducer

class PeopleReducer :
    ScreenDslReducer<PeopleEvent, PeopleEvent.Ui, PeopleEvent.Internal, PeopleState, PeopleEffect, PeopleCommand>(
        PeopleEvent.Ui::class,
        PeopleEvent.Internal::class
) {

    private var allPeople: List<UserItem> = emptyList()

    override fun Result.internal(event: PeopleEvent.Internal) = when(event) {
        is PeopleEvent.Internal.PeopleLoadingSuccess -> {
            println("debug: Reducer: Internal success")
            if(event.users.isEmpty()){
                state{ copy(isLoading = false, emptyList = true, emptySearchResult = false) }
            } else {
                allPeople = event.users
                state{ copy(isLoading = false, emptyList = false, emptySearchResult = false) }
                commands { +PeopleCommand.SearchPeople(allPeople, ALL_USERS_QUERY) }
            }
        }
        is PeopleEvent.Internal.LoadingError -> {
            println("debug: Reducer: Internal error")
            state { copy(isLoading = false) }
            effects { +PeopleEffect.ShowLoadingError }
        }
        is PeopleEvent.Internal.PeopleSearchingSuccess -> {
            if(event.users.isEmpty()){
                state { copy(isLoading = false, searchedUsers = event.users, emptyList = false, emptySearchResult = true) }
            } else {
                state { copy(isLoading = false, searchedUsers = event.users, emptyList = false, emptySearchResult = false) }
            }
        }
    }

    override fun Result.ui(event: PeopleEvent.Ui) = when(event) {
        is PeopleEvent.Ui.Init -> {
            state {
                copy(
                    isLoading = true,
//                    searchedUsers = emptyList(),
//                    emptyList = false,
//                    emptySearchResult = false
                )
            }
            commands { +PeopleCommand.LoadPeople }
        }
        is PeopleEvent.Ui.SearchPeople -> {
            if (!state.isLoading && allPeople.isNotEmpty()) {
                state { copy(isLoading = true, searchedUsers = emptyList()) }
                commands { +PeopleCommand.SearchPeople(allPeople, event.searchQuery) }
            } else {
                state { copy() }
            }
        }
        is PeopleEvent.Ui.UserSelected -> {
            effects { +PeopleEffect.NavigateToProfile(event.userId) }
        }
    }

    companion object{
        private const val ALL_USERS_QUERY = ""
    }
}