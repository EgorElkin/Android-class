package com.example.zulipapp.presentation.channels

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.viewpager2.widget.ViewPager2
import com.example.zulipapp.R
import com.example.zulipapp.databinding.FragmentChannelsBinding
import com.example.zulipapp.di.DaggerChannelsComponent
import com.example.zulipapp.presentation.Navigator
import com.example.zulipapp.presentation.channels.adapter.ChannelsAdapter
import com.example.zulipapp.presentation.channels.elm.ChannelsEffect
import com.example.zulipapp.presentation.channels.elm.ChannelsEvent
import com.example.zulipapp.presentation.channels.elm.ChannelsState
import com.example.zulipapp.presentation.main.MainActivity
import com.google.android.material.tabs.TabLayoutMediator
import vivid.money.elmslie.android.base.ElmFragment
import vivid.money.elmslie.core.store.Store

class ChannelsFragment : ElmFragment<ChannelsEvent, ChannelsEffect, ChannelsState>(R.layout.fragment_channels) {

    private var _binding: FragmentChannelsBinding? = null
    private val binding: FragmentChannelsBinding
        get() = _binding!!

    private lateinit var viewPagerAdapter: ChannelsViewPagerAdapter
    private lateinit var channelsSubscribedAdapter: ChannelsAdapter
    private lateinit var channelsAllAdapter: ChannelsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentChannelsBinding.bind(view)

        binding.channelsSearchEditText.doAfterTextChanged {
            store.accept(ChannelsEvent.Ui.SearchQueryChanged(binding.channelsViewPager.currentItem, it.toString()))
        }

        binding.channelsSearchButton.setOnClickListener {
            store.accept(ChannelsEvent.Ui.SearchButtonClicked(binding.channelsViewPager.currentItem, binding.channelsSearchEditText.text.toString()))
        }

        channelsSubscribedAdapter = ChannelsAdapter({
            store.accept(ChannelsEvent.Ui.StreamSelected(it))
        },{
            store.accept(ChannelsEvent.Ui.TopicSelected(it.streamName, it))
        })

        channelsAllAdapter = ChannelsAdapter({
            store.accept(ChannelsEvent.Ui.StreamSelected(it))
        },{
            store.accept(ChannelsEvent.Ui.TopicSelected(it.streamName, it))
        })

        viewPagerAdapter = ChannelsViewPagerAdapter(channelsSubscribedAdapter, channelsAllAdapter)
        binding.channelsViewPager.adapter = viewPagerAdapter

        binding.channelsViewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                store.accept(ChannelsEvent.Ui.TabChanged(position, binding.channelsSearchEditText.text.toString()))
            }
        })

        TabLayoutMediator(binding.channelsTabLayout, binding.channelsViewPager) { tab, position ->
            tab.text = when (position) {
                ChannelsViewPagerAdapter.SUBSCRIBED_FRAGMENT_POSITION -> resources.getString(R.string.channels_tab_subscribed)
                ChannelsViewPagerAdapter.ALL_STREAMS_FRAGMENT_POSITION -> resources.getString(R.string.channels_tab_all)
                else -> throw IllegalArgumentException("TabLayoutMediator.TabConfigurationStrategy: such a position does not exist")
            }
        }.attach()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    // ELM
    override val initEvent: ChannelsEvent
        get() = ChannelsEvent.Ui.Init

    override fun createStore(): Store<ChannelsEvent, ChannelsEffect, ChannelsState> {
        return DaggerChannelsComponent.factory()
            .create((requireActivity() as MainActivity).activityComponent).channelsStore
    }

    override fun render(state: ChannelsState) {
        when(binding.channelsTabLayout.selectedTabPosition){
            ChannelsViewPagerAdapter.SUBSCRIBED_FRAGMENT_POSITION -> {
                if(state.searchedSubscribedStreams != null) {
                    viewPagerAdapter.setSubscribedStreams(state.searchedSubscribedStreams)
                }
            }
            ChannelsViewPagerAdapter.ALL_STREAMS_FRAGMENT_POSITION -> {
                    if(state.searchedAllStreams != null) {
                    viewPagerAdapter.setAllStreams(state.searchedAllStreams)
                }
            }
        }
    }

    override fun handleEffect(effect: ChannelsEffect) = when(effect){
        is ChannelsEffect.ShowLoadingError -> {
            Toast.makeText(context, getString(R.string.channels_loading_error), Toast.LENGTH_SHORT).show()
        }
        is ChannelsEffect.NavigateToStream -> {
            (requireActivity() as Navigator).showChat(effect.stream.name, null)
        }
        is ChannelsEffect.NavigateToTopic -> {
            (requireActivity() as Navigator).showChat(effect.topic.streamName, effect.topic.name)
        }
    }
}