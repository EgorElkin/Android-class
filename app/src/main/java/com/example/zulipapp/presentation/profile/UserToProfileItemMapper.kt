package com.example.zulipapp.presentation.profile

import com.example.zulipapp.domain.entity.User

class UserToProfileItemMapper : (User) -> ProfileItem {
    override fun invoke(user: User): ProfileItem {
        return ProfileItem(
            user.id,
            user.name,
            user.avatarUrl,
            ""
        )
    }

}