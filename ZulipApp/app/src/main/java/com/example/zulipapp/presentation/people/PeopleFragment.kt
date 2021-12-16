package com.example.zulipapp.presentation.people

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import com.example.zulipapp.R
import com.example.zulipapp.databinding.FragmentPeopleBinding
import com.example.zulipapp.di.DaggerPeopleComponent
import com.example.zulipapp.presentation.Navigator
import com.example.zulipapp.presentation.main.MainActivity
import com.example.zulipapp.presentation.people.adapter.PeopleAdapter
import com.example.zulipapp.presentation.people.elm.PeopleEffect
import com.example.zulipapp.presentation.people.elm.PeopleEvent
import com.example.zulipapp.presentation.people.elm.PeopleState
import vivid.money.elmslie.android.base.ElmFragment
import vivid.money.elmslie.core.store.Store

class PeopleFragment : ElmFragment<PeopleEvent, PeopleEffect, PeopleState>(R.layout.fragment_people){

    private var _binding: FragmentPeopleBinding? = null
    private val binding: FragmentPeopleBinding
        get() = _binding!!

    private lateinit var adapter: PeopleAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentPeopleBinding.bind(view)

        binding.peopleSearchEditText.doAfterTextChanged {
            store.accept(PeopleEvent.Ui.SearchPeople(it.toString()))
        }

        binding.peopleSearchButton.setOnClickListener {
            store.accept(PeopleEvent.Ui.SearchPeople(binding.peopleSearchEditText.text.toString()))
        }

        binding.peopleRecyclerVIew.setHasFixedSize(true)
        adapter = PeopleAdapter {
            store.accept(PeopleEvent.Ui.UserSelected(it.userId))

        }
        binding.peopleRecyclerVIew.adapter = adapter
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    // ELM
    override val initEvent: PeopleEvent
        get() = PeopleEvent.Ui.Init

    override fun createStore(): Store<PeopleEvent, PeopleEffect, PeopleState> {
        return DaggerPeopleComponent.factory()
            .create((requireActivity() as MainActivity).activityComponent).peopleStore
    }

    override fun render(state: PeopleState) {
        binding.peopleProgressBar.isVisible = state.isLoading
        binding.peopleEmptyListTextView.isVisible = state.emptyList
        binding.peopleEmptySearchTextView.isVisible = state.emptySearchResult
        if(state.searchedUsers != null){
            adapter.submitList(state.searchedUsers)
        }
    }

    override fun handleEffect(effect: PeopleEffect) = when(effect){
        is PeopleEffect.ShowLoadingError -> {
            Toast.makeText(requireContext(), getString(R.string.people_loading_error), Toast.LENGTH_SHORT).show()
        }
        is PeopleEffect.NavigateToProfile -> {
            (requireActivity() as Navigator).showProfile(effect.userId)
        }
    }
}