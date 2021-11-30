package com.example.zulipapp.data.database

import android.content.Context
import com.example.zulipapp.data.database.mapper.EntitiesToUsersMapper
import com.example.zulipapp.data.database.mapper.EntityToUserMapper
import com.example.zulipapp.data.database.mapper.UsersToEntitiesMapper
import com.example.zulipapp.domain.entity.User
import io.reactivex.Completable
import io.reactivex.Single

class LocalUserDataSource(applicationContext: Context) {

    private val appDatabase = RoomBuilder.provideAppDatabase(applicationContext)

    fun addAllUsers(users: List<User>): Completable = appDatabase.UserDao().insertAll(UsersToEntitiesMapper().invoke(users))

    fun getAllUsers(): Single<List<User>> = appDatabase.UserDao().getAllUsers().map(EntitiesToUsersMapper())

    fun getUserById(userId: Int): Single<User> = appDatabase.UserDao().getUserById(userId).map(EntityToUserMapper())
}