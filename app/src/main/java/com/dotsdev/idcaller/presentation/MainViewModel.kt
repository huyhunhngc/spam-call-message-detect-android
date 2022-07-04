package com.dotsdev.idcaller.presentation

import com.dotsdev.idcaller.core.base.BaseViewModel
import com.dotsdev.idcaller.data.local.CacheDataSource

class MainViewModel(private val cacheDataSource: CacheDataSource) : BaseViewModel() {
    fun onSaveModeNight(isEnable: Boolean) {
        cacheDataSource.setNightMode(isEnable)
    }
}
