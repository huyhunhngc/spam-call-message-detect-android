package com.dotsdev.idcaller.di

import com.dotsdev.idcaller.appSettingState.AppTheme
import com.dotsdev.idcaller.data.memory.contact.CallMemory
import com.dotsdev.idcaller.data.memory.contact.ContactMemory
import com.dotsdev.idcaller.data.memory.message.MessageMemory
import com.dotsdev.idcaller.data.memory.message.SpamMessageMemory
import com.dotsdev.idcaller.domain.classifier.ClassifierMessage
import com.dotsdev.idcaller.domain.classifier.resource.ClassifierModelHelper
import com.dotsdev.idcaller.domain.contact.query.GetCallLog
import com.dotsdev.idcaller.domain.contact.query.GetContact
import com.dotsdev.idcaller.domain.contact.query.GetRecentContact
import com.dotsdev.idcaller.domain.detectSpam.DetectSpamMessage
import com.dotsdev.idcaller.domain.message.query.GetMessageLog
import com.dotsdev.idcaller.domain.vectorizer.TfidfVectorizer
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val domainModule = module {
    single { ContactMemory() }
    single { MessageMemory() }
    single { CallMemory() }
    single { SpamMessageMemory() }
    single { ClassifierMessage.newInstance() }
    single { TfidfVectorizer.newInstance() }
    single { AppTheme() }
    single {
        ClassifierModelHelper(get(), get(), androidApplication()).apply {
            initRawResource()
        }
    }
    single { GetCallLog(get(), get()) }
    single { GetRecentContact(get(), get()) }
    single { GetMessageLog(get(), get(), get()) }
    factory { GetContact(get()) }
    factory { DetectSpamMessage(get(), get()) }
}
