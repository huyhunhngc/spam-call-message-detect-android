package com.dotsdev.idcaller.di

import com.dotsdev.idcaller.data.local.CacheDataSource
import com.dotsdev.idcaller.data.local.PreferencesDataSource
import org.koin.dsl.module

val repositoryModule = module {
    single { PreferencesDataSource() }
    single { CacheDataSource() }
}