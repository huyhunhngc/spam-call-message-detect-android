package com.dotsdev.idcaller.presentation.main.profile

import com.dotsdev.idcaller.R
import com.dotsdev.idcaller.core.base.BaseFragment
import com.dotsdev.idcaller.core.base.viewBindings
import com.dotsdev.idcaller.databinding.FragmentEditNumberphoneBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class EditNumberPhoneFragment : BaseFragment<EditNumberPhoneViewModel, FragmentEditNumberphoneBinding>(
    R.layout.fragment_edit_numberphone
) {
    override val viewModel: EditNumberPhoneViewModel by viewModel()
    override val binding: FragmentEditNumberphoneBinding by viewBindings(
        FragmentEditNumberphoneBinding::bind
    )


}