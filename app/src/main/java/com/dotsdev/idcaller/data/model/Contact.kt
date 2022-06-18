package com.dotsdev.idcaller.data.model

import java.io.Serializable

data class Contact(
    val phoneNumber: String = "",
    val callerName: String = "",
    val photoId: String = ""
) : Serializable
