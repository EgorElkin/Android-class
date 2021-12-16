package com.example.zulipapp.presentation.people.elm

import com.example.zulipapp.presentation.people.adapter.UserItem

data class PeopleState(
    val isLoading: Boolean = false,
    val allUsers: List<UserItem>? = null,
    val searchedUsers: List<UserItem>? = null,
    val emptyList: Boolean = false,
    val emptySearchResult: Boolean = false
)
