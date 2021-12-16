package com.example.zulipapp.domain.usecase

import com.example.zulipapp.domain.entity.User
import com.example.zulipapp.domain.repository.UserRepository
import io.reactivex.Observable

interface GetPeopleUseCase : () -> Observable<List<User>>

class GetPeopleUseCaseImpl(private val userRepository: UserRepository) : GetPeopleUseCase{
    override fun invoke(): Observable<List<User>> {
        return userRepository.getAllUsers()
    }

}