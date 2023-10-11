package com.dotsdev.idcaller.core.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.dotsdev.idcaller.utils.hideLoadingDialog
import com.dotsdev.idcaller.utils.showLoadingDialog
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

abstract class BaseFragment<VM : BaseViewModel, VDB : ViewDataBinding>(@LayoutRes contentLayoutId: Int) :
    Fragment(contentLayoutId) {
    protected abstract val viewModel: VM
    protected abstract val binding: VDB

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycle.addObserver(viewModel)
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                launch {
                    viewModel.error.collect { throwable ->
                        throwable?.let { onError(it) }
                    }
                }
                launch {
                    viewModel.loading.collect {
                        if (it) showLoadingDialog() else hideLoadingDialog()
                    }
                }
            }
        }
    }

    open fun onError(throwable: Throwable) {
    }
}
