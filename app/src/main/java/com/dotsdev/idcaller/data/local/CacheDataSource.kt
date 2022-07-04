package com.dotsdev.idcaller.data.local

import com.dotsdev.idcaller.data.prefsmodel.AppPreference
import com.dotsdev.idcaller.data.prefsmodel.CachePreference

class CacheDataSource {
    fun getNeededGrantPermission(): Boolean = !CachePreference.isHasGrantPermission
    fun getIsOpenAppFirstTime(): Boolean = CachePreference.isFirstOpenApp
    fun setIsNeededGrantPermission(granted: Boolean) {
        CachePreference.isHasGrantPermission = granted
    }

    fun setHasOpenAppFirstTime(hasOpen: Boolean) {
        CachePreference.isFirstOpenApp = !hasOpen
    }

    fun setNightMode(isNightMode: Boolean) {
        AppPreference.isNightMode = isNightMode
    }

    fun getNightMode(): Boolean = AppPreference.isNightMode
}
