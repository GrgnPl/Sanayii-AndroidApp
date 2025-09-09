package tr.com.sdateapp.ui.utils

interface SecureStorage {
    fun saveKey(key: String, expireAt: Long)
    fun getKey(): String?
    fun getExpireAt(): Long?
    fun isKeyValid(): Boolean
    fun clearKey()
}