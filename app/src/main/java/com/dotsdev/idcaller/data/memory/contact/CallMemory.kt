package com.dotsdev.idcaller.data.memory.contact

import com.dotsdev.idcaller.data.model.Call
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow

class CallMemory {
    private val stateFlow = MutableStateFlow(listOf<Call>())

    fun observe(): Flow<List<Call>> {
        return stateFlow.asSharedFlow()
    }

    fun set(info: List<Call>) {
        stateFlow.tryEmit(info)
    }

    fun get(): List<Call> {
        return stateFlow.value
    }
}
