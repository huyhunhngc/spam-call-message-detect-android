package com.dotsdev.idcaller.presentation.main.contacttab

import android.os.Bundle
import android.view.View
import com.dotsdev.idcaller.R
import com.dotsdev.idcaller.core.base.BaseFragment
import com.dotsdev.idcaller.core.base.viewBindings
import com.dotsdev.idcaller.databinding.FragmentContactTabBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class ContactTabFragment :
   BaseFragment<ContactTabViewModel, FragmentContactTabBinding>(R.layout.fragment_contact_tab) {
    override val viewModel: ContactTabViewModel by viewModel()
    override val binding: FragmentContactTabBinding by viewBindings {
        FragmentContactTabBinding.bind(it)
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

    companion object {
        fun newInstance(): ContactTabFragment {
            return ContactTabFragment()
        }
    }
}