package com.example.zulipapp.presentation.chat

import com.example.zulipapp.R
import com.example.zulipapp.data.api.NetworkMessageDataSource
import com.example.zulipapp.data.api.RetrofitBuilder
import com.example.zulipapp.data.api.entity.message.Narrow
import com.example.zulipapp.data.repository.MessageRepositoryImpl
import com.example.zulipapp.domain.usecase.GetMessagesUseCase
import com.example.zulipapp.domain.usecase.GetMessagesUseCaseImpl
import com.example.zulipapp.presentation.base.BasePresenter
import com.example.zulipapp.presentation.chat.adapter.ChatItem
import com.example.zulipapp.presentation.chat.adapter.MessageToItemMapper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ChatPresenterImpl(
    chatView: ChatView,
) : BasePresenter<ChatView>(chatView), ChatPresenter {

    private lateinit var  getMessagesUseCase: GetMessagesUseCase

    private var currentFirstMessageId = 0
    private val pageSize = 30
    private val pageThreshold = 5
    private var narrows: List<Narrow> = emptyList()

    override fun attachView(view: ChatView){
        super.attachView(view)
        getMessagesUseCase = GetMessagesUseCaseImpl(
            MessageRepositoryImpl(
                NetworkMessageDataSource(RetrofitBuilder.messageApiService),
//                LocalMessageDataSource((getView() as Fragment).requireContext())
            )
        )
    }

    override fun viewIsReady(streamName: String?, topicName: String?) {
        narrows = if(streamName.isNullOrEmpty()){
            emptyList()
        } else {
            if(topicName.isNullOrEmpty()){
                listOf(Narrow("stream", streamName))
            } else {
                listOf(Narrow("stream", streamName), Narrow("topic", topicName))
            }
        }

        getFirstPage()
    }

    override fun sendMessageClicked(content: String) {
        if(content.isBlank()) {
            getView()?.showEmptyMessageNotice(R.string.chat_empty_message_notice)
        } else {
            TODO("Send message")
        }
    }

    override fun reactionSelected() {
        TODO("Not yet implemented")
    }

    override fun newReactionAdded() {
        TODO("Not yet implemented")
    }

    override fun reachThreshold() {
        TODO("Not yet implemented")
    }

    private fun getFirstPage(){
        compositeDisposable.add(
            getMessagesUseCase.getMessages("newest", pageSize, 0, narrows)
                .subscribeOn(Schedulers.io())
                .map(MessageToItemMapper())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    currentFirstMessageId = (it.last() as ChatItem.MessageItem).messageId
                    getView()?.setMessages(it)
                    println("debug: onNext: size=${it.size} messages, lastId=$currentFirstMessageId")
                },{
                    println("debug: onError: $it")
                })
        )
    }

    private fun getNextPage(){
        compositeDisposable.add(
            getMessagesUseCase.getMessages(currentFirstMessageId, pageSize, 0, narrows)
                .subscribeOn(Schedulers.io())
                .map(MessageToItemMapper())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    currentFirstMessageId = (it.last() as ChatItem.MessageItem).messageId
                    getView()?.addMessages(it)
                    println("debug: onNext: size=${it.size} messages, lastId=$currentFirstMessageId")
                },{
                    println("debug: onError: $it")
                })
        )
    }
}