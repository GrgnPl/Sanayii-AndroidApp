package tr.com.sdateapp.logic.di

import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import tr.com.sdateapp.logic.repository.MainRepository
import tr.com.sdateapp.ui.viewmodels.LoginViewModel
import tr.com.sdateapp.ui.viewmodels.SignupViewModel

val appModule = module {
    single { MainRepository() }
    viewModel { LoginViewModel(androidApplication(), get()) }
    viewModel { SignupViewModel(androidApplication(), get()) }
}