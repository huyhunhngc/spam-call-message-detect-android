package com.dotsdev.idcaller.presentation.main.messagetab.tablayout

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.dotsdev.idcaller.R
import com.dotsdev.idcaller.core.base.BaseFragment
import com.dotsdev.idcaller.core.base.viewBindings
import com.dotsdev.idcaller.databinding.FragmentInboxMessageBinding
import com.dotsdev.idcaller.utils.toDetail
import org.koin.androidx.viewmodel.ext.android.viewModel

class InboxMessageFragment : BaseFragment<InboxMessageViewModel, FragmentInboxMessageBinding>
    (R.layout.fragment_inbox_message) {
    override val viewModel: InboxMessageViewModel by viewModel()
    override val binding: FragmentInboxMessageBinding by viewBindings(
        FragmentInboxMessageBinding::bind
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        observers()
    }

    private fun observers() {
        with(viewModel) {
            detailClick.observe(viewLifecycleOwner) {
                it.toDetail()?.let(findNavController()::navigate)
            }
        }
    }

    companion object {
        fun newInstance(): InboxMessageFragment {
            return InboxMessageFragment()
        }
    }
}
