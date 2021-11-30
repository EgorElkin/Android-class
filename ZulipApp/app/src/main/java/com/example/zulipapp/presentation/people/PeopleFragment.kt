package com.example.zulipapp.presentation.people

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.zulipapp.R
import com.example.zulipapp.presentation.Navigator
import com.example.zulipapp.presentation.people.adapter.PeopleAdapter
import com.example.zulipapp.presentation.people.adapter.UserItem

class PeopleFragment : Fragment(R.layout.fragment_people), PeopleView {

    private lateinit var presenter: PeoplePresenterImpl

    private lateinit var editText: EditText
    private lateinit var searchButton: ImageButton
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var adapter: PeopleAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        editText = view.findViewById(R.id.peopleSearchEditText)
        editText.doAfterTextChanged {
            presenter.searchRequestChanged(it.toString())
        }

        searchButton = view.findViewById(R.id.peopleSearchButton)
        searchButton.setOnClickListener {
            presenter.searchRequestChanged(editText.text.toString())
        }

        recyclerView = view.findViewById(R.id.peopleRecyclerVIew)
        recyclerView.setHasFixedSize(true)
        adapter = PeopleAdapter {
            presenter.userSelected(it.userId)
        }
        recyclerView.adapter = adapter

        progressBar = view.findViewById(R.id.peopleProgressBar)

        presenter = PeoplePresenterImpl(this, (this.activity as Navigator))
        presenter.attachView(this)
        presenter.viewIsReady()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }

    // PeopleView
    override fun showUsers(users: List<UserItem>) {
        adapter.submitList(users)
    }

    override fun showLoading() {
        progressBar.isVisible = true
    }

    override fun hideLoading() {
        progressBar.isVisible = false
    }

    override fun showError(message: Int) {
        Toast.makeText(requireContext(), getString(message), Toast.LENGTH_SHORT).show()
    }
}