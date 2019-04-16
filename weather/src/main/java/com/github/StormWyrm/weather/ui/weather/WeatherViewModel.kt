package com.github.StormWyrm.weather.ui.weather

import android.app.Application
import com.github.StormWyrm.library.mvvm.AbsViewModel
import com.github.StormWyrm.weather.bean.place.Weather

class WeatherViewModel(application: Application) : AbsViewModel<WeatherRepository>(application) {
    var weatherId: String? = null

    var weather: Weather? = null

    var bingPicUrl: String? = null

    fun getWeather(weatherId: String, key: String) = mRepository.getWeather(weatherId, key)

    fun refreshWeather(weatherId: String, key: String) = mRepository.refreshWeather(weatherId, key)

    fun getBingPic() = mRepository.getBingPic()

    fun isWeatherCached() = mRepository.isWeatherCached()

    fun getWeatherCached() = mRepository.getCachedWeather()!!
}