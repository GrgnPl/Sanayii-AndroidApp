package tr.com.boer.boerfixturematerialstoreapp.data.network

import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import tr.com.sdateapp.logic.rest.MainInterface
import tr.com.sdateapp.logic.rest.MenuInterface
import java.security.SecureRandom
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

/*object RetrofitInstance {
    private val json = Json {
        ignoreUnknownKeys = true
        encodeDefaults = true
    }

    private val retrofit: Retrofit by lazy {
        val client: OkHttpClient = OkHttpClient.Builder()
            .connectTimeout(100, TimeUnit.SECONDS)
            .readTimeout(100, TimeUnit.SECONDS)
            .writeTimeout(100, TimeUnit.SECONDS)
            .callTimeout(100, TimeUnit.SECONDS)
            .build()

        Retrofit.Builder()
            .baseUrl("https://www.example.com/")
            .client(client)
            .addConverterFactory(
                json.asConverterFactory("application/json; charset=UTF-8".toMediaType())
            )
            .build()
    }

    val getInstance: BoerStoreApi by lazy {
        retrofit.create(BoerStoreApi::class.java)
    }
}*/
/*object RetrofitInstance {
    private val json = Json {
        ignoreUnknownKeys = true
        encodeDefaults = true
    }
    //Saniyeleri Düşürdük
    //Normalde 100 Saniyeydi okuma yazma bağlanma süreleri 10 Saniyeye Düşürdük
    private val retrofit: Retrofit by lazy {
        val client: OkHttpClient = OkHttpClient.Builder()
            .connectTimeout(20, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .writeTimeout(20, TimeUnit.SECONDS)
            .callTimeout(20, TimeUnit.SECONDS)
            .build()

        Retrofit.Builder()
            .baseUrl("https://www.example.com/")
            .client(client)
            .addConverterFactory(
                json.asConverterFactory("application/json; charset=UTF-8".toMediaType())
            )
            .build()
    }

    val getInstance: BoerStoreApi by lazy {
        retrofit.create(BoerStoreApi::class.java)
    }
}*/

object RetrofitInstance {
    private val json = Json {
        ignoreUnknownKeys = true
        encodeDefaults = true
    }


    private val retrofit: Retrofit by lazy {
        val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
            override fun checkClientTrusted(chain: Array<X509Certificate>, authType: String) {}

            override fun checkServerTrusted(chain: Array<X509Certificate>, authType: String) {}

            override fun getAcceptedIssuers(): Array<X509Certificate> = arrayOf()
        })

        val sslContext = SSLContext.getInstance("SSL").apply {
            init(null, trustAllCerts, SecureRandom())
        }

        val sslSocketFactory = sslContext.socketFactory

        val client: OkHttpClient = OkHttpClient.Builder()
            .connectTimeout(20, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .writeTimeout(20, TimeUnit.SECONDS)
            .callTimeout(20, TimeUnit.SECONDS)
            .addInterceptor { chain ->
                val original = chain.request()
                val requestBuilder = original.newBuilder()
                    .header("Content-Type", "application/json")
                    .header("Accept", "application/json")
                    .method(original.method, original.body)

                chain.proceed(requestBuilder.build())
            }
            .sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
            .hostnameVerifier(HostnameVerifier { _, _ -> true })
            .build()

        Retrofit.Builder()
            .baseUrl("https://www.example.com/")
            .client(client)
            .addConverterFactory(json.asConverterFactory("application/json; charset=UTF-8".toMediaType()))
            .build()
    }

    val getMainInstance: MainInterface by lazy {
        retrofit.create(MainInterface::class.java)
    }
    val getMenuInstance: MenuInterface by lazy {
        retrofit.create(MenuInterface::class.java)
    }

}