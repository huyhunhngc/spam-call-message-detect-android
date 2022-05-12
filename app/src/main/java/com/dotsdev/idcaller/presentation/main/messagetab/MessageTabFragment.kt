package com.dotsdev.idcaller.presentation.main.messagetab

import com.dotsdev.idcaller.R
import com.dotsdev.idcaller.core.base.BaseFragment
import com.dotsdev.idcaller.core.base.viewBindings
import com.dotsdev.idcaller.databinding.FragmentMessageTabBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class MessageTabFragment :
    BaseFragment<MessageTabViewModel, FragmentMessageTabBinding>(R.layout.fragment_message_tab) {
    override val viewModel: MessageTabViewModel by viewModel()
    override val binding: FragmentMessageTabBinding by viewBindings {
        FragmentMessageTabBinding.bind(it)
    }

    companion object {
        fun newInstance(): MessageTabFragment{
           return MessageTabFragment()
        }
    }
}