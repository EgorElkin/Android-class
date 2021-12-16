package com.example.zulipapp.presentation.people.elm

import com.example.zulipapp.presentation.people.adapter.UserItem

sealed class PeopleEvent {

    sealed class Ui : PeopleEvent() {
        object Init : Ui()
        class SearchPeople(val searchQuery: String) : Ui()
        class UserSelected(val userId: Int) : Ui()
    }

    sealed class Internal : PeopleEvent() {
        class PeopleLoadingSuccess(val users: List<UserItem>) : Internal()
        class PeopleSearchingSuccess(val users: List<UserItem>) : Internal()
        class LoadingError(val error: Throwable) : Internal()
    }
}