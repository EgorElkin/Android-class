package com.example.zulipapp.data.database

import com.example.zulipapp.data.database.dao.UserDao
import com.example.zulipapp.data.database.mapper.EntitiesToUsersMapper
import com.example.zulipapp.data.database.mapper.EntityToUserMapper
import com.example.zulipapp.data.database.mapper.UsersToEntitiesMapper
import com.example.zulipapp.domain.entity.User
import io.reactivex.Completable
import io.reactivex.Single

class LocalUserDataSource(private val userDao: UserDao) {

    fun addAllUsers(users: List<User>): Completable = userDao.insertAll(UsersToEntitiesMapper().invoke(users))

    fun getAllUsers(): Single<List<User>> = userDao.getAllUsers().map(EntitiesToUsersMapper())

    fun getUserById(userId: Int): Single<User> = userDao.getUserById(userId).map(EntityToUserMapper())
}