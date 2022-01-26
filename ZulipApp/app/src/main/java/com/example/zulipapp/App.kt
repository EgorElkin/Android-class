package com.example.zulipapp

import android.app.Application
import com.example.zulipapp.di.AppComponent
import com.example.zulipapp.di.DaggerAppComponent

class App : Application() {

    private var _appComponent: AppComponent? = null
    internal val appComponent: AppComponent
        get() = checkNotNull(_appComponent){
                "AppComponent isn't initialized"
        }

    override fun onCreate() {
        super.onCreate()
        _appComponent = DaggerAppComponent.factory().create(applicationContext)
    }
}