package com.dotsdev.idcaller.data.model

import com.dotsdev.idcaller.utils.convertTimestampToHours
import java.io.Serializable

data class User(
    val externalId: String = "",
    val phoneNumber: String = "",
    val lastCorrectLogoutAt: Long? = null,
    val personas: List<Any> = listOf(),
    val updatedAt: Long? = null,
    val createdAt: Long? = null,
    val name: String = "",
    val familyName: String = "",
    val firstName: String = "",
    val nickname: String = "",
    val personaId: String = "",
    val fcmTokens: List<String> = listOf(),
    val id: String = "",
    val photoUri: String = "",
    val email: String = "",
    val gender: String = "",
    val birthDay: String = ""
) : Serializable {
    val createAtFormat: String
        get() = (createdAt ?: 0).convertTimestampToHours()
}
