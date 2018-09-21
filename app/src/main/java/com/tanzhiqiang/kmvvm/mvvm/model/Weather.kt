package com.tanzhiqiang.kmvvm.mvvm.model


data class Weather(
        val date: String,
        val message: String,
        val status: Int,
        val city: String,
        val count: Int,
        val data: Data
)

data class Data(
        val shidu: String,
        val pm25: Int,
        val pm10: Int,
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
        val aqi: Int,
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
        val aqi: Int,
        val fx: String,
        val fl: String,
        val type: String,
        val notice: String
)