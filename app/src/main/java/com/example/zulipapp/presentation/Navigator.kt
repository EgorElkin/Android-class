package com.example.zulipapp.presentation

interface Navigator {

    fun navigateToChannels()

    fun navigateToPeople()

    fun navigateToProfile()

    fun showProfile(userId: Int)

    fun showChat(streamName: String, topicName: String?)
}