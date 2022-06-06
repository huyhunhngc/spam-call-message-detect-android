package com.dotsdev.idcaller.presentation.main.messagetab

import android.os.Bundle
import android.view.View
import com.dotsdev.idcaller.R
import com.dotsdev.idcaller.core.base.BaseFragment
import com.dotsdev.idcaller.core.base.viewBindings
import com.dotsdev.idcaller.databinding.FragmentMessageDetailBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class MessageDetailFragment :
    BaseFragment<MessageDetailViewModel, FragmentMessageDetailBinding>(R.layout.fragment_message_detail) {
    override val viewModel by viewModel<MessageDetailViewModel>()
    override val binding by viewBindings(FragmentMessageDetailBinding::bind)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {
        with(binding) {
            viewModel = this@MessageDetailFragment.viewModel
        }
    }
}