package com.julie.adchakathon.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.julie.adchakathon.remote.ADCService
import com.julie.adchakathon.repositories.AuthRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    private fun provideLogger(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(gson: Gson): ADCService = Retrofit.Builder()
        .baseUrl("http://api.openweathermap.org/data/2.5/")
        .client(provideLogger())
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()
        .create(ADCService::class.java)


    @Provides
    fun provideGson(): Gson = GsonBuilder().create()

    @Singleton
    @Provides
    fun provideAuthRepository(adcService: ADCService) =
        AuthRepo(adcService)

}