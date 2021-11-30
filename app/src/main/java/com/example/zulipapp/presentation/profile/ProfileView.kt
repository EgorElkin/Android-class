package com.example.zulipapp.presentation.profile

import com.example.zulipapp.domain.entity.User
import com.example.zulipapp.presentation.base.MvpView

interface ProfileView : MvpView {

    fun showUser(profile: ProfileItem)

    fun showError(errorRes: Int)

    fun showLoading()

    fun hideLoading()
}
