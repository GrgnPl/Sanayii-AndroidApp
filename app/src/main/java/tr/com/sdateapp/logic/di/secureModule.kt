package tr.com.sdateapp.logic.di

import org.koin.compose.viewmodel.dsl.viewModel
import org.koin.dsl.module
import tr.com.sdateapp.ui.utils.SecureStorage
import tr.com.sdateapp.ui.utils.SecureStorageImp
import tr.com.sdateapp.ui.viewmodels.AuthViewModel

val secureStorageModule = module {
    single<SecureStorage> { SecureStorageImp(get()) }
    viewModel { AuthViewModel(get()) }
}