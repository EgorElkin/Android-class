package com.example.zulipapp.domain.usecase

import com.example.zulipapp.domain.entity.UserStatus
import com.example.zulipapp.domain.repository.UserRepository
import io.reactivex.Single

interface GetUserStatusUseCase : (Int) -> Single<UserStatus>

class GetUserStatusUseCaseImpl(private val userRepository: UserRepository) : GetUserStatusUseCase{

    override fun invoke(userId: Int): Single<UserStatus> {
        return userRepository.getUserStatusById(userId)
    }

}