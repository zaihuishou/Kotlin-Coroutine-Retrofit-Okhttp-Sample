package com.tanzhiqiang.kmvvm.repository

import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.experimental.CoroutineCallAdapterFactory
import com.tanzhiqiang.kmvvm.mvvm.model.Weather
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.async
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object HttpRepository {
    private fun getApiService(): Api {
        return Retrofit.Builder()
                .baseUrl("https://www.sojson.com/")
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .client(provideOkHttpClient(provideLoggingInterceptor()))
                .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
                .build()
                .create(Api::class.java)
    }

    private fun provideOkHttpClient(interceptor: HttpLoggingInterceptor): OkHttpClient = OkHttpClient.Builder().apply { addInterceptor(interceptor) }.build()

    private fun provideLoggingInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor()
            .apply { level = HttpLoggingInterceptor.Level.BODY }

    suspend fun getWeather(city: String): Deferred<Weather> {
        return getApiService().getWeather(city)
    }

}