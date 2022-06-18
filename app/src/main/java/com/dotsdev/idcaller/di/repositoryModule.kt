package com.dotsdev.idcaller.di

import com.dotsdev.idcaller.data.dao.RoomRepository
import com.dotsdev.idcaller.data.local.CacheDataSource
import com.dotsdev.idcaller.data.local.PreferencesDataSource
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val repositoryModule = module {
    single { PreferencesDataSource() }
    single { CacheDataSource() }
    single {
        RoomRepository.getDatabase(androidContext())
    }
}
