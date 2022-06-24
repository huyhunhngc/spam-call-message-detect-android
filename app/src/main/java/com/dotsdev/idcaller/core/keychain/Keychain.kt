package com.dotsdev.idcaller.core.keychain

import android.os.Build
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.util.Base64
import androidx.annotation.RequiresApi
import androidx.lifecycle.asFlow
import com.chibatching.kotpref.livedata.asLiveData
import com.dotsdev.idcaller.data.prefsmodel.KeychainData
import kotlinx.coroutines.flow.Flow
import java.io.*
import java.security.GeneralSecurityException
import java.security.Key
import java.security.KeyStore
import java.util.concurrent.atomic.AtomicBoolean
import javax.crypto.Cipher
import javax.crypto.CipherInputStream
import javax.crypto.CipherOutputStream
import javax.crypto.KeyGenerator
import javax.crypto.spec.IvParameterSpec

internal object Keychain {
    private const val ENCRYPTION_KEY_SIZE = 256
    private const val KEYSTORE_TYPE = "AndroidKeyStore"
    private const val aliasPrefix = "idcaller"
    private const val SECURITY_LEVEL = 1
    private const val ENCRYPTION_TRANSFORMATION =
        KeyProperties.KEY_ALGORITHM_AES + "/" + KeyProperties.BLOCK_MODE_CBC + "/" + KeyProperties.ENCRYPTION_PADDING_PKCS7

    private var syncObj: Any = Object()
    private var privateKey: Key? = null

    fun setValue(keychainItem: KeychainWrapper.KeychainItem, value: String) {
        val safeAlias = aliasPrefix + keychainItem.name
        val key: Key = extractGeneratedKey(safeAlias)
        val encryptedValue: String = encryptString(key, value)
        when (keychainItem) {
            KeychainWrapper.KeychainItem.PERSONA_ID -> {
                KeychainData.personaId = encryptedValue
            }
            KeychainWrapper.KeychainItem.NEW_EMAIL -> {
                KeychainData.newEmail = encryptedValue
            }
            KeychainWrapper.KeychainItem.IS_LOGGED_IN -> {
                KeychainData.isLoggedIn = encryptedValue
            }
        }
    }

    fun getValue(keychainItem: KeychainWrapper.KeychainItem): String {
        val safeAlias = aliasPrefix + keychainItem.name
        val value = when (keychainItem) {
            KeychainWrapper.KeychainItem.PERSONA_ID -> {
                KeychainData.personaId
            }
            KeychainWrapper.KeychainItem.NEW_EMAIL -> {
                KeychainData.newEmail
            }
            KeychainWrapper.KeychainItem.IS_LOGGED_IN -> {
                KeychainData.isLoggedIn
            }
        }
        return decryptValue(safeAlias, value)
    }

    fun removeKey(keychainItem: KeychainWrapper.KeychainItem) {
        val safeAlias = aliasPrefix + keychainItem.name
        try {
            val keystore = getKeystore()
            if (keystore.containsAlias(safeAlias)) {
                keystore.deleteEntry(safeAlias)
            }
            when (keychainItem) {
                KeychainWrapper.KeychainItem.PERSONA_ID -> {
                    KeychainData.remove(
                        KeychainData::personaId
                    )
                }
                KeychainWrapper.KeychainItem.NEW_EMAIL -> {
                    KeychainData.remove(
                        KeychainData::newEmail
                    )
                }
                KeychainWrapper.KeychainItem.IS_LOGGED_IN -> {
                    KeychainData.remove(
                        KeychainData::isLoggedIn
                    )
                }
            }
        } catch (err: GeneralSecurityException) {
        }
    }

    fun observePersonaIdValue(): Flow<String> {
        return KeychainData.asLiveData(
            KeychainData::personaId
        ).asFlow()
    }

    private fun decryptValue(alias: String, encryptedValue: String): String {
        if (encryptedValue.isEmpty()) return encryptedValue
        val key: Key = extractGeneratedKey(alias)
        val value: ByteArray = Base64.decode(encryptedValue, Base64.DEFAULT)
        return decryptBytes(key, value)
    }

    private fun extractGeneratedKey(alias: String): Key {
        val keystore = getKeystore()
        if (!keystore.containsAlias(alias)) {
            generateKeyAndStoreUnderAlias(
                alias,
                SECURITY_LEVEL
            )
        }
        return extractKey(
            keystore,
            alias
        )
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun getKeyGenSpecBuilder(alias: String): KeyGenParameterSpec.Builder {
        return KeyGenParameterSpec.Builder(
            alias,
            KeyProperties.PURPOSE_DECRYPT or KeyProperties.PURPOSE_ENCRYPT
        )
            .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
            .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7)
            .setRandomizedEncryptionRequired(true)
            .setKeySize(ENCRYPTION_KEY_SIZE)
    }

    private var cachedKeystore: KeyStore? = null
    private fun getKeystore(): KeyStore {
        synchronized(this) {
            if (null == cachedKeystore) {
                val keystore = KeyStore.getInstance(KEYSTORE_TYPE)
                keystore.load(null)
                cachedKeystore = keystore
            }
        }
        return cachedKeystore!!
    }

