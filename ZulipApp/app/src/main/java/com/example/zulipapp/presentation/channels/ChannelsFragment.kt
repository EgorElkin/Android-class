package com.example.zulipapp.presentation.channels

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.RecyclerView
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
import com.google.android.material.tabs.TabLayout
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

        channelsSubscribedAdapter = ChannelsAdapter({
            store.accept(ChannelsEvent.Ui.StreamSelected(it))
        },{
            store.accept(ChannelsEvent.Ui.TopicSelected(it))
        })

        channelsAllAdapter = ChannelsAdapter({
            store.accept(ChannelsEvent.Ui.StreamSelected(it))
        },{
            store.accept(ChannelsEvent.Ui.TopicSelected(it))
        })

        viewPagerAdapter = ChannelsViewPagerAdapter(channelsSubscribedAdapter, channelsAllAdapter)
        binding.channelsViewPager.adapter = viewPagerAdapter

        binding.channelsViewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                store.accept(ChannelsEvent.Ui.TabChanged(position, binding.channelsSearchEditText.text.toString()))
            }
        })

        TabLayoutMediator(binding.channelsTabLayout, binding.channelsViewPager, object : TabLayoutMediator.TabConfigurationStrategy{
            override fun onConfigureTab(tab: TabLayout.Tab, position: Int) {
                tab.text = when(position){
                    ChannelsViewPagerAdapter.SUBSCRIBED_POSITION -> resources.getString(R.string.channels_tab_subscribed)
                    ChannelsViewPagerAdapter.ALL_STREAMS_POSITION -> resources.getString(R.string.channels_tab_all)
                    else -> throw IllegalArgumentException("TabLayoutMediator.TabConfigurationStrategy: such a position does not exist")
                }
            }
        }).attach()

        binding.channelsSearchButton.setOnClickListener {
            store.accept(ChannelsEvent.Ui.SearchButtonClicked(binding.channelsViewPager.currentItem, binding.channelsSearchEditText.text.toString()))
        }

        binding.channelsSearchEditText.doAfterTextChanged {
            store.accept(ChannelsEvent.Ui.SearchQueryChanged(binding.channelsViewPager.currentItem, it.toString()))
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

// ------------------------------ ELM --------------------------------------------
    override val initEvent: ChannelsEvent
        get() = ChannelsEvent.Ui.Init

    override fun createStore(): Store<ChannelsEvent, ChannelsEffect, ChannelsState> {
        return DaggerChannelsComponent.factory()
            .create((requireActivity() as MainActivity).activityComponent).channelsStore
    }

    override fun render(state: ChannelsState) {
        when(binding.channelsViewPager.currentItem){
            ChannelsViewPagerAdapter.SUBSCRIBED_POSITION -> {
                if(((binding.channelsViewPager.getChildAt(0) as RecyclerView).findViewHolderForAdapterPosition(0) as? ChannelsViewPagerAdapter.PageViewHolder) != null)
                    ((binding.channelsViewPager.getChildAt(0) as RecyclerView).findViewHolderForAdapterPosition(0) as ChannelsViewPagerAdapter.PageViewHolder).showLoading(state.subscribedIsLoading)

                if(!state.subscribedIsLoading)
                    channelsSubscribedAdapter.submitList(state.subscribedSearchedList)
            }
            ChannelsViewPagerAdapter.ALL_STREAMS_POSITION -> {
                if(((binding.channelsViewPager.getChildAt(0) as RecyclerView).findViewHolderForAdapterPosition(1) as? ChannelsViewPagerAdapter.PageViewHolder) != null)
                    ((binding.channelsViewPager.getChildAt(0) as RecyclerView).findViewHolderForAdapterPosition(1) as ChannelsViewPagerAdapter.PageViewHolder).showLoading(state.allIsLoading)

                if(!state.allIsLoading)
                    channelsAllAdapter.submitList(state.allSearchedList)
            }
            else -> {
                Any()
            }
        }
    }

    override fun handleEffect(effect: ChannelsEffect) = when(effect){
        is ChannelsEffect.ShowLoadingError -> {
            Toast.makeText(requireContext(), "Произошла ошибка при загрузке", Toast.LENGTH_SHORT).show()
        }
        is ChannelsEffect.NavigateToTopic -> {
            (activity as Navigator).showChat(effect.topic.streamName, effect.topic.name)
        }
        is ChannelsEffect.NavigateToStream -> {
            (activity as Navigator).showChat(effect.stream.name, null)
        }
    }
// ------------------------------ ELM END ----------------------------------------
}