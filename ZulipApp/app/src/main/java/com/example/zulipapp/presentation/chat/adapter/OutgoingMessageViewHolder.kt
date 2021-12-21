package com.example.zulipapp.presentation.chat.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.zulipapp.R
import com.example.zulipapp.presentation.view.OutgoingMessageViewGroup

class OutgoingMessageViewHolder(
    itemView: View,
    private val onReactionClickListener: (position: Int, reactionItem: ReactionItem) -> Unit,
    onPlusClickListener: (Int) -> Unit,
    onMessageLongClickListener: (Int) -> Unit
) : RecyclerView.ViewHolder(itemView) {

    private val messageView: OutgoingMessageViewGroup = itemView.findViewById(R.id.outgoingMessageViewGroup)

    init {
        messageView.setOnPlusClickListener{
            onPlusClickListener(adapterPosition)
        }
        messageView.getChildAt(OutgoingMessageViewGroup.MESSAGE_INDEX).setOnLongClickListener{
            onMessageLongClickListener(adapterPosition)
            true
        }
    }

    fun bind(messageItem: ChatItem.MessageItem){
        messageView.setMessageText(messageItem.message)
        messageView.setReactions(messageItem.reactionItems) {
            onReactionClickListener(adapterPosition, it)
        }
    }
}