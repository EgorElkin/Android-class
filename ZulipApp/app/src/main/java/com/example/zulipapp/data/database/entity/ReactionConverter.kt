package com.example.zulipapp.data.database.entity

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ReactionConverter {

    private val gson = Gson()
    private val type = object : TypeToken<List<ReactionEntity>>(){}.type

    @TypeConverter
    fun fromReactions(reactions: List<ReactionEntity>): String{
        return gson.toJson(reactions, type)
    }

    @TypeConverter
    fun toReactions(json: String): List<ReactionEntity>{
        return gson.fromJson(json, type)
    }
}