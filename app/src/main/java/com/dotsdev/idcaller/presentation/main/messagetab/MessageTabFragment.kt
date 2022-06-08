package com.dotsdev.idcaller.presentation.main.messagetab

import android.os.Bundle
import android.view.View
import com.dotsdev.idcaller.R
import com.dotsdev.idcaller.adapter.MessageViewPagerAdapter
import com.dotsdev.idcaller.core.base.BaseFragment
import com.dotsdev.idcaller.core.base.viewBindings
import com.dotsdev.idcaller.databinding.FragmentMessageTabBinding
import com.google.android.material.tabs.TabLayoutMediator
import org.koin.androidx.viewmodel.ext.android.viewModel

class MessageTabFragment :
    BaseFragment<MessageTabViewModel, FragmentMessageTabBinding>(R.layout.fragment_message_tab) {
    var tabTitle = arrayOf("Inbox", "Important", "Spam")

    override val viewModel: MessageTabViewModel by viewModel()
    override val binding: FragmentMessageTabBinding by viewBindings {
        FragmentMessageTabBinding.bind(it)
    }

    companion object {
        fun newInstance(): MessageTabFragment {
            return MessageTabFragment()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {
        with(binding) {
            viewPager.adapter =
                activity?.let { MessageViewPagerAdapter(it.supportFragmentManager, lifecycle) }
            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                tab.text = tabTitle[position]
            }.attach()
        }
    }
}