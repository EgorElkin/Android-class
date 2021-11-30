package com.example.zulipapp.presentation.chat

interface ChatPresenter {

    fun viewIsReady(streamName: String?, topicName: String?)

    fun sendMessageClicked(content: String)

    fun reactionSelected()

    fun newReactionAdded()

    fun reachThreshold()
}