package com.dotsdev.idcaller.presentation.main.calltab.dial

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dotsdev.idcaller.R
import com.dotsdev.idcaller.databinding.DialogDialpadBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class DialNumpadDialog : BottomSheetDialogFragment() {
    private lateinit var binding: DialogDialpadBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = DialogDialpadBinding.inflate(inflater, container, false).let {
        binding = it
        it.root
    }

    override fun getTheme(): Int = R.style.BottomSheetDialog

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initObservers()
    }

    private fun initViews() {
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
        }
    }

    private fun initObservers() {
    }
}
