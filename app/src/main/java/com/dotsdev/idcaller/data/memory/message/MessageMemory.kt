package com.dotsdev.idcaller.data.memory.message

import com.dotsdev.idcaller.data.model.Contact
import com.dotsdev.idcaller.data.model.Conversation
import com.dotsdev.idcaller.data.model.Message
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.map

class MessageMemory {
    private val stateFlow = MutableStateFlow(listOf<Message>())
    val memoryMessage = mutableListOf<Message>()

    fun observe(): Flow<List<Message>> {
        return stateFlow.asSharedFlow()
    }

    fun set(info: List<Message>) {
        memoryMessage.addAll(info)
        stateFlow.tryEmit(info)
    }

    fun add(info: Message) {
        memoryMessage.add(info)
        stateFlow.tryEmit(memoryMessage)
    }

    fun get(): List<Message> {
        return stateFlow.value
    }

    fun observe(contact: Contact): Flow<List<Message>> {
        return stateFlow.asSharedFlow().map{
            it.filter { peer ->
                peer.from.phoneNumber == contact.phoneNumber
            }
        }
    }
}