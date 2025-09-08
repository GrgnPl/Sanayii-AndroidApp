package tr.com.sdateapp.ui.viewmodels

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import tr.com.sdateapp.R
import tr.com.sdateapp.logic.repository.MainRepository

class LoginViewModel(val context: Context, val mainRepository: MainRepository): ViewModel(){
    private val _toastMessage = MutableSharedFlow<String>()
    val toastMessage = _toastMessage.asSharedFlow()
    fun login(username:String, password:String){
        viewModelScope.launch {
            if (username.isEmpty()) _toastMessage.emit("Lütfen Kullanıcı Adını Giriniz")
            if (password.isEmpty()) _toastMessage.emit("Lütfen Şifrenizi Giriniz")

            val message = withContext(Dispatchers.IO) { mainRepository.login(username, password).message }
            _toastMessage.emit(message)
        }
    }
}