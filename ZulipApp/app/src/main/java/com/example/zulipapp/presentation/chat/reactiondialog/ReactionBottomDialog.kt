package com.example.zulipapp.presentation.chat.reactiondialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.zulipapp.R
import com.example.zulipapp.presentation.util.EmojiCodeMapper
import com.example.zulipapp.presentation.util.EmojiContainer
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ReactionBottomDialog : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_bottom_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val recyclerView: RecyclerView = view.findViewById(R.id.bottomSheetRecyclerView )
        val gridLayoutManager = GridLayoutManager(context, columnCount, GridLayoutManager.VERTICAL, false)
        val map = EmojiContainer.getEmojisMap(requireContext())
        val adapter = ReactionAdapter(map.map{ EmojiItem(it.key, it.value, EmojiCodeMapper.codeToEmoji(it.value)) }){
            parentFragmentManager.setFragmentResult(REQUEST_KEY, bundleOf(BUNDLE_KEY to it))
            dismissAllowingStateLoss()
        }
        recyclerView.layoutManager = gridLayoutManager
        recyclerView.adapter = adapter
    }

    companion object {
        const val TAG = "ReactionBottomDialog"
        const val REQUEST_KEY = "reaction_selection"
        const val BUNDLE_KEY = "emoji"
        private const val columnCount = 7
    }
}