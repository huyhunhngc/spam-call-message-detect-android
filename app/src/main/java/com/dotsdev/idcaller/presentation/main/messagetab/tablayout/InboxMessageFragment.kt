package com.dotsdev.idcaller.presentation.main.messagetab.tablayout

import androidx.fragment.app.viewModels
import com.dotsdev.idcaller.R
import com.dotsdev.idcaller.core.base.BaseFragment
import com.dotsdev.idcaller.core.base.viewBindings
import com.dotsdev.idcaller.databinding.FragmentInboxMessageBinding

class InboxMessageFragment : BaseFragment<InboxMessageViewModel, FragmentInboxMessageBinding>
    (R.layout.fragment_inbox_message) {
    override val viewModel: InboxMessageViewModel by viewModels()
    override val binding: FragmentInboxMessageBinding by viewBindings(
        FragmentInboxMessageBinding::bind
    )
}