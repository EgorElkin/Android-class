package com.example.zulipapp.presentation.profile.elm

sealed class ProfileCommand {

    class LoadProfile(val userId: Int): ProfileCommand()
}