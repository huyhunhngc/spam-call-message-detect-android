package com.dotsdev.idcaller.presentation.main.calltab

import android.os.Bundle
import android.view.View
import com.dotsdev.idcaller.R
import com.dotsdev.idcaller.core.base.BaseFragment
import com.dotsdev.idcaller.core.base.viewBindings
import com.dotsdev.idcaller.databinding.FragmentCallDetailBinding
import com.dotsdev.idcaller.presentation.template.NormalAppbarActivity
import com.dotsdev.idcaller.widget.recycler.FromData
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class CallDetailFragment(
) : BaseFragment<CallDetailViewModel,FragmentCallDetailBinding>(R.layout.fragment_call_detail){
    override val viewModel by viewModel<CallDetailViewModel> {
        parametersOf((arguments?.get(NormalAppbarActivity.DATA_FROM_KEY) as? FromData.FromCallGroup)?.callGroup)
    }
    override val binding by viewBindings(FragmentCallDetailBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
    }
}