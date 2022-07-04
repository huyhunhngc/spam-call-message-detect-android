package com.dotsdev.idcaller.presentation

import androidx.appcompat.app.AppCompatDelegate
import com.dotsdev.idcaller.appSettingState.AppTheme
import com.dotsdev.idcaller.core.base.BaseViewModel
import kotlinx.coroutines.GlobalScope

class MainViewModel(private val appTheme: AppTheme) : BaseViewModel() {
    override fun onCreate() {
        super.onCreate()
        GlobalScope.launch {
            appTheme.observe().collect {
                val theme = when (it) {
                    false -> AppCompatDelegate.MODE_NIGHT_NO
                    true -> AppCompatDelegate.MODE_NIGHT_YES
                }
                AppCompatDelegate.setDefaultNightMode(theme)
            }
        }
    }
}
