package com.dotsdev.idcaller.data.model

import android.provider.CallLog
import java.io.Serializable
import java.util.*

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

enum class CallType(val value: String) {
    OUTGOING("Out going"),
    INCOMING("In coming"),
    MISSED("Missed"),
    UNKNOWN("Unknown"),
}

data class CallGroup(
    val callId: String = "",
    val calls: MutableList<Call> = mutableListOf()
)

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
                    calls = mutableListOf(call)
                )
            )
        }
    }
    return callGroups
}