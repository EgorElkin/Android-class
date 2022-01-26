package com.example.zulipapp.data.database.dao

import androidx.room.*
import com.example.zulipapp.data.database.entity.UserEntity
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface UserDao {

    @Query("SELECT * FROM USERS_TABLE WHERE user_id = :userId")
    fun getUserById(userId: Int): Single<UserEntity>

    @Query("SELECT * FROM USERS_TABLE")
    fun getAllUsers(): Single<List<UserEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: UserEntity): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(users: List<UserEntity>): Completable

    @Delete
    fun delete(user: UserEntity): Completable

    @Query("DELETE FROM USERS_TABLE")
    fun deleteAll(): Completable
}