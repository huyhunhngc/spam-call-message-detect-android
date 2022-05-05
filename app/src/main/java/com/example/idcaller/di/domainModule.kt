package com.example.idcaller.di

import com.example.idcaller.data.memory.contact.ContactMemory
import org.koin.dsl.module

val domainModule = module {
    single { ContactMemory() }
}