package com.dotsdev.idcaller.di

import com.dotsdev.idcaller.data.memory.contact.CallMemory
import com.dotsdev.idcaller.data.memory.contact.ContactMemory
import com.dotsdev.idcaller.data.memory.message.MessageMemory
import com.dotsdev.idcaller.domain.classifier.ClassifierMessage
import com.dotsdev.idcaller.domain.contact.query.GetCallLog
import com.dotsdev.idcaller.domain.contact.query.GetRecentContact
import com.dotsdev.idcaller.domain.message.query.GetMessageLog
import org.koin.dsl.module

val domainModule = module {
    single { ContactMemory() }
    single { MessageMemory() }
    single { CallMemory() }
    single { ClassifierMessage.newInstance() }
    factory { GetCallLog(get(), get()) }
    factory { GetRecentContact(get(), get()) }
    factory { GetMessageLog(get(), get()) }
}