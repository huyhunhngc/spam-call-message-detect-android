package com.dotsdev.idcaller.presentation.main.contacttab

import com.dotsdev.idcaller.R
import com.dotsdev.idcaller.core.base.BaseFragment
import com.dotsdev.idcaller.core.base.viewBindings
import com.dotsdev.idcaller.databinding.FragmentContactTabBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class ContactTabFragment :
    com.dotsdev.idcaller.core.base.BaseFragment<ContactTabViewModel, FragmentContactTabBinding>(R.layout.fragment_contact_tab) {
    override val viewModel: ContactTabViewModel by viewModel()
    override val binding: FragmentContactTabBinding by viewBindings {
        FragmentContactTabBinding.bind(it)
    }
}