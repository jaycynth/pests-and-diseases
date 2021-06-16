package com.julie.adchakathon.di

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.julie.adchakathon.remote.ADCService
import com.julie.adchakathon.remote.AuthRemoteDataSource
import com.julie.adchakathon.remote.HomeRemoteDataSource
import com.julie.adchakathon.repositories.AuthRepo
import com.julie.adchakathon.repositories.HomeRepo
import com.julie.adchakathon.utils.Constants.SHARED_PREFERENCES_NAME
import com.julie.adchakathon.utils.Constants.USER_FIRST_TIME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
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
        .baseUrl("https://tranquil-falls-15489.herokuapp.com/api/")
        .client(provideLogger())
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()
        .create(ADCService::class.java)


    @Provides
    fun provideGson(): Gson = GsonBuilder().create()

    @Singleton
    @Provides
    fun provideAuthRemoteDataSource(adcService: ADCService) =
        AuthRemoteDataSource(adcService)

    @Singleton
    @Provides
    fun provideHomeRemoteDataSource(adcService: ADCService) =
        HomeRemoteDataSource(adcService)


    @Singleton
    @Provides
    fun provideAuthRepository(authRemoteDataSource: AuthRemoteDataSource) =
        AuthRepo(authRemoteDataSource)

    @Singleton
    @Provides
    fun provideHomeRepository(homeRemoteDataSource: HomeRemoteDataSource) =
        HomeRepo(homeRemoteDataSource)

    @Singleton
    @Provides
    fun provideSharedPreferences(@ApplicationContext app: Context) =
        app.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)

    @Singleton
    @Provides
    fun provideFirstTimeToggle(sharedPref: SharedPreferences) =
        sharedPref.getBoolean(USER_FIRST_TIME, true)


}