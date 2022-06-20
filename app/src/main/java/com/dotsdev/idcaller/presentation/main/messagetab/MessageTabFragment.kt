package com.dotsdev.idcaller.presentation.main.messagetab

import androidx.fragment.app.FragmentContainerView
import androidx.navigation.findNavController
import com.dotsdev.idcaller.R
import com.dotsdev.idcaller.core.base.BaseFragment
import com.dotsdev.idcaller.core.base.viewBindings
import com.dotsdev.idcaller.databinding.FragmentMessageTabBinding
import com.dotsdev.idcaller.presentation.main.mainflow.IBottomNavigation
import org.koin.androidx.viewmodel.ext.android.viewModel

class MessageTabFragment :
    BaseFragment<MessageTabViewModel, FragmentMessageTabBinding>(R.layout.fragment_message_tab),
    IBottomNavigation {

    override val viewModel: MessageTabViewModel by viewModel()
    override val binding: FragmentMessageTabBinding by viewBindings {
        FragmentMessageTabBinding.bind(it)
    }

    fun findNaviHost(): FragmentContainerView = binding.navHostFragment

    companion object {
        fun newInstance(): MessageTabFragment {
            return MessageTabFragment()
        }
    }

    override fun backToRoot() {
        kotlin.runCatching {
            findNaviHost().findNavController().popBackStack(R.id.message_list, false)
        }
    }

    override fun onChangeTab() {

    }
}
