package com.example.zulipapp.presentation

import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import com.example.zulipapp.R
import com.example.zulipapp.presentation.profile.ProfileFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        window.statusBarColor = ContextCompat.getColor(this, R.color.black)
        if(savedInstanceState == null){
            supportFragmentManager.beginTransaction()
                .replace(R.id.mainFragmentContainer, ProfileFragment())
                .commit()
        }

//        findViewById<Toolbar>(R.id.mainToolbar).visibility = View.GONE
//        findViewById<BottomNavigationView>(R.id.mainBottomNavigation).visibility = View.GONE
        setSupportActionBar(findViewById(R.id.mainToolbar))
//        supportActionBar?.hide()
//        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            else -> {
                supportActionBar?.hide()
                println("debug: else")
                true
            }
        }
    }
}