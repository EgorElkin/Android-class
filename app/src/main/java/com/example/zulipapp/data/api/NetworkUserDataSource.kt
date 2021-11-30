package com.example.zulipapp.data.api

import com.example.zulipapp.data.api.mapper.AllUsersResponseMapper
import com.example.zulipapp.data.api.mapper.UserByIdResponseMapper
import com.example.zulipapp.data.api.mapper.UserMeResponseMapper
import com.example.zulipapp.data.api.mapper.UserPresenceResponseMapper
import com.example.zulipapp.domain.entity.User
import com.example.zulipapp.domain.entity.UserStatus
import io.reactivex.Single

class NetworkUserDataSource(private val userApiService: UserApiService) {

    fun getAllUsers(): Single<List<User>> {
        return userApiService.getAllUsers().map(AllUsersResponseMapper())
    }

    fun getUserMe(): Single<User> {
        return userApiService.getUserMe().map(UserMeResponseMapper())
    }

    fun getUserById(userId: String): Single<User> {
        return userApiService.getUserById(userId).map(UserByIdResponseMapper())
    }

    fun getUserPresenceById(userId: String): Single<UserStatus> {
        return userApiService.getUserPresenceById(userId).map(UserPresenceResponseMapper())
    }
}