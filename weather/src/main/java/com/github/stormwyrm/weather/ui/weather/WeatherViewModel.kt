package com.github.stormwyrm.weather.ui.weather

import com.github.stormwyrm.library.mvvm.AbsViewModel
import com.github.stormwyrm.weather.bean.place.Weather

class WeatherViewModel() : AbsViewModel<WeatherRepository>() {
    var weatherId: String? = null

    var weather: Weather? = null

    var bingPicUrl: String? = null

    fun getWeather(weatherId: String, key: String) = mRepository.getWeather(weatherId, key)

    fun refreshWeather(weatherId: String, key: String) = mRepository.refreshWeather(weatherId, key)

    fun getBingPic() = mRepository.getBingPic()

    fun isWeatherCached() = mRepository.isWeatherCached()

    fun getWeatherCached() = mRepository.getCachedWeather()!!
}