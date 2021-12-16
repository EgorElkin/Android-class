package com.example.zulipapp.presentation.profile.elm

import vivid.money.elmslie.core.store.dsl_reducer.ScreenDslReducer

class ProfileReducer :
    ScreenDslReducer<ProfileEvent, ProfileEvent.Ui, ProfileEvent.Internal, ProfileState, ProfileEffect, ProfileCommand>(
        ProfileEvent.Ui::class,
        ProfileEvent.Internal::class
    ) {

    init {
        println("debug: Reducer: INIT")
    }

    override fun Result.internal(event: ProfileEvent.Internal): Any? = when (event) {
        is ProfileEvent.Internal.ProfileLoaded -> {
            state { copy(isLoading = false, profile = event.value) }
        }
        is ProfileEvent.Internal.ErrorLoading -> {
            state { copy(isLoading = false, profile = null) }
            effects { +ProfileEffect.ShowError }
        }
    }

    override fun Result.ui(event: ProfileEvent.Ui) = when (event) {
        is ProfileEvent.Ui.Init -> {
            state { copy(isLoading = true, profile = null) }
            commands { +ProfileCommand.LoadProfile(userId = event.userId) }
        }
    }
}