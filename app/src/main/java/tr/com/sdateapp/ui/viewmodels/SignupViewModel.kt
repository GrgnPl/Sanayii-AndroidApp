package tr.com.sdateapp.ui.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import tr.com.sdateapp.logic.repository.MainRepository

class SignupViewModel(
    val context: Context,
    val mainRepository: MainRepository
): ViewModel() {
    private val _signUptoastMessage = MutableSharedFlow<String>()
    val signUptoastMessage = _signUptoastMessage.asSharedFlow()

    suspend fun signUp(username: String, password: String, aracPlakasi: String): Boolean {
        if (username.isEmpty()) {
            _signUptoastMessage.emit("Lütfen Kullanıcı Adını Giriniz")
            return false
        }
        if (password.isEmpty()) {
            _signUptoastMessage.emit("Lütfen Şifrenizi Giriniz")
            return false
        }
        if (aracPlakasi.isEmpty()) {
            _signUptoastMessage.emit("Lütfen Araç Plakanızı Giriniz")
            return false
        }

        val message = withContext(Dispatchers.IO) {
            mainRepository.signUp(username, password, aracPlakasi).message
        }
        _signUptoastMessage.emit(message)

        return message.contains("Başarılı", ignoreCase = true)
    }
}