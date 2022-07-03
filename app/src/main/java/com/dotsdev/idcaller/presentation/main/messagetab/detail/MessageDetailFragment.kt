package com.dotsdev.idcaller.presentation.main.messagetab.detail

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.navigation.fragment.navArgs
import com.dotsdev.idcaller.R
import com.dotsdev.idcaller.core.base.BaseFragment
import com.dotsdev.idcaller.core.base.viewBindings
import com.dotsdev.idcaller.databinding.FragmentMessageDetailBinding
import com.dotsdev.idcaller.presentation.template.NormalAppbarActivity.Companion.DATA_FROM_KEY
import com.dotsdev.idcaller.utils.setActionBarTitle
import com.dotsdev.idcaller.utils.showActionBar
import com.dotsdev.idcaller.widget.recycler.FromData
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class MessageDetailFragment :
    BaseFragment<MessageDetailViewModel, FragmentMessageDetailBinding>(R.layout.fragment_message_detail) {
    private val safeArgs: MessageDetailFragmentArgs by navArgs()
    override val viewModel by viewModel<MessageDetailViewModel> {
        parametersOf(
            (arguments?.get(DATA_FROM_KEY) as? FromData.FromMessageGroup)?.messageGroup
                ?: safeArgs.messageGroup
        )
    }
    override val binding by viewBindings(FragmentMessageDetailBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initObservers()
    }

    private fun initViews() {
        setHasOptionsMenu(true)
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_message_detail, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            else -> super.onOptionsItemSelected(item)
        }
    }
}
