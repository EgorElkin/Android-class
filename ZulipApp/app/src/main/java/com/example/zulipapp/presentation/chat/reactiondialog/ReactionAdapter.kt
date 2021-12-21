package com.example.zulipapp.presentation.chat.reactiondialog

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.zulipapp.R

class ReactionAdapter(
    private val emojiList: List<EmojiItem>,
    private val onEmojiClick: (EmojiItem) -> Unit
) : RecyclerView.Adapter<ReactionAdapter.ReactionViewHolder>() {

    class ReactionViewHolder(itemView: View, clickAtPosition: (Int) -> Unit) : RecyclerView.ViewHolder(itemView) {

        private val emojiView: TextView = itemView.findViewById(R.id.bottom_sheet_item)

        init {
            itemView.setOnClickListener{
                clickAtPosition(adapterPosition)
            }
        }

        fun bind(emoji: EmojiItem) {
            emojiView.text = emoji.emojiUniCode
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReactionViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.bottom_sheet_item, parent, false)
        return ReactionViewHolder(view){
            onEmojiClick(emojiList[it])
        }
    }

    override fun onBindViewHolder(holder: ReactionViewHolder, position: Int) {
        holder.bind(emojiList[position])
    }

    override fun getItemCount(): Int {
        return emojiList.size
    }
}