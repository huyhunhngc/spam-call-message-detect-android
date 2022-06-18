package com.dotsdev.idcaller.domain.contact.query

import com.dotsdev.idcaller.data.memory.contact.CallMemory
import com.dotsdev.idcaller.data.memory.contact.ContactMemory
import com.dotsdev.idcaller.data.model.Call
import com.dotsdev.idcaller.data.model.Contact
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetCallLog(
    private val callMemory: CallMemory,
    private val contactMemory: ContactMemory
) {
    fun observeCall(): Flow<List<Call>> {
        val contacts = contactMemory.get()
        return callMemory.observe().map { calls ->
            calls.map { call ->
                val contact = contacts.find { call.callerNumber == it.phoneNumber }
                call.copy(
                    contact = contact ?: Contact(
                        phoneNumber = call.callerNumber,
                        callerName = call.callerNumber
                    )
                )
            }.also {
                callMemory.set(it)
            }
        }
    }
}
