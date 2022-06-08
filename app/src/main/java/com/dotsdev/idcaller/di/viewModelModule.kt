package com.dotsdev.idcaller.di

import com.dotsdev.idcaller.presentation.MainViewModel
import com.dotsdev.idcaller.presentation.main.calltab.CallTabViewModel
import com.dotsdev.idcaller.presentation.main.contacttab.ContactTabViewModel
import com.dotsdev.idcaller.presentation.main.initial.InitialViewModel
import com.dotsdev.idcaller.presentation.main.mainflow.MainFlowViewModel
import com.dotsdev.idcaller.presentation.main.messagetab.MessageDetailViewModel
import com.dotsdev.idcaller.presentation.main.messagetab.MessageTabViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { CallTabViewModel() }
    viewModel { MainFlowViewModel(get(), get()) }
    viewModel { MainViewModel() }
    viewModel { MessageTabViewModel() }
    viewModel { ContactTabViewModel(get()) }
    viewModel { InitialViewModel(get()) }
    viewModel { MessageDetailViewModel(get()) }
}