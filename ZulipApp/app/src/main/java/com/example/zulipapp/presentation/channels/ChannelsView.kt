package com.example.zulipapp.presentation.channels

import com.example.zulipapp.presentation.base.MvpView
import com.example.zulipapp.presentation.channels.adapter.ChannelsItem

interface ChannelsView : MvpView{

    fun showAllStreams(streams: List<ChannelsItem>)

    fun showSubscribedStreams(streams: List<ChannelsItem>)

    fun showAllLoading()

    fun hideAllLoading()

    fun showSubscribedLoading()

    fun hideSubscribedLoading()

    fun showError(message: Int)
}