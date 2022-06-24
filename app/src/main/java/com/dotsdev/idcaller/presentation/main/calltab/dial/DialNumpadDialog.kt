package com.dotsdev.idcaller.presentation.main.calltab.dial

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.dotsdev.idcaller.R
import com.dotsdev.idcaller.databinding.DialogDialpadBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.koin.androidx.viewmodel.ext.android.viewModel


class DialNumpadDialog : BottomSheetDialogFragment() {
    private lateinit var binding: DialogDialpadBinding
    private val viewModel: DialNumpadViewModel by viewModel()
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
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initViews() {
        binding.apply {
            viewModel = this@DialNumpadDialog.viewModel
            lifecycleOwner = viewLifecycleOwner
            tvNumberField.apply {
                showSoftInputOnFocus = false
            }
            btnErase.setOnTouchListener { v, event ->
                if (event.action == MotionEvent.ACTION_DOWN) {
                    this@DialNumpadDialog.viewModel.onLongDelete()
                }
                if (event.action == MotionEvent.ACTION_UP) {
                    this@DialNumpadDialog.viewModel.deleteJob = false
                }
                true
            }
        }
        with(viewModel) {
            onClickDial.observe(this@DialNumpadDialog) {
                if (ContextCompat.checkSelfPermission(
                        requireActivity(), Manifest.permission.CALL_PHONE
                    ) == PackageManager.PERMISSION_DENIED
                ) {
                    requestPermissionLauncher.launch(
                        Manifest.permission.CALL_PHONE,
                    )
                } else {
                    val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:$it"))
                    startActivity(intent)
                }
            }
            triggerAddNumber.observe(this@DialNumpadDialog) {
                viewModel.addNumberValue(it, binding.tvNumberField.selectionEnd)
            }
            cursor.observe(viewLifecycleOwner) {
                binding.tvNumberField.setSelection(it)
            }
        }
    }

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { result ->
            if (result == false ) {
                Toast.makeText(activity, "Permission request failed", Toast.LENGTH_LONG).show()
            }
        }
}