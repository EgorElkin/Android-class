package com.example.zulipapp.di

import com.example.zulipapp.domain.repository.UserRepository
import com.example.zulipapp.domain.usecase.GetPeopleUseCase
import com.example.zulipapp.domain.usecase.GetPeopleUseCaseImpl
import com.example.zulipapp.presentation.people.elm.*
import dagger.Module
import dagger.Provides
import vivid.money.elmslie.core.ElmStoreCompat

@Module
class PeopleModule {

    @Provides
    @FragmentScope
    fun provideGetPeopleUseCase(userRepository: UserRepository): GetPeopleUseCase = GetPeopleUseCaseImpl(userRepository)

    @Provides
    @FragmentScope
    fun providePeopleState(): PeopleState {
        return PeopleState()
    }

    @Provides
    @FragmentScope
    fun providePeopleActor(getPeopleUseCase: GetPeopleUseCase): PeopleActor {
        return PeopleActor(getPeopleUseCase)
    }

    @Provides
    @FragmentScope
    fun providePeopleReducer(): PeopleReducer {
        return PeopleReducer()
    }

    @Provides
    @FragmentScope
    fun providePeopleStoreFactory(
        peopleState: PeopleState,
        peopleReducer: PeopleReducer,
        peopleActor: PeopleActor
    ): ElmStoreCompat<PeopleEvent, PeopleState, PeopleEffect, PeopleCommand> = ElmStoreCompat(
        initialState = peopleState,
        reducer = peopleReducer,
        actor = peopleActor
    )
}