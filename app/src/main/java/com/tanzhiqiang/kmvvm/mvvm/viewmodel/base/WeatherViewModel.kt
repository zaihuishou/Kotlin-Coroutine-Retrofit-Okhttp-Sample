package com.tanzhiqiang.kmvvm.mvvm.viewmodel.base

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.tanzhiqiang.kmvvm.mvvm.model.Weather
import com.tanzhiqiang.kmvvm.repository.HttpRepository
import kotlinx.coroutines.experimental.IO
import kotlinx.coroutines.experimental.async

class WeatherViewModel : BaseViewModel() {

    val mWeather: MutableLiveData<Weather> = MutableLiveData()

    fun getWeather(city: String) {
        launchOnUITryCatch(
                {
                    val weather = async(IO) { HttpRepository.getWeather(city) }.await()
                    mWeather.value = weather.await()
                }
                ,
                {
                    Log.i("tt", "${it.message}")
                }, {}, true)
    }
}