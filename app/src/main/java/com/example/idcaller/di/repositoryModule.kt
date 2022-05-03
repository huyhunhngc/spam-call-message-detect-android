package com.example.idcaller.di

import com.example.idcaller.data.local.PreferencesDataSource
import org.koin.dsl.module

val repositoryModule = module {
    single { PreferencesDataSource() }
}