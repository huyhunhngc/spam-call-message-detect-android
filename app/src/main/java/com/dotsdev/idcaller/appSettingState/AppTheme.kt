package com.dotsdev.idcaller.appSettingState

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asFlow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow

class AppTheme {
    val obs = MutableLiveData(false)

    fun set(state: Boolean) {
        obs.postValue(state)
    }
}
