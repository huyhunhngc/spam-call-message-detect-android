package com.dotsdev.idcaller.di

import com.dotsdev.idcaller.presentation.MainViewModel
import com.dotsdev.idcaller.presentation.main.blockingTab.BlockingTabViewModel
import com.dotsdev.idcaller.presentation.main.calltab.CallDetailViewModel
import com.dotsdev.idcaller.presentation.main.calltab.CallListViewModel
import com.dotsdev.idcaller.presentation.main.calltab.CallTabViewModel
import com.dotsdev.idcaller.presentation.main.calltab.dial.DialNumpadViewModel
import com.dotsdev.idcaller.presentation.main.contacttab.ContactListViewModel
import com.dotsdev.idcaller.presentation.main.contacttab.ContactTabViewModel
import com.dotsdev.idcaller.presentation.main.initial.InitialViewModel
import com.dotsdev.idcaller.presentation.main.mainflow.MainFlowViewModel
import com.dotsdev.idcaller.presentation.main.messagetab.MessageListViewModel
import com.dotsdev.idcaller.presentation.main.messagetab.MessageTabViewModel
import com.dotsdev.idcaller.presentation.main.messagetab.detail.MessageDetailViewModel
import com.dotsdev.idcaller.presentation.main.messagetab.tablayout.ImportantMessageViewModel
import com.dotsdev.idcaller.presentation.main.messagetab.tablayout.InboxMessageViewModel
import com.dotsdev.idcaller.presentation.main.messagetab.tablayout.SpamMessageViewModel
import com.dotsdev.idcaller.presentation.main.profile.EditNumberPhoneViewModel
import com.dotsdev.idcaller.presentation.main.profile.EditProfileViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { CallTabViewModel() }
    viewModel { CallListViewModel(get(), get()) }
    viewModel { MainFlowViewModel(get(), get(), get(), get(), get(), get(), get(), get(), get()) }
    viewModel { MainViewModel(get()) }
    viewModel { MessageTabViewModel() }
    viewModel { MessageListViewModel(get()) }
    viewModel { ContactTabViewModel() }
    viewModel { ContactListViewModel(get()) }
    viewModel { InitialViewModel(get()) }
    viewModel { data -> MessageDetailViewModel(get(), get(), data.get()) }
    viewModel { SpamMessageViewModel(get()) }
    viewModel { InboxMessageViewModel(get()) }
    viewModel { ImportantMessageViewModel(get()) }
    viewModel { DialNumpadViewModel() }
    viewModel { EditProfileViewModel()}
    viewModel { EditNumberPhoneViewModel() }
    viewModel { data -> CallDetailViewModel(data.get(), get(), get())}
    viewModel { BlockingTabViewModel() }
}
