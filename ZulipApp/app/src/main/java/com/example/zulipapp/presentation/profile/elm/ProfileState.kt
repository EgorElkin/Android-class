package com.example.zulipapp.presentation.profile.elm

import com.example.zulipapp.presentation.profile.ProfileItem

data class ProfileState(
    val isLoading: Boolean = false,
    val profile: ProfileItem? = null
)