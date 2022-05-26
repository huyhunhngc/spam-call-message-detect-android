package com.dotsdev.idcaller.usecase

import com.dotsdev.idcaller.data.local.PreferencesDataSource
import com.dotsdev.idcaller.data.model.User

class UserUsecase(private val preferencesDataSource: PreferencesDataSource) {
    suspend fun getUser(): User? {
        return preferencesDataSource.userInfo
    }
}