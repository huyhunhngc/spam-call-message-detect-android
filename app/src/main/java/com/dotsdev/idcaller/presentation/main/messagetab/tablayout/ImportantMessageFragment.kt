package com.dotsdev.idcaller.presentation.main.messagetab.tablayout

import com.dotsdev.idcaller.R
import com.dotsdev.idcaller.core.base.BaseFragment
import com.dotsdev.idcaller.core.base.viewBindings
import com.dotsdev.idcaller.databinding.FragmentImportantMessageBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class ImportantMessageFragment :
    BaseFragment<ImportantMessageViewModel, FragmentImportantMessageBinding>
        (R.layout.fragment_important_message) {
    override val viewModel: ImportantMessageViewModel by viewModel()
    override val binding: FragmentImportantMessageBinding by viewBindings(
        FragmentImportantMessageBinding::bind
    )

    companion object {
        fun newInstance(): ImportantMessageFragment {
            return ImportantMessageFragment()
        }
    }
}