package com.example.zulipapp.presentation.channels

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.zulipapp.R
import com.example.zulipapp.presentation.channels.adapter.ChannelsAdapter
import com.example.zulipapp.presentation.channels.adapter.ChannelsItem

class ChannelsViewPagerAdapter(
    private val channelsSubscribedAdapter: ChannelsAdapter,
    private val channelsAllAdapter: ChannelsAdapter
): RecyclerView.Adapter<ChannelsViewPagerAdapter.PageViewHolder>() {

    private val subscribedChannels: MutableList<ChannelsItem> = mutableListOf()
    private val allChannels: MutableList<ChannelsItem> = mutableListOf()

    class PageViewHolder(itemView: View, private val adapter: ChannelsAdapter) : RecyclerView.ViewHolder(itemView){

        private val recyclerView: RecyclerView = itemView.findViewById(R.id.channelsViewPagerRecyclerView)
        private val progressBar: ProgressBar = itemView.findViewById(R.id.channelsProgressBar)

        init {
            recyclerView.adapter = adapter
        }

        fun bind(list: List<ChannelsItem>){
            adapter.submitList(list)
        }

        fun showLoading(loading: Boolean){
            progressBar.isVisible = loading
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.channels_view_pager_item, parent, false)
        return when(viewType){
            SUBSCRIBED_FRAGMENT_POSITION -> PageViewHolder(view, channelsSubscribedAdapter)
            ALL_STREAMS_FRAGMENT_POSITION -> PageViewHolder(view, channelsAllAdapter)
            else -> throw IllegalArgumentException("ChannelsViewPagerAdapter: onCreateVIewHOlder(): Unknown ViewType")
        }
    }

    override fun onBindViewHolder(holder: PageViewHolder, position: Int) {
        when(position){
            SUBSCRIBED_FRAGMENT_POSITION -> holder.bind(subscribedChannels)
            ALL_STREAMS_FRAGMENT_POSITION -> holder.bind(allChannels)
        }
    }

    override fun getItemCount(): Int {
        return ITEMS_COUNT
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    fun setSubscribedStreams(newList: List<ChannelsItem>){
        subscribedChannels.clear()
        subscribedChannels.addAll(newList)
        notifyItemChanged(SUBSCRIBED_FRAGMENT_POSITION)
    }

    fun setAllStreams(newList: List<ChannelsItem>){
        allChannels.clear()
        allChannels.addAll(newList)
        notifyItemChanged(ALL_STREAMS_FRAGMENT_POSITION)
    }

    fun subscribedSize() = subscribedChannels.size

    fun allSize() = allChannels.size

    fun showSubscribedLoading(loading: Boolean){

    }

    fun showAllLoading(loading: Boolean){

    }

    companion object{
        const val ITEMS_COUNT = 2
        const val SUBSCRIBED_FRAGMENT_POSITION = 0
        const val ALL_STREAMS_FRAGMENT_POSITION = 1
    }
}