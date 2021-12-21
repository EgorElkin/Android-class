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
import com.example.zulipapp.presentation.chat.adapter.ChatItem
import com.example.zulipapp.presentation.chat.elm.ChatEffect
import com.example.zulipapp.presentation.chat.elm.ChatEvent
import com.example.zulipapp.presentation.chat.elm.ChatState
import com.example.zulipapp.presentation.chat.reactiondialog.EmojiItem
import com.example.zulipapp.presentation.chat.reactiondialog.ReactionBottomDialog
import com.example.zulipapp.presentation.main.MainActivity
import com.example.zulipapp.presentation.util.Constants
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

    private var selectedMessageId = -1
    private var selectedPosition = -1
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
            binding.chatTopicHeadline.text = getString(R.string.chat_topic_title, topicName)
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
                store.accept(ChatEvent.Ui.SendButtonClicked("stream", streamName, topicName, binding.chatEditText.text.toString(), narrows))
            }
        }

        val manager = LinearLayoutManager(context)
        manager.stackFromEnd = true
        binding.chatRecyclerView.layoutManager = manager
        adapter = ChatAdapter({ position, messageId, reactionItem ->
            if(reactionItem.userIds.contains(Constants.MY_ID)){
                store.accept(ChatEvent.Ui.ReactionRemoved(messageId, reactionItem.emojiName, reactionItem.emojiCode, position, narrows))
            } else {
                store.accept(ChatEvent.Ui.ReactionSelected(messageId, reactionItem.emojiName, reactionItem.emojiCode, position, narrows))
            }
        }, { position, item ->
            selectedMessageId = (item as ChatItem.MessageItem).messageId
            selectedPosition = position
            showDialog()
        }, { position, item ->
            selectedMessageId = (item as ChatItem.MessageItem).messageId
            selectedPosition = position
            showDialog()
        }, {
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
        childFragmentManager.setFragmentResultListener(ReactionBottomDialog.REQUEST_KEY, this){ key, bundle ->
            if(key == ReactionBottomDialog.REQUEST_KEY){
                val result = bundle.getParcelable<EmojiItem>(ReactionBottomDialog.BUNDLE_KEY)
                if(result != null)
                    store.accept(ChatEvent.Ui.ReactionSelected(selectedMessageId, result.emojiName, result.emojiCode, selectedPosition, narrows))
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
        adapter.addMessages(state.newMessages)
    }

    override fun handleEffect(effect: ChatEffect) = when(effect){
        is ChatEffect.ShowLoadingError -> {
            Toast.makeText(requireContext(), getString(R.string.chat_loading_error), Toast.LENGTH_SHORT).show()
        }
        is ChatEffect.EmptyMessage -> {
            Toast.makeText(requireContext(), getString(R.string.chat_empty_message), Toast.LENGTH_SHORT).show()
        }
        is ChatEffect.ClearInput -> {
            binding.chatEditText.setText("")
        }
        is ChatEffect.SendingError -> {
            Toast.makeText(requireContext(), getString(R.string.chat_sending_error), Toast.LENGTH_SHORT).show()
        }
        is ChatEffect.AddReactionError -> {
            Toast.makeText(requireContext(), getString(R.string.chat_add_reaction_error), Toast.LENGTH_SHORT).show()
        }
        is ChatEffect.RemoveReactionError -> {
            Toast.makeText(requireContext(), getString(R.string.chat_remove_reaction_error), Toast.LENGTH_SHORT).show()
        }
        is ChatEffect.RefreshMessage -> {
            adapter.refreshMessage(effect.message, effect.position)
        }
        is ChatEffect.RefreshChat -> {
            adapter.insertMessages(effect.messages)
            binding.chatRecyclerView.scrollToPosition(adapter.itemCount-1)
        }
    }
}