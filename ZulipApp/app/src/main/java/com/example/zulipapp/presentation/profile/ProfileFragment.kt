package com.example.zulipapp.presentation.profile

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.zulipapp.R
import com.example.zulipapp.databinding.FragmentProfileBinding
import com.example.zulipapp.di.DaggerProfileComponent
import com.example.zulipapp.presentation.main.MainActivity
import com.example.zulipapp.presentation.profile.elm.ProfileCommand
import com.example.zulipapp.presentation.profile.elm.ProfileEffect
import com.example.zulipapp.presentation.profile.elm.ProfileEvent
import com.example.zulipapp.presentation.profile.elm.ProfileState
import vivid.money.elmslie.android.base.ElmFragment
import vivid.money.elmslie.core.ElmStoreCompat
import vivid.money.elmslie.core.store.Store
import javax.inject.Inject

class  ProfileFragment : ElmFragment<ProfileEvent, ProfileEffect, ProfileState>(R.layout.fragment_profile){

    companion object{
        const val ARG_KEY = "profile_arguments_key"
        const val DEFAULT_ID = 0

        fun newInstance(userId: Int): ProfileFragment{
            val fragment = ProfileFragment()
            val arguments = Bundle()
            arguments.putInt(ARG_KEY, userId)
            fragment.arguments = arguments
            return fragment
        }
    }

    private var _binding: FragmentProfileBinding? = null
    private val binding: FragmentProfileBinding
        get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentProfileBinding.bind(view)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    // ELM
    override val initEvent: ProfileEvent
        get() = ProfileEvent.Ui.Init(arguments?.getInt(ARG_KEY) ?: DEFAULT_ID)

    override fun createStore(): Store<ProfileEvent, ProfileEffect, ProfileState> {
        return DaggerProfileComponent.factory()
            .create((requireActivity() as MainActivity).activityComponent).profileStore
    }

    override fun render(state: ProfileState) {
        if(state.isLoading){
//            binding.profileProgressBar.isVisible = true
            binding.profileShimmer.startShimmer()
            binding.profileShimmer.showShimmer(true)
        } else {
            if(state.profile == null){
//                binding.profileProgressBar.isVisible = false
                binding.profileShimmer.stopShimmer()
                binding.profileShimmer.hideShimmer()
            } else {
                val profile = state.profile
//                binding.profileProgressBar.isVisible = false
                binding.profileShimmer.stopShimmer()
                binding.profileShimmer.hideShimmer()
                Glide.with(binding.profileAvatarImageView)
                    .load(profile.avatarUrl)
                    .transform(RoundedCorners(resources.getDimension(R.dimen.profile_avatar_corner_radius).toInt()))
                    .into(binding.profileAvatarImageView)
                binding.profileNameTextView.text = profile.name
                when(profile.status){
                    ProfileItem.STATUS_ONLINE -> {
                        binding.profileStatusTextVIew.setTextColor(ResourcesCompat.getColor(resources, ProfileItem.STATUS_ONLINE_COLOR, null))
                        binding.profileStatusTextVIew.text = ProfileItem.STATUS_ONLINE
                    }
                    ProfileItem.STATUS_IDLE -> {
                        binding.profileStatusTextVIew.setTextColor(ResourcesCompat.getColor(resources, ProfileItem.STATUS_IDLE_COLOR, null))
                        binding.profileStatusTextVIew.text = ProfileItem.STATUS_IDLE
                    }
                    ProfileItem.STATUS_OFFLINE -> {
                        binding.profileStatusTextVIew.setTextColor(ResourcesCompat.getColor(resources, ProfileItem.STATUS_OFFLINE_COLOR, null))
                        binding.profileStatusTextVIew.text = ProfileItem.STATUS_OFFLINE
                    }
                }
            }
        }
    }

    override fun handleEffect(effect: ProfileEffect): Unit? = when(effect){
        is ProfileEffect.ShowError -> {
            Toast.makeText(context, getString(R.string.profile_loading_error), Toast.LENGTH_SHORT).show()
        }
    }
}