package com.dotsdev.idcaller.widget.recycler

import androidx.annotation.DrawableRes
import com.dotsdev.idcaller.data.model.CallGroup
import com.dotsdev.idcaller.data.model.Contact
import com.dotsdev.idcaller.utils.convertTimestampToHours

data class ContactMessageInfo(
    val peerPhotoUrl: String = "",
    val peerName: String = "",
    val subLine: String = "",
    val type: ItemType = ItemType.CONTACT,
    val unknownNumber: Boolean = false,
    @DrawableRes val subLineStartIcon: Int? = null
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
        subLine = "${call.callType.value} • ${call.iat.time.convertTimestampToHours()}",
        subLineStartIcon = call.callType.icon,
        type = ItemType.CALL,
        unknownNumber = call.callerNumber == call.contact.callerName
    )
}
