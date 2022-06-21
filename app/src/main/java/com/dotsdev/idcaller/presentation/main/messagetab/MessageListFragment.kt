package com.dotsdev.idcaller.presentation.main.messagetab

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import com.dotsdev.idcaller.R
import com.dotsdev.idcaller.adapter.MessageViewPagerAdapter
import com.dotsdev.idcaller.core.base.BaseFragment
import com.dotsdev.idcaller.core.base.viewBindings
import com.dotsdev.idcaller.databinding.FragmentMessageListBinding
import com.google.android.material.tabs.TabLayoutMediator
import org.koin.androidx.viewmodel.ext.android.viewModel

class MessageListFragment :
    BaseFragment<MessageListViewModel, FragmentMessageListBinding>(R.layout.fragment_message_list) {
    override val viewModel: MessageListViewModel by viewModel()
    override val binding: FragmentMessageListBinding by viewBindings {
        FragmentMessageListBinding.bind(it)
    }
    private val tabTitle = listOf(
        Pair("Inbox", R.drawable.ic_baseline_inbox_24),
        Pair("Important", R.drawable.ic_baseline_star_24),
        Pair("Spam", R.drawable.ic_baseline_app_blocking_24)
    )
    private var mediator: TabLayoutMediator? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun initViews() {
        with(binding) {
            viewPager.adapter =
                MessageViewPagerAdapter(childFragmentManager, viewLifecycleOwner.lifecycle)
            mediator = TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                tab.text = tabTitle[position].first
                tab.icon = context?.getDrawable(tabTitle[position].second)
            }
            mediator?.attach()
        }
    }

    override fun onDestroy() {
        mediator?.detach()
        super.onDestroy()
    }
}