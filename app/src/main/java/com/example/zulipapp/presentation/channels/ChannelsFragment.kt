package com.example.zulipapp.presentation.channels

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.zulipapp.R
import com.example.zulipapp.presentation.Navigator
import com.example.zulipapp.presentation.channels.adapter.ChannelsAdapter
import com.example.zulipapp.presentation.channels.adapter.ChannelsItem
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class ChannelsFragment : Fragment(R.layout.fragment_channels), ChannelsView {

    private lateinit var presenter: ChannelsPresenterImpl

    private lateinit var editText: EditText
    private lateinit var searchButton: ImageButton
    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout

    private lateinit var viewPagerAdapter: ChannelsViewPagerAdapter
    private lateinit var channelsSubscribedAdapter: ChannelsAdapter
    private lateinit var channelsAllAdapter: ChannelsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        editText = view.findViewById(R.id.channelsSearchEditText)
        editText.doAfterTextChanged {
            presenter.searchRequestChanged(it.toString())
        }

        searchButton = view.findViewById(R.id.channelsSearchButton)
        searchButton.setOnClickListener {
            presenter.searchButtonClicked(editText.text.toString())
        }

        tabLayout = view.findViewById(R.id.channelsTabLayout)
        viewPager = view.findViewById(R.id.channelsViewPager)

        channelsSubscribedAdapter = ChannelsAdapter({
            presenter.streamSelected(it.name)
        },{
            presenter.topicSelected(it.streamName, it.name)
        })
        channelsAllAdapter = ChannelsAdapter({
            presenter.streamSelected(it.name)
        },{
            presenter.topicSelected(it.streamName, it.name)
        })
        viewPagerAdapter = ChannelsViewPagerAdapter(channelsSubscribedAdapter, channelsAllAdapter)
        viewPager.adapter = viewPagerAdapter

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                when(position){
                    ChannelsViewPagerAdapter.SUBSCRIBED_FRAGMENT_POSITION -> {
                        presenter.subscribedStreamsListSelected(editText.text.toString())
                    }
                    ChannelsViewPagerAdapter.ALL_STREAMS_FRAGMENT_POSITION -> {
                        presenter.allStreamsListSelected(editText.text.toString())
                    }
                }
            }
        })

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = when (position) {
                ChannelsViewPagerAdapter.SUBSCRIBED_FRAGMENT_POSITION -> resources.getString(R.string.channels_tab_subscribed)
                ChannelsViewPagerAdapter.ALL_STREAMS_FRAGMENT_POSITION -> resources.getString(R.string.channels_tab_all)
                else -> throw IllegalArgumentException("TabLayoutMediator.TabConfigurationStrategy: such a position does not exist")
            }
        }.attach()

        presenter = ChannelsPresenterImpl(this, (this.activity as Navigator))
        presenter.attachView(this)
        presenter.viewIsReady()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }

    // ChannelsView
    override fun showAllStreams(streams: List<ChannelsItem>) {
        viewPagerAdapter.setAllStreams(streams)
    }

    override fun showSubscribedStreams(streams: List<ChannelsItem>) {
        viewPagerAdapter.setSubscribedStreams(streams)
    }

    override fun showAllLoading() {
        TODO("Not yet implemented")
    }

    override fun hideAllLoading() {
        TODO("Not yet implemented")
    }

    override fun showSubscribedLoading() {
        TODO("Not yet implemented")
    }

    override fun hideSubscribedLoading() {
        TODO("Not yet implemented")
    }

    override fun showError(message: Int) {
        Toast.makeText(context, getString(message), Toast.LENGTH_SHORT).show()
    }
}