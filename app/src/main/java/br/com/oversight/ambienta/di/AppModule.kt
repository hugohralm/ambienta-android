package br.com.oversight.ambienta.di

import android.content.Context
import android.os.Vibrator
import br.com.oversight.ambienta.AmbientaApplication
import br.com.oversight.ambienta.BuildConfig
import br.com.oversight.ambienta.service.BooleanTypeAdapter
import br.com.oversight.ambienta.service.DenunciaService
import br.com.oversight.ambienta.service.LiveDataCallAdapterFactory
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @Singleton
    fun provideContext(application: AmbientaApplication): Context {
        return application
    }

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .registerTypeAdapter(Boolean::class.java, BooleanTypeAdapter())
            .create()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val client = OkHttpClient.Builder()
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
            .retryOnConnectionFailure(false)
            .addInterceptor { chain ->
                val request = chain.request()
                    .newBuilder()
                    .addHeader("Accept", "application/json")
                    .addHeader("content-type", "application/json")
                    .build()
                chain.proceed(request)
            }
            .addInterceptor(HttpLoggingInterceptor().also { it.level =  HttpLoggingInterceptor.Level.BODY})
        return client.build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient, gson: Gson): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.API_URL)
            .client(client)
            .addCallAdapterFactory(LiveDataCallAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    @Provides
    @Singleton
    fun provideVibrator(context: Context): Vibrator {
        return (context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator)
    }

    @Provides
    @Singleton
    fun provideDenunciaService(retrofit: Retrofit): DenunciaService {
        return retrofit.create(DenunciaService::class.java)
    }

}