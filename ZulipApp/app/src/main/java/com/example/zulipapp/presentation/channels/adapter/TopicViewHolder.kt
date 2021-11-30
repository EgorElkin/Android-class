package com.example.zulipapp.presentation.channels.adapter

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.zulipapp.R

class TopicViewHolder(itemView: View, onTopicClicked: (Int) -> Unit) : RecyclerView.ViewHolder(itemView) {

    private val topicName: TextView = itemView.findViewById(R.id.channelsTopicTitleTextView)
    private val messagesCount: TextView = itemView.findViewById(R.id.channelsTopicMessagesCount)

    init {
        itemView.setOnClickListener {
            onTopicClicked(adapterPosition)
        }
    }

    fun bind(topicItem: ChannelsItem.TopicItem){
        topicName.text = topicItem.name
    }
}