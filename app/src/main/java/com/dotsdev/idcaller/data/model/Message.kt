package com.dotsdev.idcaller.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dotsdev.idcaller.utils.phoneNumberWithoutCountryCode
import java.util.*

@Entity(tableName = "message")
data class Message(
    @PrimaryKey val uniqueId: String = "",
    @ColumnInfo(name = "from") val from: Contact,
    @ColumnInfo(name = "type") val type: MessageType = MessageType.SMS,
    @ColumnInfo(name = "content") val content: String,
    @ColumnInfo(name = "iat") val iat: Date
)

enum class MessageType(val value: String) {
    SMS("sms"),
    MMS("mms")
}
