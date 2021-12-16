package com.example.zulipapp.presentation.people.elm

import vivid.money.elmslie.core.store.dsl_reducer.ScreenDslReducer

class PeopleReducer :
    ScreenDslReducer<PeopleEvent, PeopleEvent.Ui, PeopleEvent.Internal, PeopleState, PeopleEffect, PeopleCommand>(
        PeopleEvent.Ui::class,
        PeopleEvent.Internal::class
) {

    override fun Result.internal(event: PeopleEvent.Internal): Any? = when(event) {
        is PeopleEvent.Internal.PeopleLoadingSuccess -> {
            if(event.users.isEmpty()){
                state{ copy(isLoading = false, allUsers = event.users, emptyList = true, emptySearchResult = false) }
            } else {
                state{ copy(isLoading = false, allUsers = event.users, searchedUsers = event.users, emptyList = false, emptySearchResult = false) }
            }
        }
        is PeopleEvent.Internal.PeopleSearchingSuccess -> {
            if(event.users.isEmpty()){
                state { copy(isLoading = false, searchedUsers = event.users, emptyList = false, emptySearchResult = true) }
            } else {
                state { copy(isLoading = false, searchedUsers = event.users, emptyList = false, emptySearchResult = false) }
            }
        }
        is PeopleEvent.Internal.LoadingError -> {
            state { copy(isLoading = false) }
            effects { +PeopleEffect.ShowLoadingError }
        }
    }

    override fun Result.ui(event: PeopleEvent.Ui): Any = when(event) {
        is PeopleEvent.Ui.Init -> {
            state {
                copy(
                    isLoading = true,
                    allUsers = emptyList(),
                    searchedUsers = null,
                    emptyList = false,
                    emptySearchResult = false
                )
            }
            commands { +PeopleCommand.LoadPeople }
        }
        is PeopleEvent.Ui.SearchPeople -> {
            if (!state.isLoading && !state.allUsers.isNullOrEmpty()) {
                state { copy(isLoading = true, searchedUsers = emptyList()) }
                commands { +PeopleCommand.SearchPeople(state.allUsers!!, event.searchQuery) }
            } else {

            }
        }
        is PeopleEvent.Ui.UserSelected -> {
            effects { +PeopleEffect.NavigateToProfile(event.userId) }
        }
    }
}