package com.dotsdev.idcaller.data.model

import android.provider.CallLog
import androidx.annotation.DrawableRes
import com.dotsdev.idcaller.R
import java.io.Serializable
import java.util.Date

data class Call(
    val callId: String = "",
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
    UNKNOWN("Unknown", R.drawable.ic_help_question_mark),
}

data class CallGroup(
    val callId: String = "",
    val calls: MutableList<Call> = mutableListOf(),
    val contact: Contact
) : Serializable

fun Int.toCallType(): CallType {
    return when (this) {
        CallLog.Calls.OUTGOING_TYPE -> CallType.OUTGOING
        CallLog.Calls.INCOMING_TYPE -> CallType.INCOMING
        CallLog.Calls.MISSED_TYPE -> CallType.MISSED
        else -> CallType.UNKNOWN
    }
}

fun List<Call>.toCallGroup(): List<CallGroup> {
    val callGroups = mutableListOf<CallGroup>()
    this.forEach { call ->
        if (callGroups.map { it.callId }.contains(call.callId)) {
            callGroups.find { it.callId == call.callId }?.calls?.add(call)
        } else {
            callGroups.add(
                CallGroup(
                    callId = call.callId,
                    calls = mutableListOf(call),
                    contact = Contact()
                )
            )
        }
    }
    return callGroups
}

fun List<Call>.toCallRecentData(): List<CallGroup> {
    val callGroups = mutableListOf<CallGroup>()
    this.forEach { call ->
        if (callGroups.map { it.callId }.contains(call.callerNumber)) {
            callGroups.find { it.callId == call.callerNumber }?.calls?.add(call)
        } else {
            callGroups.add(
                CallGroup(
                    callId = call.callerNumber,
                    calls = mutableListOf(call),
                    contact = Contact()
                )
            )
        }
    }
    return callGroups
}
