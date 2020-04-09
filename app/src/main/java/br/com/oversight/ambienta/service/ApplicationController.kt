package br.com.oversight.ambienta.service

import android.content.res.Resources
import androidx.multidex.MultiDexApplication
import com.google.gson.GsonBuilder
import com.orhanobut.hawk.Hawk
import okhttp3.CipherSuite
import okhttp3.ConnectionSpec
import okhttp3.OkHttpClient
import okhttp3.TlsVersion
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApplicationController : MultiDexApplication() {
    companion object {
        lateinit var applicationController: ApplicationController private set
        lateinit var service: DenunciaService private set
        lateinit var resourses: Resources
    }

    private var URL = "https://5e8e9343e0e7de001685f82d.mockapi.io/"

    override fun onCreate() {
        super.onCreate()
        configureRetrofit()
        applicationController = this
        Hawk.init(this).build()
        resourses = resources
    }

    private fun configureRetrofit() {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        val spec = ConnectionSpec.Builder(ConnectionSpec.MODERN_TLS)
            .tlsVersions(TlsVersion.TLS_1_2)
            .cipherSuites(
                CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256,
                CipherSuite.TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256,
                CipherSuite.TLS_DHE_RSA_WITH_AES_128_GCM_SHA256
            )
            .build()

        val client = OkHttpClient.Builder()
            //.connectionSpecs(Collections.singletonList(spec))
            .connectTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .retryOnConnectionFailure(false)
            .addInterceptor { chain ->
                val request = chain.request()
                    .newBuilder()
                    .addHeader("Accept", "application/json")
                    .addHeader("content-type", "application/json")
                    .build()
                chain.proceed(request)
            }
            .addInterceptor(logging)
            .build()

        val gson = GsonBuilder().create()

        service = Retrofit.Builder()
            .baseUrl(URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(DenunciaService::class.java)
    }
}