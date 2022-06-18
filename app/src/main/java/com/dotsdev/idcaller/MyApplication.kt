package com.dotsdev.idcaller

import android.app.Application
import com.chibatching.kotpref.Kotpref
import com.chibatching.kotpref.gsonpref.gson
import com.dotsdev.idcaller.di.domainModule
import com.dotsdev.idcaller.di.repositoryModule
import com.dotsdev.idcaller.di.usecaseModule
import com.dotsdev.idcaller.di.viewModelModule
import com.google.gson.Gson
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        koinConfig()
        Kotpref.gson = Gson()
    }

    private fun koinConfig() = startKoin {
        androidContext(this@MyApplication)
        modules(
            viewModelModule,
            domainModule,
            repositoryModule,
            usecaseModule
        )
    }
}
