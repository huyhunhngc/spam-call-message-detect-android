package com.example.idcaller.di

import com.example.idcaller.presentation.main.callTab.CallTabViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { CallTabViewModel() }
}