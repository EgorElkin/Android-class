package com.example.zulipapp.data.database

import android.content.Context

class LocalMessageDataSource(applicationContext: Context) {

    private val appDatabase = RoomBuilder.provideAppDatabase(applicationContext)


}