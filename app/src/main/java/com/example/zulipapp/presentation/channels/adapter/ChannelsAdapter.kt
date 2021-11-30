package com.example.zulipapp.presentation.channels.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.zulipapp.R

class ChannelsAdapter(
    private val onStreamCLicked: (ChannelsItem.StreamItem) -> Unit,
    private val onTopicClicked: (ChannelsItem.TopicItem) -> Unit
) : ListAdapter<ChannelsItem, RecyclerView.ViewHolder>(ItemDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType){
            ChannelsItemViewType.STREAM.type -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.channels_stream_item , parent, false)
                StreamViewHolder(
                    view,
                    {
                        onStreamCLicked(getItem(it) as ChannelsItem.StreamItem)
                    },
                    {
                        expandStream(it)
                    }
                )
            }
            ChannelsItemViewType.TOPIC.type -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.channels_topic_item , parent, false)
                TopicViewHolder(view) {
                    onTopicClicked(getItem(it) as ChannelsItem.TopicItem)
                }
            }
            else -> throw IllegalArgumentException("ChannelsAdapter: Unknown ViewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(getItemViewType(position)){
            ChannelsItemViewType.STREAM.type -> (holder as StreamViewHolder).bind(getItem(position) as ChannelsItem.StreamItem)
            ChannelsItemViewType.TOPIC.type -> (holder as TopicViewHolder).bind(getItem(position) as ChannelsItem.TopicItem)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return getItem(position).viewType.type
    }

    private fun expandStream(position: Int){
        val stream = getItem(position) as ChannelsItem.StreamItem
        val newList = mutableListOf<ChannelsItem>()

        if(stream.expanded){
            val range = 0.rangeTo(position) + ((position + stream.topics.size + 1) until itemCount)
            for(i in range){
                newList.add(getItem(i))
            }
            (newList[position] as ChannelsItem.StreamItem).expanded = false
        } else {
            for (i in 0 until itemCount){
                newList.add(getItem(i))
                if(i == position){
                    stream.topics.forEach {
                        newList.add(it)
                    }
                }
            }
            (newList[position] as ChannelsItem.StreamItem).expanded = true
        }
        submitList(newList)
        notifyItemChanged(position)
    }
}

object ItemDiffCallback : DiffUtil.ItemCallback<ChannelsItem>(){
    override fun areItemsTheSame(oldItem: ChannelsItem, newItem: ChannelsItem): Boolean {
        return oldItem.viewType == newItem.viewType && oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: ChannelsItem, newItem: ChannelsItem): Boolean {
        return oldItem.viewType == newItem.viewType && oldItem.name == newItem.name && oldItem.id == newItem.id
    }

}