package com.example.zulipapp.presentation.util

import android.content.Context

class EmojiContainer {
    companion object{
        fun getEmojisMap(context: Context): LinkedHashMap<String, String>{
            val map = LinkedHashMap<String, String>()
            val buffer = try{
                context.assets.open("emojis.txt").bufferedReader()
            } catch(e: Exception) {
                return map
            }
            var line = buffer.readLine()
            while (!line.isNullOrEmpty()){
                val lines = line.split(":").toTypedArray()
                try {
                    map.put(lines[0], lines[1])
                } catch(e: Exception){
                    return map
                }
                line = buffer.readLine()
            }
            return map
        }
    }
}