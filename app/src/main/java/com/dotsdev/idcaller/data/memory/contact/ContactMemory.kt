package com.dotsdev.idcaller.data.memory.contact

import com.dotsdev.idcaller.data.model.Contact
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow

class ContactMemory {
    private val stateFlow = MutableStateFlow(listOf<Contact>())

    fun observe(): Flow<List<Contact>> {
        return stateFlow.asSharedFlow()
    }

    fun set(info: List<Contact>) {
        stateFlow.tryEmit(info)
    }

    fun get(): List<Contact> {
        return stateFlow.value
    }
}
