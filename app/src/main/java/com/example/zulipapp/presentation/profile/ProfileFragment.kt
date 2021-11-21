package com.example.zulipapp.presentation.profile

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.zulipapp.R

class ProfileFragment: Fragment(R.layout.fragment_profile), ProfileView {

    private val presenter = ProfilePresenterImpl()

    private lateinit var name: TextView
    private lateinit var button: Button

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        println("debug: onViewCreated $view")
//        name = view.findViewById<TextView>(R.id.nameTextView)
//        button = view.findViewById<Button>(R.id.nameButton)
//
//        button.setOnClickListener {
//            presenter.loadUser()
//        }

        presenter.attachView(this)
    }

    override fun showUser(userName: String) {
        name.text = userName
    }

    override fun showError(message: String) {
        name.text = message
    }

    override fun onDestroy() {
        super.onDestroy()
        println("debug: onDestroy")
        presenter.detachView(this)
    }
}