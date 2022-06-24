package com.dotsdev.idcaller.data.prefsmodel

import com.chibatching.kotpref.KotprefModel

object KeychainData : KotprefModel() {
    var personaId by stringPref(default = "")
    var newEmail by stringPref(default = "")
    var isLoggedIn by stringPref(default = "")
}
