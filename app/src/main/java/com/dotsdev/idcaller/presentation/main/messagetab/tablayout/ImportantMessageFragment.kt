package com.dotsdev.idcaller.presentation.main.messagetab.tablayout

import androidx.fragment.app.viewModels
import com.dotsdev.idcaller.R
import com.dotsdev.idcaller.core.base.BaseFragment
import com.dotsdev.idcaller.core.base.viewBindings
import com.dotsdev.idcaller.databinding.FragmentImportantMessageBinding

class ImportantMessageFragment :
    BaseFragment<ImportantMessageViewModel, FragmentImportantMessageBinding>
        (R.layout.fragment_important_message) {
    override val viewModel: ImportantMessageViewModel by viewModels()
    override val binding: FragmentImportantMessageBinding by viewBindings(
        FragmentImportantMessageBinding::bind
    )
}