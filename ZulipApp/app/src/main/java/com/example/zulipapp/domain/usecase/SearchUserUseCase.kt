package com.example.zulipapp.domain.usecase

import com.example.zulipapp.domain.entity.User
import com.example.zulipapp.domain.repository.UserRepository
import io.reactivex.Observable

interface SearchUserUseCase : (String) -> Observable<List<User>>

class SearchUserUseCaseImpl(private val userRepository: UserRepository) : SearchUserUseCase{

    override fun invoke(searchQuery: String): Observable<List<User>> {
        return userRepository.getAllUsers()
            .map { list ->
            list.filter {
                it.name.contains(searchQuery, ignoreCase = true)
            }
        }
    }

}