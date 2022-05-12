package com.example.idcaller.data.memory.message

import com.example.idcaller.data.model.Conversation
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow

class MessageMemory {
    private val stateFlow = MutableStateFlow(listOf<Conversation>())

    fun observe(): Flow<List<Conversation>> {
        return stateFlow.asSharedFlow()
    }

    fun set(info: List<Conversation>) {
        stateFlow.tryEmit(info)
    }

    fun get(): List<Conversation> {
        return stateFlow.value
    }
}