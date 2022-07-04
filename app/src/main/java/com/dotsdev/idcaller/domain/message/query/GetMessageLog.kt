package com.dotsdev.idcaller.domain.message.query

import androidx.lifecycle.asFlow
import com.dotsdev.idcaller.data.memory.contact.ContactMemory
import com.dotsdev.idcaller.data.memory.message.MessageMemory
import com.dotsdev.idcaller.data.memory.message.SpamMessageMemory
import com.dotsdev.idcaller.data.model.Contact
import com.dotsdev.idcaller.data.model.Message
import com.dotsdev.idcaller.utils.phoneNumberWithoutCountryCode
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.map

class GetMessageLog(
    private val messageMemory: MessageMemory,
    private val spamMessageMemory: SpamMessageMemory,
    private val contactMemory: ContactMemory
) {
    fun observeMessage(): Flow<List<Message>> {
        return messageMemory.obs.asFlow().combine(contactMemory.observe()) { messages, contacts ->
            messages.map { message ->
                val number = kotlin.runCatching {
                    message.messageNumber.phoneNumberWithoutCountryCode()
                }.getOrNull()
                val contact = contacts.find {
                    number == it.phoneNumber
                }
                kotlin.runCatching {
                    message.copy(
                        contact = contact ?: Contact(
                            phoneNumber = message.messageNumber,
                            callerName = message.messageName
                        )
                    )
                }.getOrDefault(message)
            }.sortedByDescending {
                it.iat
            }
        }
    }

    fun observeSpam(): Flow<List<Message>> {
        return spamMessageMemory.obs.asFlow().combine(contactMemory.observe()) { messages, contacts ->
            messages.map { message ->
                val number = kotlin.runCatching {
                    message.messageNumber.phoneNumberWithoutCountryCode()
                }.getOrNull()
                val contact = contacts.find {
                    number == it.phoneNumber
                }
                message.copy(
                    contact = contact ?: Contact(
                        phoneNumber = message.messageNumber,
                        callerName = message.messageName
                    )
                )
            }.sortedByDescending {
                it.iat
            }
        }
    }
}
