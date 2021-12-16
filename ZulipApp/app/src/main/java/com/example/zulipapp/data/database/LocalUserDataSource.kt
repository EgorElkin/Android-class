package com.example.zulipapp.data.database

import android.content.Context
import com.example.zulipapp.data.database.dao.UserDao
import com.example.zulipapp.data.database.mapper.EntitiesToUsersMapper
import com.example.zulipapp.data.database.mapper.EntityToUserMapper
import com.example.zulipapp.data.database.mapper.UsersToEntitiesMapper
import com.example.zulipapp.domain.entity.User
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class LocalUserDataSource(private val userDao: UserDao) {

//    private val appDatabase = RoomBuilder.provideAppDatabase(applicationContext)

//    fun addAllUsers(users: List<User>): Completable = appDatabase.UserDao().insertAll(UsersToEntitiesMapper().invoke(users))
    fun addAllUsers(users: List<User>): Completable = userDao.insertAll(UsersToEntitiesMapper().invoke(users))

//    fun getAllUsers(): Single<List<User>> = appDatabase.UserDao().getAllUsers().map(EntitiesToUsersMapper())
    fun getAllUsers(): Single<List<User>> = userDao.getAllUsers().map(EntitiesToUsersMapper())

//    fun getUserById(userId: Int): Single<User> = appDatabase.UserDao().getUserById(userId).map(EntityToUserMapper())
    fun getUserById(userId: Int): Single<User> = userDao.getUserById(userId).map(EntityToUserMapper())
}