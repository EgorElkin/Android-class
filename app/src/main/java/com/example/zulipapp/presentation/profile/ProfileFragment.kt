package com.example.zulipapp.presentation.profile

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.zulipapp.R

class  ProfileFragment: Fragment(R.layout.fragment_profile), ProfileView {

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

    private lateinit var presenter: ProfilePresenterImpl

    private lateinit var avatar: ImageView
    private lateinit var name: TextView
    private lateinit var status: TextView
    private lateinit var progressBar: ProgressBar

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        avatar = view.findViewById(R.id.profileAvatarImageView)
        name = view.findViewById(R.id.profileNameTextView)
        status = view.findViewById(R.id.profileStatusTextVIew)
        progressBar = view.findViewById(R.id.profileProgressBar)

        presenter = ProfilePresenterImpl(this)
        presenter.attachView(this)
        presenter.viewIsReady(arguments?.getInt(ARG_KEY) ?: DEFAULT_ID)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }

    override fun showUser(profile: ProfileItem) {
        Glide.with(avatar)
            .load(profile.avatarUrl)
            .transform(RoundedCorners(resources.getDimension(R.dimen.profile_avatar_corner_radius).toInt()))
            .into(avatar)
        name.text = profile.name
        when(profile.status){
            ProfileItem.STATUS_ONLINE -> {
                status.setTextColor(ResourcesCompat.getColor(resources, ProfileItem.STATUS_ONLINE_COLOR, null))
                status.text = ProfileItem.STATUS_ONLINE
            }
            ProfileItem.STATUS_IDLE -> {
                status.setTextColor(ResourcesCompat.getColor(resources, ProfileItem.STATUS_IDLE_COLOR, null))
                status.text = ProfileItem.STATUS_IDLE
            }
            ProfileItem.STATUS_OFFLINE -> {
                status.setTextColor(ResourcesCompat.getColor(resources, ProfileItem.STATUS_OFFLINE_COLOR, null))
                status.text = ProfileItem.STATUS_OFFLINE
            }
        }
    }

    override fun showError(errorRes: Int) {
        Toast.makeText(context, getString(errorRes), Toast.LENGTH_SHORT).show()
    }

    override fun showLoading() {
        progressBar.isVisible = true
    }

    override fun hideLoading() {
        progressBar.isVisible = false
    }
}