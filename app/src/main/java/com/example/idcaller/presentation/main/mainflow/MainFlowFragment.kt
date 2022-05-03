package com.example.idcaller.presentation.main.mainflow

import android.os.Bundle
import android.view.View
import com.example.idcaller.R
import com.example.idcaller.core.base.BaseFragment
import com.example.idcaller.core.base.viewBindings
import com.example.idcaller.databinding.FragmentMainFlowBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainFlowFragment :
    BaseFragment<MainFlowViewModel, FragmentMainFlowBinding>(R.layout.fragment_main_flow) {
    override val viewModel: MainFlowViewModel by viewModel()
    override val binding: FragmentMainFlowBinding by viewBindings {
        FragmentMainFlowBinding.bind(it)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {
        with(binding) {

        }
    }
}