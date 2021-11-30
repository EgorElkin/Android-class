package com.example.zulipapp.presentation.chat.adapter

import com.example.zulipapp.domain.entity.Message
import com.example.zulipapp.domain.entity.Reaction
import com.example.zulipapp.presentation.util.Constants

class MessageToItemMapper : (List<Message>) ->  List<ChatItem>{

    override fun invoke(messages: List<Message>): List<ChatItem> {
        return messages.map {
            when(it.senderId){
                Constants.MY_ID -> {
                    ChatItem.MessageItem(
                        ChatItemViewType.OUTGOING_MESSAGE,
                        it.id,
                        it.senderAvatar,
                        it.senderFullName,
                        it.content,
                        parseReactions(it.reactions),
                        it.timeStamp
                    )
                }
                else -> {
                    ChatItem.MessageItem(
                        ChatItemViewType.INCOMING_MESSAGE,
                        it.id,
                        it.senderAvatar,
                        it.senderFullName,
                        it.content,
                        parseReactions(it.reactions),
                        it.timeStamp
                    )
                }
            }
        }
    }

    private fun parseReactions(reactions: List<Reaction>): MutableList<ReactionItem>{
        val newList = mutableListOf<ReactionItem>()
        var name = ""
        reactions.sortedBy { it.emojiName }.forEach {
            if(it.emojiName != name){
                name = it.emojiName
                newList.add(
                    ReactionItem(
                        it.emojiName,
                        it.emojiCode,
                        it.reactionType,
                        mutableListOf(it.userId)
                    )
                )
            } else {
                newList.last().userIds.add(it.userId)
            }
        }
        newList.sortByDescending { it.userIds.size }
        return newList
    }
}