package com.example.zulipapp.presentation.chat.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.zulipapp.R
import java.lang.IllegalArgumentException

class ChatAdapter(
    private val onEmojiClickListener: View.OnClickListener,
    private val onPlusClickListener: View.OnClickListener,
    private val onLongClickListener: (item: ChatItem, position: Int) -> Unit,
private val thresholdPassed: (Int) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val messagesList: MutableList<ChatItem> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType){
            ChatItemViewType.INCOMING_MESSAGE.type -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.chat_incoming_message_item, parent, false)
                IncomingMessageViewHolder(view, onEmojiClickListener, onPlusClickListener){
                    onLongClickListener(messagesList[it], it)
                }
            }
            ChatItemViewType.OUTGOING_MESSAGE.type -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.chat_outgoing_message_item, parent, false)
                OutgoingMessageViewHolder(view, onEmojiClickListener, onPlusClickListener){
                    onLongClickListener(messagesList[it], it)
                }
            }
            ChatItemViewType.DATE_ITEM.type -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.chat_date_item, parent, false)
                DateViewHolder(view)
            }
            else -> {
                throw IllegalArgumentException("ChatAdapter: Invalid ViewType")
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(position == 5){
            thresholdPassed(position)
        }
        when(getItemViewType(position)){
            ChatItemViewType.INCOMING_MESSAGE.type -> (holder as IncomingMessageViewHolder).bind(messagesList[position] as ChatItem.MessageItem)
            ChatItemViewType.OUTGOING_MESSAGE.type -> (holder as OutgoingMessageViewHolder).bind(messagesList[position] as ChatItem.MessageItem)
            ChatItemViewType.DATE_ITEM.type -> (holder as DateViewHolder).bind(messagesList[position] as ChatItem.DateItem)
        }
    }

    override fun getItemCount(): Int {
        return messagesList.size
    }

    override fun getItemViewType(position: Int): Int {
        return messagesList[position].viewType.type
    }

    fun addMessage(item: ChatItem){
        messagesList.add(item)
        notifyItemInserted(itemCount - 1)
    }

    fun addMessages(messages: List<ChatItem>){
        val positionStart = messagesList.size
        messagesList.addAll(messages)
        notifyItemRangeInserted(positionStart, messages.size)
    }

    fun setMessages(newList: List<ChatItem>){
        messagesList.clear()
        messagesList.addAll(newList)
//        notifyDataSetChanged()
        notifyItemRangeInserted(0, messagesList.size)
    }

    fun addReactionOnMessageAtIndex(reactionItem: ReactionItem, index: Int){
        (messagesList[index] as ChatItem.MessageItem).reactionItems.add(reactionItem)
        notifyItemChanged(index)
    }

    fun removeReactionOnMessageByIndex(messageIndex: Int, reactionIndex: Int){
        (messagesList[messageIndex] as ChatItem.MessageItem).reactionItems.removeAt(reactionIndex)
        notifyItemChanged(messageIndex)

    }

    fun removeReactionOnMessage(messageIndex: Int, reactionItem: ReactionItem){
        (messagesList[messageIndex] as ChatItem.MessageItem).reactionItems.remove(reactionItem)
        notifyItemChanged(messageIndex)
    }
}