package com.example.zulipapp.domain.repository

import com.example.zulipapp.domain.entity.User
import com.example.zulipapp.domain.entity.UserStatus
import io.reactivex.Observable
import io.reactivex.Single

interface UserRepository {

    fun getAllUsers(): Observable<List<User>>

    fun getUserMe(): Single<User>

    fun getUserById(userId: Int): Single<User>

    fun getUserStatusById(userId: Int): Single<UserStatus>
}