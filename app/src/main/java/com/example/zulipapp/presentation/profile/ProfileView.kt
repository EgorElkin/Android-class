package com.example.zulipapp.presentation.profile

interface ProfileView {

    fun showUser(userName: String)

    fun showError(message: String)
}