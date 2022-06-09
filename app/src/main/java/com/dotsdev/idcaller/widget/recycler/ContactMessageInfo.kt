package com.dotsdev.idcaller.widget.recycler

import com.dotsdev.idcaller.data.model.Call
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

fun Call.toInfoData(): ContactMessageInfo {
    return ContactMessageInfo(
        peerName = this.contact.callerName,
        subLine = this.callType.value,
        type = ItemType.CALL,
        unknownNumber = this.callerNumber == this.contact.callerName
    )
}
