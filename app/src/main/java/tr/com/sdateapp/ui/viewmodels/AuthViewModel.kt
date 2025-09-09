package tr.com.sdateapp.ui.viewmodels

import androidx.lifecycle.ViewModel
import tr.com.sdateapp.ui.utils.SecureStorage
import tr.com.sdateapp.ui.utils.SecureStorageImp

class AuthViewModel(private val secureStorage: SecureStorage): ViewModel() {

    fun saveToken(token: String) {
        secureStorage.saveKey(token, System.currentTimeMillis() + 3600_000)
    }

    fun getToken(): String? {
        return secureStorage.getKey()
    }
}