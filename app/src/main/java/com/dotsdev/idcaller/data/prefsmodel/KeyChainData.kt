package com.dotsdev.idcaller.data.prefsmodel

import com.chibatching.kotpref.KotprefModel

object KeychainData : KotprefModel() {
    var accessToken by stringPref(default = "")
    var refreshToken by stringPref(default = "")
    var personaId by stringPref(default = "")
    var tmpPassword by stringPref(default = "")
    var newEmail by stringPref(default = "")
    var isLoggedIn by stringPref(default = "")
}