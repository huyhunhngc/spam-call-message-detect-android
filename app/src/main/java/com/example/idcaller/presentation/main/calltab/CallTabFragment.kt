package com.example.idcaller.presentation.main.calltab

import android.os.Bundle
import android.view.View
import com.example.idcaller.R
import com.example.idcaller.core.base.BaseFragment
import com.example.idcaller.core.base.viewBindings
import com.example.idcaller.databinding.FragmentCallTabBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class CallTabFragment: BaseFragment<CallTabViewModel, FragmentCallTabBinding>(R.layout.fragment_call_tab) {
    override val viewModel by viewModel<CallTabViewModel>()
    override val binding by viewBindings(FragmentCallTabBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initObservers()
    }

    private fun initViews() {
        binding.viewModel = viewModel
    }

    private fun initObservers() {

    }
}