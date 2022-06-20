package com.dotsdev.idcaller.presentation.main.contacttab

import androidx.fragment.app.FragmentContainerView
import androidx.navigation.findNavController
import com.dotsdev.idcaller.R
import com.dotsdev.idcaller.core.base.BaseFragment
import com.dotsdev.idcaller.core.base.viewBindings
import com.dotsdev.idcaller.databinding.FragmentContactTabBinding
import com.dotsdev.idcaller.presentation.main.mainflow.IBottomNavigation
import org.koin.androidx.viewmodel.ext.android.viewModel

class ContactTabFragment :
    BaseFragment<ContactTabViewModel, FragmentContactTabBinding>(R.layout.fragment_contact_tab),
    IBottomNavigation {
    override val viewModel: ContactTabViewModel by viewModel()
    override val binding: FragmentContactTabBinding by viewBindings {
        FragmentContactTabBinding.bind(it)
    }

    fun findNaviHost(): FragmentContainerView = binding.navHostFragment

    companion object {
        fun newInstance(): ContactTabFragment {
            return ContactTabFragment()
        }
    }

    override fun backToRoot() {
        kotlin.runCatching {
            findNaviHost().findNavController().popBackStack(R.id.contact_list, false)
        }
    }

    override fun onChangeTab() {

    }
}
