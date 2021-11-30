package com.example.zulipapp.data.database.entity

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class TopicConverter {

    private val gson = Gson()
    private val type = object : TypeToken<List<TopicEntity>>(){}.type

    @TypeConverter
    fun fromTopics(topics: List<TopicEntity>): String{
        return gson.toJson(topics, type)
    }

    @TypeConverter
    fun toTopics(json: String): List<TopicEntity>{
        return gson.fromJson(json, type)
    }
}