package com.example.zulipapp.presentation.chat.elm

import com.example.zulipapp.data.api.entity.message.Narrow

sealed class ChatCommand {

    class LoadFirstPage(val narrows: List<Narrow>) : ChatCommand()
    class LoadPage(val anchor: Int, val narrows: List<Narrow>) : ChatCommand()
    class SendMessage(val type: String, val streamName: String, val topicName: String?, val message: String, val narrows: List<Narrow>) : ChatCommand()
    class AddReaction(val messageId: Int, val emojiName: String, val emojiCode: String, val position: Int, val narrows: List<Narrow>) : ChatCommand()
    class RemoveReaction(val messageId: Int, val emojiName: String, val emojiCode: String, val position: Int, val narrows: List<Narrow>) : ChatCommand()
    class RefreshChat(val currentNewestMessageId: Int, val narrows: List<Narrow>) : ChatCommand()
    class RefreshMessage(val messageId: Int, val narrows: List<Narrow>, val position: Int) : ChatCommand()
}