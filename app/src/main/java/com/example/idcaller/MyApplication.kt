package com.example.idcaller

import android.app.Application
import com.example.idcaller.di.domainModule
import com.example.idcaller.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        koinConfig()
    }

    private fun koinConfig() = startKoin {
        androidContext(this@MyApplication)
        modules(
            viewModelModule,
            domainModule
        )
    }
}