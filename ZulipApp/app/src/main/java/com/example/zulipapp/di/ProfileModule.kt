package com.example.zulipapp.di

import com.example.zulipapp.domain.repository.UserRepository
import com.example.zulipapp.domain.usecase.GetUserByIdUseCase
import com.example.zulipapp.domain.usecase.GetUserByIdUseCaseImpl
import com.example.zulipapp.domain.usecase.GetUserStatusUseCase
import com.example.zulipapp.domain.usecase.GetUserStatusUseCaseImpl
import com.example.zulipapp.presentation.profile.elm.*
import dagger.Module
import dagger.Provides
import vivid.money.elmslie.core.ElmStoreCompat

@Module
class ProfileModule {

    @Provides
    @FragmentScope
    fun provideGetUserByIdUseCase(userRepository: UserRepository): GetUserByIdUseCase =
        GetUserByIdUseCaseImpl(userRepository)

    @Provides
    @FragmentScope
    fun provideGetUserStatusUseCase(userRepository: UserRepository): GetUserStatusUseCase =
        GetUserStatusUseCaseImpl(userRepository)

    @Provides
    @FragmentScope
    fun provideProfileState(): ProfileState {
        return ProfileState()
    }

    @Provides
    @FragmentScope
    fun provideProfileActor(getUserUseCase: GetUserByIdUseCase, getUserPresenceUseCase: GetUserStatusUseCase): ProfileActor {
        return ProfileActor(getUserUseCase, getUserPresenceUseCase)
    }

    @Provides
    @FragmentScope
    fun provideProfileReducer(): ProfileReducer {
        return ProfileReducer()
    }

    @Provides
    @FragmentScope
    fun provideProfileStoreFactory(
        profileState: ProfileState,
        profileReducer: ProfileReducer,
        profileActor: ProfileActor
    ): ElmStoreCompat<ProfileEvent, ProfileState, ProfileEffect, ProfileCommand> = ElmStoreCompat(
        initialState = profileState,
        reducer = profileReducer,
        actor = profileActor
    )
}