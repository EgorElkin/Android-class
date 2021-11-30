package com.example.zulipapp.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users_table")
class UserEntity(
    @PrimaryKey
    @ColumnInfo(name = "user_id")
    val id: Int,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "email")
    val email: String,
    @ColumnInfo(name = "avatar_url")
    val avatarUrl:String
)