package com.dotsdev.idcaller.domain.contact.query

import com.dotsdev.idcaller.data.memory.contact.ContactMemory
import com.dotsdev.idcaller.data.model.Contact

class GetContact(
    private val contactMemory: ContactMemory
) {
    operator fun invoke(phoneNumber: String): Contact? {
        return contactMemory.get().find {
            it.phoneNumber == phoneNumber
        }
    }
}