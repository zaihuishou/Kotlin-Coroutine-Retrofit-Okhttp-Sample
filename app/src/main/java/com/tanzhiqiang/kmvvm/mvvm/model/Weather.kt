package com.tanzhiqiang.kmvvm.mvvm.model


data class Weather(
        val time: String,
        val cityInfo: CityInfo,
        val date: String,
        val message: String,
        val status: Int,
        val data: Data
)

data class Data(
        val shidu: String,
        val pm25: Double,
        val pm10: Double,
        val quality: String,
        val wendu: String,
        val ganmao: String,
        val yesterday: Yesterday,
        val forecast: List<Forecast>
)

data class Yesterday(
        val date: String,
        val sunrise: String,
        val high: String,
        val low: String,
        val sunset: String,
        val aqi: Double,
        val fx: String,
        val fl: String,
        val type: String,
        val notice: String
)

data class Forecast(
        val date: String,
        val sunrise: String,
        val high: String,
        val low: String,
        val sunset: String,
        val aqi: Double,
        val fx: String,
        val fl: String,
        val type: String,
        val notice: String
)

data class CityInfo(
        val city: String,
        val cityId: String,
        val parent: String,
        val updateTime: String
)