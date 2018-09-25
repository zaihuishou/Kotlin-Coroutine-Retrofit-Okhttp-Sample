package com.tanzhiqiang.kmvvm.repository

import com.tanzhiqiang.kmvvm.mvvm.model.Weather
import kotlinx.coroutines.experimental.Deferred
import retrofit2.http.GET
import retrofit2.http.Path

interface Api {
    /**
     * 获取天气信息
     */
    @GET("zaihuishou/Kotlin-mvvm/master/data.json")
    fun getWeather(): Deferred<Weather>
}