package com.example.zulipapp.presentation.chat.elm

import com.example.zulipapp.data.api.entity.message.Narrow

sealed class ChatCommand {

    class LoadFirstPage(val narrows: List<Narrow>) : ChatCommand()
    class LoadPage(val anchor: Int, val narrows: List<Narrow>) : ChatCommand()
    class SendMessage(val type: String, val streamName: String, val topicName: String?, val message: String) : ChatCommand()
}