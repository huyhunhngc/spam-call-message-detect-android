package com.dotsdev.idcaller.usecase

import com.dotsdev.idcaller.data.local.PreferencesDataSource
import com.dotsdev.idcaller.data.model.SimInfo
import com.dotsdev.idcaller.data.model.User

class UserUsecase(private val preferencesDataSource: PreferencesDataSource) {
    fun getUser(): User? {
        return preferencesDataSource.userInfo
    }

    fun getSimInfo(): List<SimInfo>? {
        return preferencesDataSource.simInfo
    }

    fun setSimInfo(sims: List<SimInfo>) {
        preferencesDataSource.simInfo = sims
    }
}
