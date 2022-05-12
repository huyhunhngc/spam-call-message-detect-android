package com.dotsdev.idcaller.data.model

import java.util.*

data class Message(
    val from: Contact,
    val type: MessageType = MessageType.SMS,
    val content: String,
    val iat: Date
)

enum class MessageType(val value: String) {
    SMS("sms"),
    MMS("mms")
}
