package com.muindi.stephen.co_opbankapp.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.muindi.stephen.co_opbankapp.data.remote.CoopCardsRemoteService
import com.muindi.stephen.co_opbankapp.domain.utils.constants.Constants.getStringBaseUrl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun providesBaseUrl(): String {
        return getStringBaseUrl()
    }

    private val httpLoggingInterceptor = HttpLoggingInterceptor()
        .setLevel(HttpLoggingInterceptor.Level.BODY)

    val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(httpLoggingInterceptor)
        .build()

    @Provides
    @Singleton
    fun providesGson(): Gson = GsonBuilder().setLenient().create()

    @Provides
    @Singleton
    fun providesRetrofit(
//        gson: Gson
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(getStringBaseUrl())
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun providesCardsRemoteService(retrofit: Retrofit): CoopCardsRemoteService {
        return retrofit.create(CoopCardsRemoteService::class.java)
    }
}