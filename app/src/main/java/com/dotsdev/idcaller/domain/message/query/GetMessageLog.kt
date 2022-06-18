package com.dotsdev.idcaller.domain.message.query

import com.dotsdev.idcaller.data.memory.contact.ContactMemory
import com.dotsdev.idcaller.data.memory.message.MessageMemory
import com.dotsdev.idcaller.data.model.Contact
import com.dotsdev.idcaller.data.model.Message
import com.dotsdev.idcaller.utils.phoneNumberWithoutCountryCode
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetMessageLog(
    private val messageMemory: MessageMemory,
    private val contactMemory: ContactMemory
) {
    fun observeMessage(): Flow<List<Message>> {
        val contacts = contactMemory.get()
        return messageMemory.observe().map { messages ->
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
            }.also {
                messageMemory.set(it)
            }
        }
    }
}
