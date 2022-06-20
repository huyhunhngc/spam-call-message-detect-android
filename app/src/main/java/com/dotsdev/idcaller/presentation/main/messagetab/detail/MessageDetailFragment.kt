package com.dotsdev.idcaller.presentation.main.messagetab.detail

import android.os.Bundle
import android.view.View
import com.dotsdev.idcaller.R
import com.dotsdev.idcaller.core.base.BaseFragment
import com.dotsdev.idcaller.core.base.viewBindings
import com.dotsdev.idcaller.data.model.MessageGroup
import com.dotsdev.idcaller.databinding.FragmentMessageDetailBinding
import com.dotsdev.idcaller.presentation.template.NormalAppbarActivity.Companion.DATA_FROM_KEY
import com.dotsdev.idcaller.utils.setActionBarTitle
import com.dotsdev.idcaller.utils.showActionBar
import com.dotsdev.idcaller.widget.recycler.FromData
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class MessageDetailFragment :
    BaseFragment<MessageDetailViewModel, FragmentMessageDetailBinding>(R.layout.fragment_message_detail) {
    override val viewModel by viewModel<MessageDetailViewModel> {
        parametersOf((arguments?.get(DATA_FROM_KEY) as? FromData.FromMessageGroup)?.messageGroup)
    }
    override val binding by viewBindings(FragmentMessageDetailBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initObservers()
    }

    private fun initViews() {
        showActionBar()
        with(binding) {
            viewModel = this@MessageDetailFragment.viewModel.apply {
                init()
            }
        }
    }

    private fun initObservers() {
        with(viewModel) {
            messageTitle.observe(viewLifecycleOwner) {
                setActionBarTitle(it)
            }
        }
    }
}
