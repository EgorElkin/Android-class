package com.example.zulipapp.presentation.chat

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.zulipapp.R
import com.example.zulipapp.data.api.entity.message.Narrow
import com.example.zulipapp.databinding.FragmentChatBinding
import com.example.zulipapp.di.DaggerChatComponent
import com.example.zulipapp.presentation.chat.adapter.ChatAdapter
import com.example.zulipapp.presentation.chat.elm.ChatEffect
import com.example.zulipapp.presentation.chat.elm.ChatEvent
import com.example.zulipapp.presentation.chat.elm.ChatState
import com.example.zulipapp.presentation.chat.reactiondialog.EmojiItem
import com.example.zulipapp.presentation.chat.reactiondialog.ReactionBottomDialog
import com.example.zulipapp.presentation.main.MainActivity
import vivid.money.elmslie.android.base.ElmFragment
import vivid.money.elmslie.core.store.Store

class ChatFragment : ElmFragment<ChatEvent, ChatEffect, ChatState>(R.layout.fragment_chat){

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

    private var _binding: FragmentChatBinding? = null
    private val binding: FragmentChatBinding
        get() = _binding!!

    private lateinit var adapter: ChatAdapter

//    private var streamName: String? = null
//    private var topicName: String? = null
    private var narrows: List<Narrow> = emptyList()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentChatBinding.bind(view)

        val streamName = arguments?.getString(ARG_STREAM_KEY)
        val topicName = arguments?.getString(ARG_TOPIC_KEY)
        narrows = if(streamName == null){
            emptyList()
        } else {
            if(topicName == null){
                listOf(Narrow("stream", streamName))
            } else {
                listOf(Narrow("stream", streamName), Narrow("topic", topicName))
            }
        }

        if(topicName != null){
            binding.chatTopicHeadline.text = topicName
        } else {
            binding.chatTopicHeadline.visibility = View.GONE
        }


        binding.chatEditText.doAfterTextChanged {
            if(it.toString().isEmpty()){
                binding.chatButtonSend.background = ResourcesCompat.getDrawable(resources, R.drawable.baseline_add_circle, null)
            } else {
                binding.chatButtonSend.background = ResourcesCompat.getDrawable(resources, R.drawable.send, null)
            }
        }

        binding.chatButtonSend.setOnClickListener {
            if (streamName != null){
                store.accept(ChatEvent.Ui.SendButtonClicked("stream", streamName, topicName, binding.chatEditText.text.toString()))
            }
        }

        val manager = LinearLayoutManager(context)
        manager.stackFromEnd = true
        binding.chatRecyclerView.layoutManager = manager
        adapter = ChatAdapter({
            TODO("on Emoji Click")
        },{
            showDialog()
        },{ item, position ->
            TODO("on Message Long CLick")
            true
        },{
            if(!store.currentState.isFullyLoaded){
                store.accept(ChatEvent.Ui.LoadNextPage(narrows))
            }
        })
        binding.chatRecyclerView.adapter = adapter

        setResultListener()
        store.accept(ChatEvent.Ui.LoadFirstPage(narrows))
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun setResultListener(){
        childFragmentManager.setFragmentResultListener("reaction_selection", this){ key, bundle ->
            if(key == "reaction_selection"){
                val result = bundle.getParcelable<EmojiItem>("emoji")
                println("debug: Fragment ${result?.emojiCode} selected")
                TODO("add reaction")
//                if(result != null) addReaction(selectedMessageId, result)
            }
        }
    }

    private fun showDialog(){
        val reactionDialog = ReactionBottomDialog()
        reactionDialog.show(childFragmentManager, ReactionBottomDialog.TAG)
    }

    // ELM
    override val initEvent: ChatEvent
        get() = ChatEvent.Ui.Init

    override fun createStore(): Store<ChatEvent, ChatEffect, ChatState> {
        return DaggerChatComponent.factory()
            .create((requireActivity() as MainActivity).activityComponent).chatStore
    }

    override fun render(state: ChatState) {
        println("debug: ChatFragment: render() oldSize=${adapter.itemCount} addSize=${state.newMessages.size}")
        adapter.addMessages(state.newMessages)
    }

    override fun handleEffect(effect: ChatEffect) = when(effect){
        is ChatEffect.FullyLoaded -> {
            Toast.makeText(requireContext(), "Вы достигли конца беседы", Toast.LENGTH_SHORT).show()
        }
        is ChatEffect.ShowLoadingError -> {
            Toast.makeText(requireContext(), "Ошибка при загрузке", Toast.LENGTH_SHORT).show()
        }
        is ChatEffect.EmptyMessage -> {
            Toast.makeText(requireContext(), "Пустое сообщение", Toast.LENGTH_SHORT).show()
        }
    }
}