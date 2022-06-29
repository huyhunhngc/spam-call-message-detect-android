package com.dotsdev.idcaller.widget.recycler

import androidx.annotation.DrawableRes
import com.dotsdev.idcaller.data.model.CallGroup
import com.dotsdev.idcaller.data.model.Contact
import com.dotsdev.idcaller.data.model.MessageGroup
import com.dotsdev.idcaller.utils.convertTimestampToHours
import com.dotsdev.idcaller.utils.isPhoneNumber
import java.io.Serializable

data class ContactMessageInfo(
    val dataFrom: FromData,
    val peerPhotoUrl: String = "",
    val peerName: String = "",
    val primaryLine: String = "",
    val time: String = "",
    val subLine: String = "",
    val type: ItemType = ItemType.CONTACT,
    val unknownNumber: Boolean = false,
    @DrawableRes val subLineStartIcon: Int? = null
) : Serializable

data class ListContactMessageInfo(
    val contactMessageInfos: List<ContactMessageInfo> = listOf()
) : Serializable

enum class ItemType {
    CONTACT,
    CALL,
    MESSAGE
}

fun Contact.toInfoData(): ContactMessageInfo {
    return ContactMessageInfo(
        dataFrom = FromData.FromContact(this),
        peerName = this.callerName,
        primaryLine = this.callerName,
        subLine = this.phoneNumber,
        type = ItemType.CONTACT
    )
}

fun MessageGroup.toInfoData(): ContactMessageInfo {
    val message = this.messages.first()
    return ContactMessageInfo(
        dataFrom = FromData.FromMessageGroup(this),
        peerName = message.contact.callerName,
        time = message.iat.time.convertTimestampToHours(),
        primaryLine = message.contact.callerName,
        subLine = message.content,
        type = ItemType.MESSAGE,
        unknownNumber = message.contact.callerName == message.messageNumber
    )
}

fun CallGroup.toInfoData(): ContactMessageInfo {
    val call = this.calls.firstOrNull()
        ?: return ContactMessageInfo(dataFrom = FromData.FromCallGroup(this))
    val primaryLine = if (this.calls.size > 1) {
        "${call.contact.callerName} (${this.calls.size})"
    } else {
        call.contact.callerName
    }
    return ContactMessageInfo(
        dataFrom = FromData.FromCallGroup(this),
        peerName = call.contact.callerName,
        primaryLine = primaryLine,
        time = call.iat.time.convertTimestampToHours(),
        subLine = "${call.callType.value} • ${call.duration}s",
        subLineStartIcon = call.callType.icon,
        type = ItemType.CALL,
        unknownNumber = call.callerNumber == call.contact.callerName
    )
}

sealed class FromData : Serializable {
    data class FromContact(val contact: Contact) : FromData()
    data class FromCallGroup(val callGroup: CallGroup) : FromData()
    data class FromMessageGroup(val messageGroup: MessageGroup) : FromData()
}
