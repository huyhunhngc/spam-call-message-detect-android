package com.dotsdev.idcaller.di

import com.dotsdev.idcaller.data.memory.contact.CallMemory
import com.dotsdev.idcaller.data.memory.contact.ContactMemory
import com.dotsdev.idcaller.data.memory.message.MessageMemory
import com.dotsdev.idcaller.domain.classifier.ClassifierMessage
import com.dotsdev.idcaller.domain.contact.query.GetCallLog
import com.dotsdev.idcaller.domain.contact.query.GetContact
import com.dotsdev.idcaller.domain.contact.query.GetRecentContact
import com.dotsdev.idcaller.domain.message.query.GetMessageLog
import com.dotsdev.idcaller.domain.vectorizer.TfidfVectorizer
import org.koin.dsl.module

val domainModule = module {
    single { ContactMemory() }
    single { MessageMemory() }
    single { CallMemory() }
    single { ClassifierMessage.newInstance() }
    single { TfidfVectorizer.newInstance() }
    factory { GetCallLog(get(), get()) }
    factory { GetRecentContact(get(), get()) }
    factory { GetMessageLog(get(), get()) }
    factory { GetContact(get()) }
}
