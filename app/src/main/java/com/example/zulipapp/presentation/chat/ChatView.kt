package com.example.zulipapp.presentation.chat

import com.example.zulipapp.presentation.base.MvpView
import com.example.zulipapp.presentation.chat.adapter.ChatItem

interface ChatView : MvpView{

    fun addMessages(messages: List<ChatItem>)

    fun setMessages(messages: List<ChatItem>)

    fun showEmptyMessageNotice(noticeRes: Int)
}