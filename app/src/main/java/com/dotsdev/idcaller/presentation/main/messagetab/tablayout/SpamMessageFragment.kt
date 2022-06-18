package com.dotsdev.idcaller.presentation.main.messagetab.tablayout

import com.dotsdev.idcaller.R
import com.dotsdev.idcaller.core.base.BaseFragment
import com.dotsdev.idcaller.core.base.viewBindings
import com.dotsdev.idcaller.databinding.FragmentSpamMessageBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class SpamMessageFragment : BaseFragment<SpamMessageViewModel, FragmentSpamMessageBinding>
    (R.layout.fragment_spam_message) {
    override val viewModel: SpamMessageViewModel by viewModel()
    override val binding: FragmentSpamMessageBinding by viewBindings(
        FragmentSpamMessageBinding::bind
    )

    companion object {
        fun newInstance(): SpamMessageFragment {
            return SpamMessageFragment()
        }
    }
}
