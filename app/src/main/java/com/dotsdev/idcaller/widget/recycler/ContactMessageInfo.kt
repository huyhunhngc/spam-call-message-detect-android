package com.dotsdev.idcaller.widget.recycler

import com.dotsdev.idcaller.data.model.CallGroup
import com.dotsdev.idcaller.data.model.Contact

data class ContactMessageInfo(
    val peerPhotoUrl: String = "",
    val peerName: String = "",
    val subLine: String = "",
    val type: ItemType = ItemType.CONTACT,
    val unknownNumber: Boolean = false
)

enum class ItemType {
    CONTACT,
    CALL,
    MESSAGE
}

fun Contact.toInfoData(): ContactMessageInfo {
    return ContactMessageInfo(
        peerName = this.callerName,
        subLine = this.phoneNumber,
        type = ItemType.CONTACT
    )
}

fun CallGroup.toInfoData(): ContactMessageInfo {
    val call = this.calls.firstOrNull() ?: return ContactMessageInfo()
    val primaryLine = if (this.calls.size > 1) {
        "${call.contact.callerName} (${this.calls.size})"
    } else {
        call.contact.callerName
    }
    return ContactMessageInfo(
        peerName = primaryLine,
        subLine = call.callType.value,
        type = ItemType.CALL,
        unknownNumber = call.callerNumber == call.contact.callerName
    )
}
