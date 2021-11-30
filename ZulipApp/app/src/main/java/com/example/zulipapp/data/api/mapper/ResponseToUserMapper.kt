package com.example.zulipapp.data.api.mapper

import com.example.zulipapp.data.api.entity.user.UserResponse
import com.example.zulipapp.data.api.entity.user.UsersResponse
import com.example.zulipapp.domain.entity.User
import com.example.zulipapp.domain.entity.UserStatus

class AllUsersResponseMapper : (UsersResponse) -> List<User> {
    override fun invoke(response: UsersResponse): List<User> {
        return response.allUsers.map {
            User(
                it.id,
                it.name,
                it.email,
                it.avatarUrl
            )
        }
    }
}

class UserMeResponseMapper : (UserResponse) -> User {
    override fun invoke(response: UserResponse): User {
        return User(
            response.id,
            response.name,
            response.email,
            response.avatarUrl
        )
    }
}

class UserByIdResponseMapper : (UsersResponse) -> User {
    override fun invoke(response: UsersResponse): User {
        return User(
            response.userById.id,
            response.userById.name,
            response.userById.email,
            response.userById.avatarUrl
        )
    }
}

class UserPresenceResponseMapper : (UsersResponse) -> UserStatus{
    override fun invoke(response: UsersResponse): UserStatus {
        return UserStatus(
            response.presence.aggregated.status,
            response.presence.aggregated.timeStamp
        )
    }

}