package com.example.idcaller.presentation.main.initial

import android.Manifest
import android.animation.Animator
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.airbnb.lottie.LottieAnimationView
import com.example.idcaller.R
import com.example.idcaller.core.base.BaseFragment
import com.example.idcaller.core.base.viewBindings
import com.example.idcaller.databinding.FragmentInitialBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class InitialFragment: BaseFragment<InitialViewModel, FragmentInitialBinding>(R.layout.fragment_initial) {
    override val viewModel: InitialViewModel by viewModel()
    override val binding: FragmentInitialBinding by viewBindings {
        FragmentInitialBinding.bind(it)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAnimation()
    }

    private fun initAnimation() {
        binding.animationView.apply {
            addAnimatorListener(object : Animator.AnimatorListener {
                override fun onAnimationRepeat(animation: Animator?) {
                }

                override fun onAnimationEnd(animation: Animator?) {
                    onGrantPermissionNeeded()
                    navigate()
                }

                override fun onAnimationCancel(animation: Animator?) {
                }

                override fun onAnimationStart(animation: Animator?) {
                }
            })
        }
    }

    private fun navigate() {
        viewModel.navigateToDest(
            firstAction = {
                findNavController().navigate(InitialFragmentDirections.openInitialToSpHome())
            },
            normalAction = {
                //findNavController().navigate(InitialFragmentDirections.actionNavInitialFragmentToAccountTopDest())
            }
        )
    }
    private fun onGrantPermissionNeeded() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            if (ContextCompat.checkSelfPermission(
                    requireActivity(),
                    Manifest.permission.READ_CONTACTS
                )
                == PackageManager.PERMISSION_DENIED
            ) {
                requestPermissionLauncher.launch(
                    arrayOf(
                        Manifest.permission.READ_CONTACTS,
                        Manifest.permission.READ_PHONE_STATE,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                    )
                )
            }
        } else {
            if (ContextCompat.checkSelfPermission(
                    requireActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
                == PackageManager.PERMISSION_DENIED
            ) {
                requestPermissionLauncher.launch(
                    arrayOf(
                        Manifest.permission.READ_CONTACTS,
                        Manifest.permission.READ_PHONE_STATE,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                    )
                )
            }
        }
    }
    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { result ->
            if (result.values.any { it == false }) {
                Toast.makeText(activity, "Permission request failed", Toast.LENGTH_LONG).show()
            }
        }
}