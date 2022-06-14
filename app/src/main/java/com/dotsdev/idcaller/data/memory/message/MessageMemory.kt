package com.dotsdev.idcaller.data.memory.message

import com.dotsdev.idcaller.data.model.Contact
import com.dotsdev.idcaller.data.model.Message
import com.dotsdev.idcaller.utils.phoneNumberWithoutCountryCode
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
        memoryMessage.addAll(
            info.map {
                it.copy(uniqueId = "${it.iat.time}-${it.from.phoneNumber.phoneNumberWithoutCountryCode()}")
            })
        stateFlow.tryEmit(info)
    }

    fun add(info: Message) {
        memoryMessage.add(info.copy(uniqueId = "${info.iat.time}-${info.from.phoneNumber.phoneNumberWithoutCountryCode()}"))
        stateFlow.tryEmit(memoryMessage)
    }

    fun get(): List<Message> {
        return stateFlow.value
    }

    fun observe(contact: Contact): Flow<List<Message>> {
        return stateFlow.asSharedFlow().map {
            it.filter { peer ->
                peer.from.phoneNumber == contact.phoneNumber
            }
        }
    }
}