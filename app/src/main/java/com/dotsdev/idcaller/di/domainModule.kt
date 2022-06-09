package com.dotsdev.idcaller.di

import com.dotsdev.idcaller.data.memory.contact.CallMemory
import com.dotsdev.idcaller.data.memory.contact.ContactMemory
import com.dotsdev.idcaller.data.memory.message.MessageMemory
import com.dotsdev.idcaller.domain.classifier.ClassifierMessage
import org.koin.dsl.module

val domainModule = module {
    single { ContactMemory() }
    single { MessageMemory() }
    single { CallMemory() }
    single { ClassifierMessage.newInstance() }
}