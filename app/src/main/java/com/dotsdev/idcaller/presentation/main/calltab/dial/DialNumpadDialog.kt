package com.dotsdev.idcaller.presentation.main.calltab.dial

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import com.dotsdev.idcaller.R
import com.dotsdev.idcaller.databinding.DialogDialpadBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class DialNumpadDialog : BottomSheetDialogFragment() {
    private lateinit var binding: DialogDialpadBinding
    var cache = ""
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
            btnZero.setOnClickListener { onButtonClick(btnZero, binding.tvNumberField, "0") }
            btnOne.setOnClickListener { onButtonClick(btnOne, binding.tvNumberField, "1") }
            btnTwo.setOnClickListener { onButtonClick(btnTwo, binding.tvNumberField, "2") }
            btnThree.setOnClickListener { onButtonClick(btnThree, binding.tvNumberField, "3") }
            btnFour.setOnClickListener { onButtonClick(btnFour, binding.tvNumberField, "4") }
            btnFive.setOnClickListener { onButtonClick(btnFive, binding.tvNumberField, "5") }
            btnSix.setOnClickListener { onButtonClick(btnSix, binding.tvNumberField, "6") }
            btnSeven.setOnClickListener { onButtonClick(btnSeven, binding.tvNumberField, "7") }
            btnEight.setOnClickListener { onButtonClick(btnEight, binding.tvNumberField, "8") }
            btnNine.setOnClickListener { onButtonClick(btnNine, binding.tvNumberField, "9") }
            btnStar.setOnClickListener { onButtonClick(btnStar, binding.tvNumberField, "*") }
            btnHash.setOnClickListener { onButtonClick(btnHash, binding.tvNumberField, "#") }
            btnErase.setOnClickListener { onDelete() }

        }
    }

    fun onButtonClick(button: TextView, inputNumber: EditText, number: String) {
        cache = binding.tvNumberField.getText().toString()
        inputNumber.setText(cache + number)

    }
    fun onDelete(){
        var lenght : Int = binding.tvNumberField.getText().length
        binding.tvNumberField.getText().delete(lenght-1, lenght)
    }

    private fun initObservers() {

    }
}