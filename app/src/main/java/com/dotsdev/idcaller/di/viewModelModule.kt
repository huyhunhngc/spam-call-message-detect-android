package com.dotsdev.idcaller.di

import com.dotsdev.idcaller.presentation.MainViewModel
import com.dotsdev.idcaller.presentation.main.calltab.CallTabViewModel
import com.dotsdev.idcaller.presentation.main.initial.InitialViewModel
import com.dotsdev.idcaller.presentation.main.mainflow.MainFlowViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { CallTabViewModel() }
    viewModel { MainFlowViewModel(get()) }
    viewModel { MainViewModel() }
    viewModel { InitialViewModel() }
}