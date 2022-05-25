package com.dotsdev.idcaller.data.local

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
}