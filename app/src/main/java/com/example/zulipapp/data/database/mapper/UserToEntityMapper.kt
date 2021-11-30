package com.example.zulipapp.data.database.mapper

import com.example.zulipapp.data.database.entity.UserEntity
import com.example.zulipapp.domain.entity.User

class UserToEntityMapper : (User) -> UserEntity {
    override fun invoke(user: User): UserEntity {
        return UserEntity(
            user.id,
            user.name,
            user.email,
            user.avatarUrl
        )
    }
}

class UsersToEntitiesMapper : (List<User>) -> List<UserEntity> {
    override fun invoke(users: List<User>): List<UserEntity> {
        return users.map {
            UserToEntityMapper().invoke(it)
        }
    }
}