package com.example.zulipapp.presentation.chat

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.zulipapp.R
import com.example.zulipapp.data.api.NetworkMessageDataSource
import com.example.zulipapp.data.api.RetrofitBuilder
import com.example.zulipapp.data.api.entity.message.Narrow
import com.example.zulipapp.data.repository.MessageRepositoryImpl
import com.example.zulipapp.domain.usecase.GetMessagesUseCase
import com.example.zulipapp.domain.usecase.GetMessagesUseCaseImpl
import com.example.zulipapp.presentation.chat.adapter.*
import com.example.zulipapp.presentation.chat.reactiondialog.EmojiItem
import com.example.zulipapp.presentation.chat.reactiondialog.ReactionBottomDialog
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class ChatFragment : Fragment(R.layout.fragment_chat), ChatView{

    companion object{
        const val ARG_STREAM_KEY = "chat_stream_key"
        const val ARG_TOPIC_KEY = "chat_topic_key"

        fun newInstance(streamName: String, topicName: String?): ChatFragment{
            val fragment = ChatFragment()
            val arguments = Bundle()
            arguments.putString(ARG_STREAM_KEY, streamName)
            arguments.putString(ARG_TOPIC_KEY, topicName)
            fragment.arguments = arguments
            return fragment
        }
    }

    private lateinit var presenter: ChatPresenterImpl

    private lateinit var headline: TextView
    private lateinit var recyclerView: RecyclerView
    private lateinit var editText: EditText
    private lateinit var buttonSend: ImageButton
    private lateinit var adapter: ChatAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        headline = view.findViewById(R.id.chatTopicHeadline)
        editText = view.findViewById(R.id.chatEditText)
        buttonSend = view.findViewById(R.id.chatButtonSend)
        buttonSend.setOnClickListener {
            presenter.sendMessageClicked(editText.text.toString())
        }

        recyclerView = view.findViewById(R.id.chatRecyclerView)
        val manager = LinearLayoutManager(context)
        manager.stackFromEnd = true
        recyclerView.layoutManager = manager
        adapter = ChatAdapter({
            TODO("on Emoji Click")
        },{
            showDialog()
        },{ item, position ->
            TODO("on Message Long CLick")
            true
        })


        recyclerView.adapter = adapter

        setResultListener()

        presenter = ChatPresenterImpl(this)
        presenter.attachView(this)
        presenter.viewIsReady(
            arguments?.getString(ARG_STREAM_KEY),
            arguments?.getString(ARG_TOPIC_KEY)
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }

    // ChatView
    override fun addMessages(messages: List<ChatItem>) {
        adapter.addMessages(messages)
    }

    override fun setMessages(messages: List<ChatItem>) {
        adapter.setMessages(messages)
    }

    override fun showEmptyMessageNotice(noticeRes: Int) {
        Toast.makeText(context, getString(noticeRes), Toast.LENGTH_SHORT).show()
    }

    private fun setResultListener(){
        childFragmentManager.setFragmentResultListener("reaction_selection", this){ key, bundle ->
            if(key == "reaction_selection"){
                val result = bundle.getParcelable<EmojiItem>("emoji")
                TODO("add selected reaction")
//                if(result != null) addReaction(selectedMessageId, result)
            }
        }
    }

    private fun showDialog(){
        val reactionDialog = ReactionBottomDialog()
        reactionDialog.show(childFragmentManager, ReactionBottomDialog.TAG)
    }
}