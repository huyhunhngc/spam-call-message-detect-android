package com.dotsdev.idcaller.presentation.main.blockingTab

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.dotsdev.idcaller.R
import com.dotsdev.idcaller.core.base.BaseFragment
import com.dotsdev.idcaller.core.base.viewBindings
import com.dotsdev.idcaller.databinding.FragmentBlockingTabBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class BlockingTabFragment :
    BaseFragment<BlockingTabViewModel, FragmentBlockingTabBinding>(R.layout.fragment_blocking_tab) {
    override val viewModel: BlockingTabViewModel by viewModel()
    override val binding: FragmentBlockingTabBinding by viewBindings {
        FragmentBlockingTabBinding.bind(it)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.run {
            clBlockNumberManually.setOnClickListener {
                val intent = Intent(context, ActivityBlockANumber::class.java)
                startActivity(intent)
            }
        }
    }

    companion object {
        fun newInstance(): BlockingTabFragment {
            return BlockingTabFragment()
        }
    }

}