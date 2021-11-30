package com.example.zulipapp.presentation.chat.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.zulipapp.R
import com.example.zulipapp.presentation.view.IncomingMessageViewGroup
import io.reactivex.subjects.PublishSubject

class IncomingMessageViewHolder(
    itemView: View,
    private val onReactionClickListener: View.OnClickListener,
    onPlusClickListener: View.OnClickListener,
    onMessageLongClickListener: (Int) -> Unit
) : RecyclerView.ViewHolder(itemView) {

    private val messageView: IncomingMessageViewGroup = itemView.findViewById(R.id.incomingMessageViewGroup)

    init {
        messageView.setOnPlusClickListener(onPlusClickListener)
        messageView.getChildAt(IncomingMessageViewGroup.MESSAGE_INDEX).setOnLongClickListener{
            onMessageLongClickListener(adapterPosition)
            true
        }
    }

    fun bind(messageItem: ChatItem.MessageItem){
        messageView.setAvatar(messageItem.avatarUrl)
        messageView.setUserName(messageItem.userName)
        messageView.setMessageText(messageItem.message)
        messageView.setReactions(messageItem.reactionItems, onReactionClickListener)
    }
}