package com.example.idcaller.presentation.main.messagetab

import com.example.idcaller.R
import com.example.idcaller.core.base.BaseFragment
import com.example.idcaller.core.base.viewBindings
import com.example.idcaller.databinding.FragmentMessageTabBinding
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