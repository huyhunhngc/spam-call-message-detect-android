package com.dotsdev.idcaller.appSettingState

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow

class AppTheme {
    private val stateFlow = MutableStateFlow(false)

    fun observe(): Flow<Boolean> {
        return stateFlow.asSharedFlow()
    }

    fun set(state: Boolean) {
        stateFlow.tryEmit(state)
    }

    fun get(): Boolean {
        return stateFlow.value
    }
}
