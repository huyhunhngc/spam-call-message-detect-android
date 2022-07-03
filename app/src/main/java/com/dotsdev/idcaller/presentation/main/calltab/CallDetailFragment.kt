package com.dotsdev.idcaller.presentation.main.calltab

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.dotsdev.idcaller.R
import com.dotsdev.idcaller.core.base.BaseFragment
import com.dotsdev.idcaller.core.base.viewBindings
import com.dotsdev.idcaller.data.model.Contact
import com.dotsdev.idcaller.data.model.Message
import com.dotsdev.idcaller.data.model.MessageGroup
import com.dotsdev.idcaller.databinding.FragmentCallDetailBinding
import com.dotsdev.idcaller.presentation.template.NormalAppbarActivity
import com.dotsdev.idcaller.widget.recycler.FromData
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class CallDetailFragment :
    BaseFragment<CallDetailViewModel, FragmentCallDetailBinding>(R.layout.fragment_call_detail) {
    override val viewModel by viewModel<CallDetailViewModel> {
        parametersOf((arguments?.get(NormalAppbarActivity.DATA_FROM_KEY) as? FromData.FromCallGroup)?.callGroup)
    }
    override val binding by viewBindings(FragmentCallDetailBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        initViews()
        observer()
    }

    private fun initViews() {
        val caller =
            (arguments?.get(NormalAppbarActivity.DATA_FROM_KEY) as? FromData.FromCallGroup)?.callGroup
        with(binding) {
            btnMessageDetailContact.setOnClickListener {
                findNavController().navigate(
                    CallDetailFragmentDirections.openDetailMessage(
                        MessageGroup(
                            messages = mutableListOf(
                                Message(
                                    from = caller?.calls?.first()?.contact ?: Contact()
                                )
                            )
                        )
                    )
                )
            }
            btnCallDetailContact.setOnClickListener {
                if (ContextCompat.checkSelfPermission(
                        requireActivity(), Manifest.permission.CALL_PHONE
                    ) == PackageManager.PERMISSION_DENIED
                ) {
                    requestPermissionLauncher.launch(
                        Manifest.permission.CALL_PHONE,
                    )
                } else {
                    val intent =
                        Intent(
                            Intent.ACTION_CALL,
                            Uri.parse("tel:${caller?.calls?.first()?.contact?.phoneNumber}")
                        )
                    startActivity(intent)
                }

            }
        }
    }

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { result ->
            if (result == false) {
                Toast.makeText(activity, "Permission request failed", Toast.LENGTH_LONG).show()
            }
        }

    private fun observer() {
        with(viewModel) {

        }
    }
}