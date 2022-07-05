package com.dotsdev.idcaller.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
import java.util.Date

@Entity(tableName = "message")
data class Message(
    @PrimaryKey val messageId: String = "",
    @ColumnInfo(name = "from") val from: Contact = Contact(),
    @ColumnInfo(name = "type") val type: MessageType = MessageType.SMS,
    @ColumnInfo(name = "content") val content: String = "",
    @ColumnInfo(name = "iat") val iat: Date = Date(),
    @ColumnInfo(name = "messageName") val messageName: String = "",
    @ColumnInfo(name = "messageNumber") val messageNumber: String = "",
    @ColumnInfo(name = "isSpam") var isSpam: Boolean = false,
    @ColumnInfo(name = "contact") val contact: Contact = Contact(),
    @ColumnInfo(name = "sentByMe") var sentByMe: Boolean = false,
    @ColumnInfo(name = "originalAddress") var originalAddress: String = ""
) : Serializable

enum class MessageType(val value: String) {
    SMS("sms"),
    MMS("mms")
}

data class MessageGroup(
    val groupId: String = "",
    val messages: MutableList<Message> = mutableListOf()
) : Serializable

fun List<Message>.toMessageGroup(): List<MessageGroup> {
    val messageGroups = mutableListOf<MessageGroup>()
    this.forEach { message ->
        if (messageGroups.map { it.groupId }.contains(message.messageNumber)) {
            messageGroups.find { it.groupId == message.messageNumber }?.messages?.add(message)
        } else {
            messageGroups.add(
                MessageGroup(
                    groupId = message.messageNumber,
                    messages = mutableListOf(message)
                )
            )
        }
    }
    return messageGroups
}
