package com.dotsdev.idcaller.presentation.main.calltab

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.dotsdev.idcaller.R
import com.dotsdev.idcaller.core.base.BaseFragment
import com.dotsdev.idcaller.core.base.viewBindings
import com.dotsdev.idcaller.databinding.FragmentCallListBinding
import com.dotsdev.idcaller.presentation.main.mainflow.MainFlowFragmentDirections
import org.koin.androidx.viewmodel.ext.android.viewModel

class CallListFragment :
    BaseFragment<CallListViewModel, FragmentCallListBinding>(R.layout.fragment_call_list) {
    override val viewModel by viewModel<CallListViewModel>()
    override val binding by viewBindings(FragmentCallListBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initObservers()
    }

    private fun initViews() {
        binding.viewModel = viewModel
        with(binding) {
            fab.setOnClickListener {
                findNavController().navigate(MainFlowFragmentDirections.openDialpad())
            }
        }
    }

    private fun initObservers() {
    }
}
