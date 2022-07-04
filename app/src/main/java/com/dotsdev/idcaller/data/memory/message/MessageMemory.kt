package com.dotsdev.idcaller.data.memory.message

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asFlow
import androidx.lifecycle.asLiveData
import com.dotsdev.idcaller.data.model.Contact
import com.dotsdev.idcaller.data.model.Message
import com.dotsdev.idcaller.utils.phoneNumberWithoutCountryCode
import kotlinx.coroutines.flow.*

open class MessageMemory {
    private val stateFlow = MutableStateFlow(listOf<Message>())
    val memoryMessage = mutableListOf<Message>()
    val obs = MutableLiveData<List<Message>>()

    fun observe(): Flow<List<Message>> {
        return stateFlow.asSharedFlow()
    }

    fun set(info: List<Message>) {
        memoryMessage.clear()
        memoryMessage.addAll(
            info.map {
                it.copy(messageId = "${it.iat.time}-${it.from.phoneNumber.phoneNumberWithoutCountryCode()}")
            }.distinctBy { it.messageId }
        )
        obs.postValue(memoryMessage)
        stateFlow.tryEmit(info)
    }

    fun save(info: List<Message>) {
        memoryMessage.clear()
        memoryMessage.addAll(
            info.map {
                it.copy(messageId = "${it.iat.time}-${it.from.phoneNumber.phoneNumberWithoutCountryCode()}")
            }
        )
    }

    fun add(messages: List<Message>) {
        messages.map { info ->
            info.copy(messageId = "${info.iat.time}-${info.from.phoneNumber.phoneNumberWithoutCountryCode()}")
        }.let(memoryMessage::addAll)
        obs.postValue(memoryMessage)
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
