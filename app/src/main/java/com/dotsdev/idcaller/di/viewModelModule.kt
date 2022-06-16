package com.dotsdev.idcaller.di

import com.dotsdev.idcaller.presentation.MainViewModel
import com.dotsdev.idcaller.presentation.main.calltab.CallTabViewModel
import com.dotsdev.idcaller.presentation.main.contacttab.ContactTabViewModel
import com.dotsdev.idcaller.presentation.main.initial.InitialViewModel
import com.dotsdev.idcaller.presentation.main.mainflow.MainFlowViewModel
import com.dotsdev.idcaller.presentation.main.messagetab.detail.MessageDetailViewModel
import com.dotsdev.idcaller.presentation.main.messagetab.MessageTabViewModel
import com.dotsdev.idcaller.presentation.main.messagetab.tablayout.ImportantMessageViewModel
import com.dotsdev.idcaller.presentation.main.messagetab.tablayout.InboxMessageViewModel
import com.dotsdev.idcaller.presentation.main.messagetab.tablayout.SpamMessageViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { CallTabViewModel(get(), get()) }
    viewModel { MainFlowViewModel(get(), get(), get(), get()) }
    viewModel { MainViewModel() }
    viewModel { MessageTabViewModel(get()) }
    viewModel { ContactTabViewModel(get()) }
    viewModel { InitialViewModel(get()) }
    viewModel { MessageDetailViewModel(get()) }
    viewModel { SpamMessageViewModel(get()) }
    viewModel { InboxMessageViewModel(get()) }
    viewModel { ImportantMessageViewModel(get()) }
}