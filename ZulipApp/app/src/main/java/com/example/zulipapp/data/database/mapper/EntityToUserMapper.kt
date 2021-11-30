package com.example.zulipapp.data.database.mapper

import com.example.zulipapp.data.database.entity.UserEntity
import com.example.zulipapp.domain.entity.User

class EntityToUserMapper : (UserEntity) -> User {
    override fun invoke(entity: UserEntity): User {
        return User(
            entity.id,
            entity.name,
            entity.email,
            entity.avatarUrl
        )
    }
}

class EntitiesToUsersMapper : (List<UserEntity>) -> List<User> {
    override fun invoke(entities: List<UserEntity>): List<User> {
        return entities.map {
            EntityToUserMapper().invoke(it)
        }
    }
}