package com.example.zulipapp.data.repository

import com.example.zulipapp.data.api.NetworkUserDataSource
import com.example.zulipapp.data.database.LocalUserDataSource
import com.example.zulipapp.domain.entity.User
import com.example.zulipapp.domain.entity.UserStatus
import com.example.zulipapp.domain.repository.UserRepository
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable

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

        return localDataSource.getAllUsers()
            .flatMapObservable { localUsers ->
                println("debug: flatMapObservable local=${localUsers.size}")
                networkDataSource.getAllUsers()
                    .toObservable()
                    .filter { apiUsers ->
                        println("debug: filter: api=${apiUsers.size} filter=${apiUsers != localUsers}")
                        apiUsers != localUsers
                    }
                    .flatMapSingle { apiUsers ->
                        println("debug: flatMapSingle api=${apiUsers.size}")
                        localDataSource.deleteAll().andThen(
                                localDataSource.addAllUsers(apiUsers).andThen(
                                    Single.just(apiUsers.sortedBy { it.name })
                                )
                            )
                    }
                    .startWith(localUsers.sortedBy { it.name })
            }

//        return networkDataSource.getAllUsers()
//            .doOnSuccess {
//                println("debug: save fetching results ${it.size}")


//                CompositeDisposable().addAll(
//                    localDataSource.deleteAll()
//                        .doOnComplete {
//                            println("debug: delete: doOnComplete")
//                        }
//                        .doOnError { error ->
//                            println("debug: delete: doOnError: $error")
//                        }
//                        .subscribe({
//                            println("debug: delete: COMPETE")
//                        },{ error ->
//                            println("debug: delete: ERROR: $error")
//                        })
//                    ,
//                    localDataSource.addAllUsers(it)
//                        .doOnComplete {
//                            println("debug: insert: doOnComplete")
//                        }
//                        .doOnError { error ->
//                            println("debug: insert: doOnError: $error")
//                        }
//                        .subscribe({
//                            println("debug: insert: COMPLETE")
//                        },{ error ->
//                            println("debug: insert: ERROR: $error")
//                        })
//                )

//                CompositeDisposable().add(
//                    localDataSource.getAllUsers()
//                        .doOnSuccess { users ->
//                            println("debug: doOnComplete: ${users.size}")
//                        }
//                        .doOnError { error ->
//                            println("debug: doOnError: $error")
//                        }
//                        .subscribe({ users ->
//                            println("debug: COMPLETE: ${users.isNullOrEmpty()}")
//                        },{ error ->
//                            println("debug: ERROR: $error")
//                        })
//                )

//        }.toObservable()

//        return localDataSource.getAllUsers().toObservable()
//        return networkDataSource.getAllUsers().toObservable()
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