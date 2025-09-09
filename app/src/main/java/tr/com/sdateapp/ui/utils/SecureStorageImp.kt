package tr.com.sdateapp.ui.utils

import android.content.Context
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec

class SecureStorageImp(private val context: Context) : SecureStorage {

    private val fileName = "secure_key.bin"
    private val ivFileName = "secure_iv.bin"
    private val keyAlias = "SanayiiAppAlias"
    private val expireFileName = "secure_expire.txt"

    init {
        if (getSecretKey() == null) {
            generateSecretKey()
        }
    }

    private fun generateSecretKey(){
        val keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore") //Sağlayıcı ve Algoritma

        val parameterSpec = KeyGenParameterSpec.Builder(
            keyAlias, //keystore da saklanacak alanismi
            KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT //hem encyrpt hem decscrpt
        )
            .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
            .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
            .setUserAuthenticationRequired(false) //Parmakİzi Pin veya Yuz Tanima İslemlerinde True
            .build()

        keyGenerator.init(parameterSpec)
        keyGenerator.generateKey()
    }

    private fun getSecretKey(): SecretKey? {
        val keyStore = KeyStore.getInstance("AndroidKeyStore") // keyStore dan nesne olustur
        keyStore.load(null) //yukle
        return keyStore.getKey(keyAlias, null) as? SecretKey //bizim app kismindan key dondur
    }

    override fun saveKey(key: String, expireAt: Long) {
        val cipher = Cipher.getInstance("AES/GCM/NoPadding")
        cipher.init(Cipher.ENCRYPT_MODE, getSecretKey())
        val iv = cipher.iv
        val encrypted = cipher.doFinal(key.toByteArray(Charsets.UTF_8))

        context.openFileOutput(fileName, Context.MODE_PRIVATE).use {
            it.write(encrypted)
        }
        context.openFileOutput(ivFileName, Context.MODE_PRIVATE).use {
            it.write(iv)
        }
        context.openFileOutput(expireFileName, Context.MODE_PRIVATE).use {
            it.write(expireAt.toString().toByteArray())
        }    }

    override fun getKey(): String? {
        if (!isKeyValid()) return null

        val encrypted = try {
            context.openFileInput(fileName).readBytes()
        } catch (e: Exception) {
            return null
        }

        val iv = try {
            context.openFileInput(ivFileName).readBytes()
        } catch (e: Exception) {
            return null
        }

        val cipher = Cipher.getInstance("AES/GCM/NoPadding")
        val spec = GCMParameterSpec(128, iv)
        cipher.init(Cipher.DECRYPT_MODE, getSecretKey(), spec)
        val decoded = cipher.doFinal(encrypted)
        return String(decoded, Charsets.UTF_8)    }

    override fun getExpireAt(): Long? {
        return try {
            val data = context.openFileInput(expireFileName).readBytes()
            String(data).toLong()
        } catch (e: Exception) {
            null
        }
    }

    override fun isKeyValid(): Boolean {
        val expire = getExpireAt() ?: return false
        return System.currentTimeMillis() < expire
    }

    override fun clearKey() {
        context.deleteFile(fileName)
        context.deleteFile(ivFileName)
        context.deleteFile(expireFileName)
    }


}