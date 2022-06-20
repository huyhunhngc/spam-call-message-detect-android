package com.dotsdev.idcaller.presentation.main.contacttab

import android.os.Bundle
import android.view.View
import com.dotsdev.idcaller.R
import com.dotsdev.idcaller.core.base.BaseFragment
import com.dotsdev.idcaller.core.base.viewBindings
import com.dotsdev.idcaller.databinding.FragmentContactListBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class ContactListFragment :
    BaseFragment<ContactListViewModel, FragmentContactListBinding>(R.layout.fragment_contact_list) {
    override val viewModel: ContactListViewModel by viewModel()
    override val binding: FragmentContactListBinding by viewBindings {
        FragmentContactListBinding.bind(it)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        observers()
    }

    private fun observers() {
        with(viewModel) {
            callClick.observe(viewLifecycleOwner) {
            }
            detailClick.observe(viewLifecycleOwner) {
            }
        }
    }
}