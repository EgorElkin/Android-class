package com.example.zulipapp.presentation.people.elm

sealed class PeopleEffect{

    object ShowLoadingError : PeopleEffect()
    class NavigateToProfile(val userId: Int) : PeopleEffect()
}