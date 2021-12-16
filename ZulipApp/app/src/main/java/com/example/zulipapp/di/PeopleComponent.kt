package com.example.zulipapp.di

import com.example.zulipapp.presentation.people.elm.PeopleCommand
import com.example.zulipapp.presentation.people.elm.PeopleEffect
import com.example.zulipapp.presentation.people.elm.PeopleEvent
import com.example.zulipapp.presentation.people.elm.PeopleState
import dagger.Component
import vivid.money.elmslie.core.ElmStoreCompat

@FragmentScope
@Component(dependencies = [ActivityComponent::class], modules = [PeopleModule::class])
interface PeopleComponent {

    val peopleStore: ElmStoreCompat<PeopleEvent, PeopleState, PeopleEffect, PeopleCommand>

    @Component.Factory
    interface Factory{
        fun create(activityComponent: ActivityComponent): PeopleComponent
    }
}