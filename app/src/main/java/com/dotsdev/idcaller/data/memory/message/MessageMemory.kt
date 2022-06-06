package com.dotsdev.idcaller.data.memory.message

import com.dotsdev.idcaller.data.model.Contact
import com.dotsdev.idcaller.data.model.Conversation
import com.dotsdev.idcaller.data.model.Message
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.map

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

    fun observe(contact: Contact): Flow<List<Message>> {
        return stateFlow.asSharedFlow().map {
            it.find { peer ->
                peer.from == contact
            }?.messages ?: listOf()
        }
    }
}