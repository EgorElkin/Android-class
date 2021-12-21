package com.example.zulipapp.presentation.profile

import com.example.zulipapp.R

class ProfileItem(
    val id: Int,
    val name: String,
    val avatarUrl:String,
    var status: String
){
    companion object{
        const val STATUS_ONLINE = "active"
        const val STATUS_IDLE = "idle"
        const val STATUS_OFFLINE = "offline"

        const val STATUS_ONLINE_COLOR = R.color.online
        const val STATUS_IDLE_COLOR = R.color.idle
        const val STATUS_OFFLINE_COLOR = R.color.offline
    }
}