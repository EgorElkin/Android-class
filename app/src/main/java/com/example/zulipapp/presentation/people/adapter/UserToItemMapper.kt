package com.example.zulipapp.presentation.people.adapter

import com.example.zulipapp.domain.entity.User

class UserToItemMapper : (List<User>) -> (List<UserItem>) {

    override fun invoke(users: List<User>): List<UserItem> {
        return users.map {
            UserItem(
                it.id,
                it.name,
                it.email,
                it.avatarUrl
            )
        }
    }
}