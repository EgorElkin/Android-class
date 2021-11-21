package com.example.zulipapp.presentation.util

class EmojiCodeMapper {

    companion object {
        fun codeToEmoji(code: String): String {
            return try {
                String(Character.toChars(code.toInt(16)))
            } catch (e: Exception){
                "\uD83E\uDEE0"
            }
        }
    }
}