package com.example.idcaller.core.keychain

import kotlinx.coroutines.flow.Flow

object KeychainWrapper {
    private val instance = Keychain

    enum class KeychainItem(val key: String) {
        ACCESS_TOKEN("accessToken"),
        REFRESH_TOKEN("refreshToken"),
        PERSONA_ID("personaId"),
        TMP_PASSWORD("password"),
        NEW_EMAIL("newEmail"),
        IS_LOGGED_IN("isLoggedIn")
    }

    fun set(key: KeychainItem, value: String) {
        instance.setValue(key, value)
    }

    fun get(key: KeychainItem): String {
        return instance.getValue(key)
    }

    fun observePersonaId(): Flow<String> {
        return instance.observePersonaIdValue()
    }

    fun clear(key: KeychainItem) {
        instance.removeKey(key)
    }

    fun clearAll() {
        KeychainItem.values().forEach { item ->
            clear(item)
        }
    }
}
