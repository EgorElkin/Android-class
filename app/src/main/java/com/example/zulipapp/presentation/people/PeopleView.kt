package com.example.zulipapp.presentation.people

import com.example.zulipapp.presentation.base.MvpView
import com.example.zulipapp.presentation.people.adapter.UserItem

interface PeopleView : MvpView {

    fun showUsers(users: List<UserItem>)

    fun showLoading()

    fun hideLoading()

    fun showError(message: Int)
}