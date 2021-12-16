package com.example.zulipapp.presentation.people.elm

import com.example.zulipapp.presentation.people.adapter.UserItem

sealed class PeopleCommand {

    object LoadPeople : PeopleCommand()
    class SearchPeople(val people: List<UserItem>, val searchQuery: String) : PeopleCommand()
}