package com.dotsdev.idcaller.data.local

import com.dotsdev.idcaller.core.keychain.KeychainWrapper
import com.dotsdev.idcaller.data.model.User
import com.dotsdev.idcaller.data.prefsmodel.AccountPreference

class PreferencesDataSource {
    fun reset() {
        KeychainWrapper.clearAll()
        AccountPreference.clear()
    }

    var refreshToken: String
        get() = KeychainWrapper.get(KeychainWrapper.KeychainItem.REFRESH_TOKEN)
        set(value) {
            KeychainWrapper.set(KeychainWrapper.KeychainItem.REFRESH_TOKEN, value)
        }

    var accessToken: String
        get() = KeychainWrapper.get(KeychainWrapper.KeychainItem.ACCESS_TOKEN)
        set(value) {
            KeychainWrapper.set(KeychainWrapper.KeychainItem.ACCESS_TOKEN, value)
        }

    var firebaseToken: String
        get() = AccountPreference.firebaseToken
        set(value) {
            AccountPreference.firebaseToken = value
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
}
