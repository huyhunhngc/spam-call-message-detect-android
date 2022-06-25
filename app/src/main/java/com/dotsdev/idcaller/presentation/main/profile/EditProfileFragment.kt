package com.dotsdev.idcaller.presentation.main.profile

import android.os.Bundle
import androidx.navigation.fragment.findNavController
import com.dotsdev.idcaller.R
import com.dotsdev.idcaller.core.base.BaseFragment
import com.dotsdev.idcaller.core.base.viewBindings
import com.dotsdev.idcaller.databinding.FragmentEditProfileBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class EditProfileFragment :
    BaseFragment<EditProfileViewModel, FragmentEditProfileBinding>(R.layout.fragment_edit_profile) {

    override val viewModel: EditProfileViewModel by viewModel()
    override val binding: FragmentEditProfileBinding by viewBindings {
        FragmentEditProfileBinding.bind(it)
    }

    override fun onStart() {
        super.onStart()
        binding.edSecondPhoneNumber.setOnClickListener {
            findNavController().navigate(EditProfileFragmentDirections.openEditNumber())

        }
    }

}