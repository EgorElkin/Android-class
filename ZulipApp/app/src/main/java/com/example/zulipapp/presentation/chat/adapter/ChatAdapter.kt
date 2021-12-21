package com.example.zulipapp.presentation.chat.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.zulipapp.R

class ChatAdapter(
    private val onEmojiClickListener: (position: Int, messageId: Int, item: ReactionItem) -> Unit,
    private val onPlusClickListener: (position: Int, item: ChatItem) -> Unit,
    private val onLongClickListener: (position: Int, item: ChatItem) -> Unit,
    private val thresholdPassed: (Int) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object{
        const val PAGING_THRESHOLD = 10
    }

    private val messagesList: MutableList<ChatItem> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType){
            ChatItemViewType.INCOMING_MESSAGE.type -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.chat_incoming_message_item, parent, false)
                IncomingMessageViewHolder(view, { position, reactionItem ->
                    onEmojiClickListener(position, (messagesList[position] as ChatItem.MessageItem).messageId, reactionItem)
                }, {
                    onPlusClickListener(it, messagesList[it])
                }){
                    onLongClickListener(it, messagesList[it])
                }
            }
            ChatItemViewType.OUTGOING_MESSAGE.type -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.chat_outgoing_message_item, parent, false)
                OutgoingMessageViewHolder(view, { position, reactionItem ->
                    onEmojiClickListener(position, (messagesList[position] as ChatItem.MessageItem).messageId, reactionItem)
                }, {
                    onPlusClickListener(it, messagesList[it])
                }){
                    onLongClickListener(it, messagesList[it])
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
        if(position == PAGING_THRESHOLD){
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

    fun addMessages(messages: List<ChatItem>){
        messagesList.addAll(0, messages)
        notifyItemRangeInserted(0, messages.size)
    }

    fun refreshMessage(message: ChatItem, position: Int){
        messagesList[position] = message
        notifyItemChanged(position)
    }

    fun insertMessages(messages: List<ChatItem>){
        val positionStart = messagesList.size
        messagesList.addAll(messages)
        notifyItemRangeInserted(positionStart, messages.size)
    }
}