package com.example.zulipapp.domain.usecase

import com.example.zulipapp.domain.entity.User
import com.example.zulipapp.domain.repository.UserRepository
import io.reactivex.Single

interface GetUserByIdUseCase : (Int) -> Single<User>

class GetUserByIdUseCaseImpl(private val userRepository: UserRepository) : GetUserByIdUseCase{

    override fun invoke(userId: Int): Single<User> {
        return userRepository.getUserById(userId)
    }

}