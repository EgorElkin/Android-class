package com.example.zulipapp.presentation.profile.elm

import com.example.zulipapp.domain.usecase.GetUserByIdUseCase
import com.example.zulipapp.domain.usecase.GetUserStatusUseCase
import com.example.zulipapp.presentation.profile.UserToProfileItemMapper
import io.reactivex.Observable
import vivid.money.elmslie.core.ActorCompat

class ProfileActor(
    private val getUserUseCase: GetUserByIdUseCase,
    private val getUserPresenceUseCase: GetUserStatusUseCase
) : ActorCompat<ProfileCommand, ProfileEvent> {

    init {
        println("debug: Actor: INIT")
    }

    init {
        println("debug: Actor: useCase1=$getUserUseCase, useCase2=$getUserPresenceUseCase")
    }

    override fun execute(command: ProfileCommand): Observable<ProfileEvent> {
        println("debug: Actor: execute")
        return when(command){
            is ProfileCommand.LoadProfile -> {
                getUserUseCase(command.userId)
                    .toObservable()
                    .map(UserToProfileItemMapper())
                    .flatMap({ profile ->
                        getUserPresenceUseCase(profile.id).toObservable()
                    },{ profile, status ->
                        profile.status = status.status
                        ProfileEvent.Internal.ProfileLoaded(profile) as ProfileEvent
                    }).doOnError {
                        ProfileEvent.Internal.ErrorLoading(it) as ProfileEvent
                    }
            }
        }
    }
}