    private var isStrongboxAvailable: AtomicBoolean? = null
    private fun generateKeyAndStoreUnderAlias(alias: String, securityLevel: Int) {
        synchronized(syncObj) {
            isStrongboxAvailable = isStrongboxAvailable ?: AtomicBoolean(false)
            if (isStrongboxAvailable!!.get()) {
                try {
                    privateKey = tryGenerateStrongBoxSecurityKey(
                        alias
                    )
                    isStrongboxAvailable!!.set(true)
                } catch (e: GeneralSecurityException) {
                }
            }
            if (null == privateKey || !isStrongboxAvailable!!.get()) {
                try {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        privateKey = tryGenerateRegularSecurityKey(alias)
                    }
                } catch (e: GeneralSecurityException) {
                    throw e
                }
            }
        }
    }

    private fun tryGenerateStrongBoxSecurityKey(alias: String): Key {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.P) {
            throw GeneralSecurityException(
                "Strong box security keystore is not supported " +
                        "for old API" + Build.VERSION.SDK_INT + "."
            )
        } else {

            val spec = getKeyGenSpecBuilder(alias)
                .setIsStrongBoxBacked(true)
                .build()
            return generateKey(spec)
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun tryGenerateRegularSecurityKey(alias: String): Key {
        val spec = getKeyGenSpecBuilder(alias)
            .build()
        return generateKey(spec)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun generateKey(spec: KeyGenParameterSpec): Key {
        val generator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, KEYSTORE_TYPE)
        generator.init(spec)
        return generator.generateKey()
    }

    private fun extractKey(keyStore: KeyStore, alias: String): Key {
        return keyStore.getKey(alias, null)
    }

    private fun encryptString(key: Key, value: String): String {
        val cipher: Cipher = Cipher.getInstance(ENCRYPTION_TRANSFORMATION)

        ByteArrayOutputStream().use { output ->
            IV.encrypt.initialize(cipher, key, output)
            output.flush()
            val encryptStream: CipherOutputStream = CipherOutputStream(output, cipher)
            encryptStream.use {
                encryptStream.write(value.toByteArray(Charsets.UTF_8))
            }
            return Base64.encodeToString(output.toByteArray(), Base64.DEFAULT)
        }
    }

    private const val IV_LENGTH = 16

    private fun decryptBytes(key: Key, value: ByteArray): String {
        val cipher: Cipher = Cipher.getInstance(ENCRYPTION_TRANSFORMATION)

        ByteArrayInputStream(value).use { input ->
            IV.decrypt.initialize(cipher, key, input)
            ByteArrayOutputStream().use { output ->
                CipherInputStream(input, cipher).use { decrypt ->
                    copy(decrypt, output)
                }
                return String(output.toByteArray(), Charsets.UTF_8)
            }
        }
    }

    // 16kb
    private const val BUFFER_SIZE = 4 * 4 * 1024
    private fun copy(input: InputStream, output: OutputStream) {
        val buf = ByteArray(BUFFER_SIZE)
        var count = 0
        while (input.read(buf).let {
                count = it; it != -1
            }
        ) {
            output.write(buf, 0, count)
        }
    }

    @Throws(IOException::class)
    fun readIv(bytes: ByteArray): IvParameterSpec? {
        val iv = ByteArray(IV_LENGTH)
        if (IV_LENGTH <= bytes.size) throw IOException("Insufficient length of input data for IV extracting.")
        System.arraycopy(bytes, 0, iv, 0, IV_LENGTH)
        return IvParameterSpec(iv)
    }

    /** Initialization vector support.  */
    object IV {
        /** Encryption/Decryption initialization vector length.  */
        private const val IV_LENGTH = 16

        /** Save Initialization vector to output stream.  */
        val encrypt: EncryptStringHandler =
            object : EncryptStringHandler {
                override fun initialize(cipher: Cipher, key: Key, output: OutputStream) {
                    cipher.init(Cipher.ENCRYPT_MODE, key)
                    val iv = cipher.iv
                    output.write(iv, 0, iv.size)
                }
            }

        /** Read initialization vector from input stream and configure cipher by it.  */
        val decrypt: DecryptBytesHandler =
            object : DecryptBytesHandler {
                override fun initialize(cipher: Cipher, key: Key, input: InputStream) {
                    val iv = readIv(input)
                    cipher.init(Cipher.DECRYPT_MODE, key, iv)
                }
            }

        /** Extract initialization vector from provided bytes array.  */
        @Throws(IOException::class)
        fun readIv(bytes: ByteArray): IvParameterSpec {
            val iv = ByteArray(IV_LENGTH)
            if (IV_LENGTH <= bytes.size) throw IOException("Insufficient length of input data for IV extracting.")
            System.arraycopy(bytes, 0, iv, 0, IV_LENGTH)
            return IvParameterSpec(iv)
        }

        /** Extract initialization vector from provided input stream.  */
        @Throws(IOException::class)
        fun readIv(inputStream: InputStream): IvParameterSpec {
            val iv = ByteArray(IV_LENGTH)
            val result = inputStream.read(iv, 0, IV_LENGTH)
            if (result != IV_LENGTH) throw IOException("Input stream has insufficient data.")
            return IvParameterSpec(iv)
        }
    }

    /** Handler for storing cipher configuration in output stream.  */
    interface EncryptStringHandler {
        @Throws(GeneralSecurityException::class, IOException::class)
        fun initialize(
            cipher: Cipher,
            key: Key,
            output: OutputStream
        )
    }

    /** Handler for configuring cipher by initialization data from input stream.  */
    interface DecryptBytesHandler {
        @Throws(GeneralSecurityException::class, IOException::class)
        fun initialize(
            cipher: Cipher,
            key: Key,
            input: InputStream
        )
    }
}
