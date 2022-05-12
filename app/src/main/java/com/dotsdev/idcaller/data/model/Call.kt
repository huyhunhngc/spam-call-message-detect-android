package com.dotsdev.idcaller.data.model

import java.io.Serializable
import java.util.*

data class Call(
    val callerId: String = "",
    val callerName: String = "",
    val callerNumber: String = "",
    val iat: Date = Date(),
    val isBlock: Boolean = false
): Serializable