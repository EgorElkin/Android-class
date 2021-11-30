package com.example.zulipapp.data.api.entity.message

import com.example.zulipapp.data.database.entity.TopicEntity
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken

class Narrow(
    @SerializedName("operator")
    val operator: String,
    @SerializedName("operand")
    val operand: String,
    @SerializedName("negated")
    val negate: Boolean? = null
){
    companion object{
        private val gson = Gson()
        private val type = object : TypeToken<List<Narrow>>(){}.type

        fun toJson(narrows: List<Narrow>) = gson.toJson(narrows, type)
    }
}