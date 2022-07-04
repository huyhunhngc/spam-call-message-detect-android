package com.dotsdev.idcaller.core.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.dotsdev.idcaller.utils.hideLoadingDialog
import com.dotsdev.idcaller.utils.showLoadingDialog
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

abstract class BaseFragment<VM : BaseViewModel, VDB : ViewDataBinding>(@LayoutRes contentLayoutId: Int) :
    Fragment(contentLayoutId) {
    protected abstract val viewModel: VM
    protected abstract val binding: VDB

    protected lateinit var viewBinding: VDB

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (!::viewBinding.isInitialized) {
            viewBinding = binding.apply {
                lifecycleOwner = viewLifecycleOwner
            }
        }
        lifecycle.addObserver(viewModel.observer)
        viewModel.error.onEach { throwable ->
            throwable?.let { onError(it) }
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.loading.onEach {
            if (it) showLoadingDialog() else hideLoadingDialog()
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    open fun onError(throwable: Throwable) {
    }

    companion object {
        const val SHOW_APP_DETAIL_SETTING = "show_app_detail_setting"
    }
}
