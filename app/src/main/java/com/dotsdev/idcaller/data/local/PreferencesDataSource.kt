package com.dotsdev.idcaller.data.local

import com.dotsdev.idcaller.core.keychain.KeychainWrapper
import com.dotsdev.idcaller.data.model.SimInfo
import com.dotsdev.idcaller.data.model.User
import com.dotsdev.idcaller.data.prefsmodel.AccountPreference

class PreferencesDataSource {
    fun reset() {
        KeychainWrapper.clearAll()
        AccountPreference.clear()
    }
    var personaId: String
        get() = KeychainWrapper.get(KeychainWrapper.KeychainItem.PERSONA_ID)
        set(value) {
            KeychainWrapper.set(KeychainWrapper.KeychainItem.PERSONA_ID, value)
        }

    var userInfo: User?
        get() = AccountPreference.userInfo
        set(value) {
            AccountPreference.userInfo = value
        }

    var simInfo: List<SimInfo>?
        get() = AccountPreference.simInfo
        set(value) {
            AccountPreference.simInfo = value
        }
}
