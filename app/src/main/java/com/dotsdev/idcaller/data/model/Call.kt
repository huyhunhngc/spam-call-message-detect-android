package com.dotsdev.idcaller.data.model

import android.provider.CallLog
import androidx.annotation.DrawableRes
import com.dotsdev.idcaller.R
import java.io.Serializable
import java.util.*

data class Call(
    val callerId: String = "",
    val callerName: String = "",
    val callerNumber: String = "",
    val iat: Date = Date(),
    val isBlock: Boolean = false,
    val callType: CallType = CallType.UNKNOWN,
    val duration: String = "",
    val contact: Contact = Contact(),
) : Serializable

enum class CallType(val value: String, @DrawableRes val icon: Int) {
    OUTGOING("Outgoing", R.drawable.ic_outgoing_call),
    INCOMING("Incoming", R.drawable.ic_incomming_call),
    MISSED("Missed", R.drawable.ic_missed_call),
    UNKNOWN("Unknown", R.drawable.ic_baseline_app_blocking_24),
}

fun Int.toCallType(): CallType {
    return when (this) {
        CallLog.Calls.OUTGOING_TYPE -> CallType.OUTGOING
        CallLog.Calls.INCOMING_TYPE -> CallType.INCOMING
        CallLog.Calls.MISSED_TYPE -> CallType.MISSED
        else -> CallType.UNKNOWN
    }
}