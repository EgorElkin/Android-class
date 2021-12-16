package com.example.zulipapp.presentation.profile.elm

import com.example.zulipapp.presentation.profile.ProfileItem

sealed class ProfileEvent {

    sealed class Ui : ProfileEvent() {
        class Init(val userId: Int) : Ui()
    }

    sealed class Internal : ProfileEvent() {
        class ProfileLoaded(val value: ProfileItem) : Internal()
        class ErrorLoading(val error: Throwable) : Internal()
    }
}
