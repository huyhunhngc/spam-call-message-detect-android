package com.dotsdev.idcaller.data.prefsmodel

import com.chibatching.kotpref.KotprefModel
import com.chibatching.kotpref.gsonpref.gsonNullablePref
import com.dotsdev.idcaller.data.model.User

object AccountPreference: KotprefModel() {
    var userInfo by gsonNullablePref<User>(null)
    var firebaseToken by stringPref()
    var verificationAuthValue by nullableStringPref(null)
    var verificationSessionId by nullableStringPref(null)
    var passwordResetSessionId by nullableStringPref(null)
    var passwordResetCode by nullableStringPref(null)
}