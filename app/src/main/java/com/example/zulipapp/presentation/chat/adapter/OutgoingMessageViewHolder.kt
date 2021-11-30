package com.example.zulipapp.presentation.chat.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.zulipapp.R
import com.example.zulipapp.presentation.view.OutgoingMessageViewGroup

class OutgoingMessageViewHolder(
    itemView: View,
    private val onReactionClickListener: View.OnClickListener,
    onPlusClickListener: View.OnClickListener,
    onMessageLongClickListener: (Int) -> Unit
) : RecyclerView.ViewHolder(itemView) {

    private val messageView: OutgoingMessageViewGroup = itemView.findViewById(R.id.outgoingMessageViewGroup)

    init {
        messageView.setOnPlusClickListener(onPlusClickListener)
        messageView.getChildAt(OutgoingMessageViewGroup.MESSAGE_INDEX).setOnLongClickListener{
            onMessageLongClickListener(adapterPosition)
            true
        }
    }

    fun bind(messageItem: ChatItem.MessageItem){
        messageView.setMessageText(messageItem.message)
        messageView.setReactions(messageItem.reactionItems, onReactionClickListener)
    }
}