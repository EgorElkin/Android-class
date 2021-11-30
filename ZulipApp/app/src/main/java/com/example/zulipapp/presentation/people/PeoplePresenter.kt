package com.example.zulipapp.presentation.people

interface PeoplePresenter {

    fun viewIsReady()

    fun searchRequestChanged(searchQuery: String)

    fun userSelected(userId: Int)
}