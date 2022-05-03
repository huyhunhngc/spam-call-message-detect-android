package com.example.idcaller.di

import com.example.idcaller.presentation.MainViewModel
import com.example.idcaller.presentation.main.calltab.CallTabViewModel
import com.example.idcaller.presentation.main.initial.InitialViewModel
import com.example.idcaller.presentation.main.mainflow.MainFlowViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { CallTabViewModel() }
    viewModel { MainFlowViewModel() }
    viewModel { MainViewModel() }
    viewModel { InitialViewModel() }
}