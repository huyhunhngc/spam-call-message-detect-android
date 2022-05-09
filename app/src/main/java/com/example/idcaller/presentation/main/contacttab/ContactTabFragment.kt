package com.example.idcaller.presentation.main.contacttab

import com.example.idcaller.R
import com.example.idcaller.core.base.BaseFragment
import com.example.idcaller.core.base.viewBindings
import com.example.idcaller.databinding.FragmentContactTabBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class ContactTabFragment :
    BaseFragment<ContactTabViewModel, FragmentContactTabBinding>(R.layout.fragment_contact_tab) {
    override val viewModel: ContactTabViewModel by viewModel()
    override val binding: FragmentContactTabBinding by viewBindings {
        FragmentContactTabBinding.bind(it)
    }
}