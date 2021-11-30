package com.example.zulipapp.presentation.channels.adapter

import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.zulipapp.R

class StreamViewHolder(itemView: View, onStreamClicked: (Int) -> Unit, onExpandButtonClicked: (Int) -> Unit) : RecyclerView.ViewHolder(itemView) {

    private val streamName: TextView = itemView.findViewById(R.id.channelsStreamTitleTextView)
    private val expandButton: ImageButton = itemView.findViewById(R.id.channelsStreamExpandImageButton)

    init {
        itemView.setOnClickListener {
            onStreamClicked(adapterPosition)
        }
        expandButton.setOnClickListener {
            onExpandButtonClicked(adapterPosition)
        }
    }

    fun bind(streamItem: ChannelsItem.StreamItem){
        streamName.text = streamItem.name
        streamName.setTextColor(streamItem.color)
        if(streamItem.topics.isNotEmpty()){
            if(streamItem.expanded){
                expandButton.visibility = View.VISIBLE
                expandButton.setImageResource(R.drawable.baseline_expand_less)
            } else {
                expandButton.visibility = View.VISIBLE
                expandButton.setImageResource(R.drawable.baseline_expand_more)
            }
        } else {
            expandButton.visibility = View.GONE
        }
    }
}