package tr.com.sdateapp


import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import tr.com.sdateapp.logic.di.appModule
import tr.com.sdateapp.logic.di.secureStorageModule

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MyApplication)
            modules(appModule)
            modules(secureStorageModule)
        }
    }
}
