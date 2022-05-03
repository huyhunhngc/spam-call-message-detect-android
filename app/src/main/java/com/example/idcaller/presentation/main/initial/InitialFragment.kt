package com.example.idcaller.presentation.main.initial

import android.animation.Animator
import android.os.Bundle
import android.view.View
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
                }

                override fun onAnimationCancel(animation: Animator?) {
                }

                override fun onAnimationStart(animation: Animator?) {
                }
            })
        }
    }
}