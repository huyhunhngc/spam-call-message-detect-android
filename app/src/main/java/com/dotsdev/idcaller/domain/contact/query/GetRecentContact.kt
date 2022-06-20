package com.dotsdev.idcaller.domain.contact.query

import com.dotsdev.idcaller.data.memory.contact.CallMemory
import com.dotsdev.idcaller.data.memory.contact.ContactMemory
import com.dotsdev.idcaller.data.model.Contact
import com.dotsdev.idcaller.data.model.toCallRecentData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map

class GetRecentContact(
    private val callMemory: CallMemory,
    private val contactMemory: ContactMemory
) {
    fun observeRecent(): Flow<List<Contact>> {
        return callMemory.observe().combine(contactMemory.observe()) { calls, contacts ->
            calls.map { call ->
                val contact = contacts.find { call.callerNumber == it.phoneNumber }
                call.copy(
                    contact = contact ?: Contact(
                        phoneNumber = call.callerNumber,
                        callerName = call.callerNumber
                    )
                )
            }.toCallRecentData().sortedByDescending { it.calls.size }.take(20).map {
                it.calls.first().contact
            }
        }
    }
}
