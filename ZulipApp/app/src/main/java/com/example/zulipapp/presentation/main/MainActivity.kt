package com.example.zulipapp.presentation.main

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.example.zulipapp.App
import com.example.zulipapp.R
import com.example.zulipapp.di.ActivityComponent
import com.example.zulipapp.di.DaggerActivityComponent
import com.example.zulipapp.presentation.Navigator
import com.example.zulipapp.presentation.channels.ChannelsFragment
import com.example.zulipapp.presentation.chat.ChatFragment
import com.example.zulipapp.presentation.people.PeopleFragment
import com.example.zulipapp.presentation.profile.ProfileFragment
import com.example.zulipapp.presentation.util.Constants
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() , Navigator{

    private lateinit var navigation: BottomNavigationView
    private lateinit var toolBar: Toolbar

    lateinit var activityComponent: ActivityComponent

    override fun onCreate(savedInstanceState: Bundle?) {
        activityComponent = DaggerActivityComponent.factory().create((application as App).appComponent)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navigation = findViewById(R.id.mainBottomNavigation)
        val fragmentManager = supportFragmentManager

        navigation.setOnItemSelectedListener { item ->
            when(item.itemId){
                R.id.menuChannels -> {
                    fragmentManager.beginTransaction()
                        .replace(R.id.mainFragmentContainer, ChannelsFragment(), "channels_fragment")
                        .addToBackStack("channels_backstack")
                        .commitAllowingStateLoss()
                    true
                }
                R.id.menuPeople -> {
                    fragmentManager.beginTransaction()
                        .replace(R.id.mainFragmentContainer, PeopleFragment(), "people_fragment")
                        .addToBackStack("people_backstack")
                        .commitAllowingStateLoss()
                    true
                }
                R.id.menuProfile -> {
                    fragmentManager.beginTransaction()
                        .replace(R.id.mainFragmentContainer, ProfileFragment.newInstance(Constants.MY_ID), "profile_fragment")
                        .addToBackStack("profile_backstack")
                        .commitAllowingStateLoss()
                    true
                }
                else -> throw IllegalArgumentException("Bottom Navigation: Such menu item is not exist")
            }
        }

        if(savedInstanceState == null){
            fragmentManager.beginTransaction()
                .replace(R.id.mainFragmentContainer, ChannelsFragment(), "channels_fragment")
                .commitAllowingStateLoss()
        }

        toolBar = findViewById(R.id.mainToolbar)
        setSupportActionBar(toolBar)
        supportActionBar?.hide()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        supportActionBar?.hide()
        navigation.visibility = View.VISIBLE
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            else -> {
                onBackPressed()
                true
            }
        }
    }

    // Navigator
    override fun navigateToChannels() {
        navigation.selectedItemId = R.id.menuChannels
    }

    override fun navigateToPeople() {
        navigation.selectedItemId = R.id.menuPeople
    }

    override fun navigateToProfile() {
        navigation.selectedItemId = R.id.menuProfile
    }

    override fun showProfile(userId: Int) {
        supportFragmentManager.beginTransaction()
            .add(R.id.mainFragmentContainer, ProfileFragment.newInstance(userId))
            .addToBackStack(null)
            .commitAllowingStateLoss()

        window.statusBarColor = ContextCompat.getColor(this, R.color.background_gray_dark)
        toolBar.setBackgroundColor(ResourcesCompat.getColor(resources, R.color.background_gray_dark, null))
        supportActionBar?.title = getString(R.string.profile_toolbar_title)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.show()
        navigation.visibility = View.GONE
    }

    override fun showChat(streamName: String, topicName: String?) {
        supportFragmentManager.beginTransaction()
            .add(R.id.mainFragmentContainer, ChatFragment.newInstance(streamName, topicName))
            .addToBackStack(null)
            .commitAllowingStateLoss()

        window.statusBarColor = ContextCompat.getColor(this, R.color.green_common)
        toolBar.setBackgroundColor(ResourcesCompat.getColor(resources, R.color.green_common, null))
        supportActionBar?.title = getString(R.string.chat_toolbar_title, streamName)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.show()
        navigation.visibility = View.GONE
    }
}