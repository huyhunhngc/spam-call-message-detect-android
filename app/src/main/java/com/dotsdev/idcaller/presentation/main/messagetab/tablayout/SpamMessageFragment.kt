package com.dotsdev.idcaller.presentation.main.messagetab.tablayout

import androidx.fragment.app.viewModels
import com.dotsdev.idcaller.R
import com.dotsdev.idcaller.core.base.BaseFragment
import com.dotsdev.idcaller.core.base.viewBindings
import com.dotsdev.idcaller.databinding.FragmentSpamMessageBinding

class SpamMessageFragment : BaseFragment<SpamMessageViewModel, FragmentSpamMessageBinding>
    (R.layout.fragment_spam_message) {
    override val viewModel: SpamMessageViewModel by viewModels()
    override val binding: FragmentSpamMessageBinding by viewBindings(
        FragmentSpamMessageBinding::bind
    )
}