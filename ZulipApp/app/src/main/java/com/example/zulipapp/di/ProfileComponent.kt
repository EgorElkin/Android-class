package com.example.zulipapp.di

import com.example.zulipapp.presentation.profile.elm.ProfileCommand
import com.example.zulipapp.presentation.profile.elm.ProfileEffect
import com.example.zulipapp.presentation.profile.elm.ProfileEvent
import com.example.zulipapp.presentation.profile.elm.ProfileState
import dagger.Component
import vivid.money.elmslie.core.ElmStoreCompat

@FragmentScope
@Component(dependencies = [ActivityComponent::class], modules = [ProfileModule::class])
interface ProfileComponent {

    val profileStore: ElmStoreCompat<ProfileEvent, ProfileState, ProfileEffect, ProfileCommand>

    @Component.Factory
    interface Factory{
        fun create(activityComponent: ActivityComponent): ProfileComponent
    }
}