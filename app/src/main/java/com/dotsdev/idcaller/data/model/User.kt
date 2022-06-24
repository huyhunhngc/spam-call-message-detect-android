package com.dotsdev.idcaller.data.model

import com.dotsdev.idcaller.utils.convertTimestampToHours
import java.io.Serializable

data class User(
    val phoneNumber: String = "",
    val updatedAt: Long? = null,
    val createdAt: Long? = null,
    val name: String = "",
    val firstName: String = "",
    val personaId: String = "",
    val id: String = "",
    val photoUri: String = "",
    val email: String = "",
    val gender: String = "",
    val birthDay: String = ""
) : Serializable {
    val createAtFormat: String
        get() = (createdAt ?: 0).convertTimestampToHours()
}

data class SimInfo(
    val id: Int,
    val slot: Int,
    val displayName: String,
    val iccid: String,
)