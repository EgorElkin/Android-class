package com.example.zulipapp.data.repository

import com.example.zulipapp.data.api.NetworkUserDataSource
import com.example.zulipapp.data.database.LocalUserDataSource
import com.example.zulipapp.domain.entity.User
import com.example.zulipapp.domain.entity.UserStatus
import com.example.zulipapp.domain.repository.UserRepository
import io.reactivex.Observable
import io.reactivex.Single

class UserRepositoryImpl(
    private val networkDataSource: NetworkUserDataSource,
    private val localDataSource: LocalUserDataSource
) : UserRepository {
    override fun getAllUsers(): Observable<List<User>> {
//        return Observable.merge(
//            localDataSource.getAllUsers().toObservable(),
//            networkDataSource.getAllUsers()
////                .doOnSuccess {
////                    println("debug: save fetching results")
////                    localDataSource.addAllUsers(it)
////                }
//                .toObservable()
//        )
//        return networkDataSource.getAllUsers().doOnSuccess {
//            println("debug: save fetching results")
//            localDataSource.addAllUsers(it)
//        }.toObservable()

        return networkDataSource.getAllUsers().toObservable()
    }

    override fun getUserMe(): Single<User> {
        return networkDataSource.getUserMe()
    }

    override fun getUserById(userId: Int): Single<User> {
        return networkDataSource.getUserById(userId.toString())
    }

    override fun getUserStatusById(userId: Int): Single<UserStatus> {
        return networkDataSource.getUserPresenceById(userId.toString())
    }
}