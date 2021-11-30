package com.example.zulipapp.presentation.channels

interface ChannelsPresenter {

    fun viewIsReady()

    fun searchRequestChanged(searchQuery: String)

    fun searchButtonClicked(searchQuery: String)

    fun allStreamsListSelected(searchQuery: String)

    fun subscribedStreamsListSelected(searchQuery: String)

    fun streamSelected(streamName: String)

    fun topicSelected(streamName: String, topicName: String)
}