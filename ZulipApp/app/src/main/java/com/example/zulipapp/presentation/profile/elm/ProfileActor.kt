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

    override fun execute(command: ProfileCommand): Observable<ProfileEvent> {
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
                        ProfileEvent.Internal.ErrorLoading(it)
                    }
            }
        }
    }
}