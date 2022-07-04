package com.dotsdev.idcaller.data.prefsmodel

import com.chibatching.kotpref.KotprefModel
import com.chibatching.kotpref.gsonpref.gsonNullablePref
import com.dotsdev.idcaller.data.model.SimInfo
import com.dotsdev.idcaller.data.model.User

object AccountPreference : KotprefModel() {
    var userInfo by gsonNullablePref<User>(null)
    var simInfo by gsonNullablePref<List<SimInfo>>(null)
}

object CachePreference : KotprefModel() {
    var isFirstOpenApp by booleanPref(true)
    var isHasGrantPermission by booleanPref(false)
    var isCompleteInfo by booleanPref(false)
}

object AppPreference : KotprefModel() {
    var isNightMode by booleanPref(false)
}
