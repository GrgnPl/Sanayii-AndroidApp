package tr.com.sdateapp.logic.services

import android.R.attr.data
import javax.crypto.Cipher

class AuthServices(val key: String){

    private fun encyrptKey(): Pair<ByteArray, ByteArray>{
        val cipher = Cipher.getInstance("AES/GCM/NoPadding")
        cipher.init(Cipher.ENCRYPT_MODE, getSecretKey())
        val iv = cipher.iv // initialization vector
        val encrypted = cipher.doFinal(data.toByteArray(Charsets.UTF_8))
        return Pair(encrypted, iv)

    }
}