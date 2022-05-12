package com.dotsdev.idcaller.di

import com.dotsdev.idcaller.data.memory.contact.ContactMemory
import com.dotsdev.idcaller.data.memory.message.MessageMemory
import org.koin.dsl.module

val domainModule = module {
    single { ContactMemory() }
    single { MessageMemory() }
}