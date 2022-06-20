package com.dotsdev.idcaller.presentation.main.calltab

import androidx.fragment.app.FragmentContainerView
import androidx.navigation.findNavController
import com.dotsdev.idcaller.R
import com.dotsdev.idcaller.core.base.BaseFragment
import com.dotsdev.idcaller.core.base.viewBindings
import com.dotsdev.idcaller.databinding.FragmentCallTabBinding
import com.dotsdev.idcaller.presentation.main.mainflow.IBottomNavigation
import org.koin.androidx.viewmodel.ext.android.viewModel

class CallTabFragment :
    BaseFragment<CallTabViewModel, FragmentCallTabBinding>(R.layout.fragment_call_tab),
    IBottomNavigation {
    override val viewModel by viewModel<CallTabViewModel>()
    override val binding by viewBindings(FragmentCallTabBinding::bind)

    fun findNaviHost(): FragmentContainerView = binding.navHostFragment

    companion object {
        fun newInstance(): CallTabFragment {
            return CallTabFragment()
        }
    }

    override fun backToRoot() {
        kotlin.runCatching {
            findNaviHost().findNavController().popBackStack(R.id.call_list, false)
        }
    }

    override fun onChangeTab() {

    }
}
