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
        memoryMessage.clear()
        memoryMessage.addAll(
            info.map {
                it.copy(messageId = "${it.iat.time}-${it.from.phoneNumber.phoneNumberWithoutCountryCode()}")
            }
        )
        stateFlow.tryEmit(info)
    }

    fun add(infos: List<Message>) {
        infos.map { info ->
            info.copy(messageId = "${info.iat.time}-${info.from.phoneNumber.phoneNumberWithoutCountryCode()}")
        }.let(memoryMessage::addAll)
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
