package com.example.zulipapp.presentation.people.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.zulipapp.R

class PeopleAdapter(private val onUserClick: (UserItem) -> Unit): ListAdapter<UserItem, PeopleAdapter.PeopleViewHolder>(UserItemDiffCallback) {

    class PeopleViewHolder(itemView: View, clickAtPosition: (Int) -> Unit) : RecyclerView.ViewHolder(itemView){

        private val avatar: ImageView = itemView.findViewById(R.id.peopleItemAvatar)
        private val name: TextView = itemView.findViewById(R.id.peopleItemName)
        private val email: TextView = itemView.findViewById(R.id.peopleItemEmail)

        init {
            itemView.setOnClickListener {
                clickAtPosition(adapterPosition)
            }
        }

        fun bind(user: UserItem){
            Glide.with(avatar)
                .load(user.avatarUrl)
                .circleCrop()
                .into(avatar)
            name.text = user.userName
            email.text = user.userEmail
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PeopleViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.people_user_item, parent, false)
        return PeopleViewHolder(view){
            onUserClick(getItem(it))
        }
    }

    override fun onBindViewHolder(holder: PeopleViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

object UserItemDiffCallback : DiffUtil.ItemCallback<UserItem>(){

    override fun areItemsTheSame(oldItem: UserItem, newItem: UserItem): Boolean {
        return newItem.userId == oldItem.userId
    }

    override fun areContentsTheSame(oldItem: UserItem, newItem: UserItem): Boolean {
        return newItem == oldItem
    }
}