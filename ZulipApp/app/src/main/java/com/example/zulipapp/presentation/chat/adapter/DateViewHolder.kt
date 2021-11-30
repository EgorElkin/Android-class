package com.example.zulipapp.presentation.chat.adapter

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.zulipapp.R

class DateViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val textView: TextView = itemView.findViewById(R.id.dateTextView)

    fun bind(chatItem: ChatItem.DateItem){
        textView.text = chatItem.date
    }
}