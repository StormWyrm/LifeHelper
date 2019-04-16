package com.github.StormWyrm.weather.db

import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.github.StormWyrm.weather.CoolWeatherApp
import com.github.StormWyrm.weather.bean.place.Weather
import com.google.gson.Gson

class WeatherDao {

    fun cacheWeatherInfo(weather: Weather?) {
        if (weather == null) return
        PreferenceManager.getDefaultSharedPreferences(CoolWeatherApp.context).edit {
            val weatherInfo = Gson().toJson(weather)
            putString("weather", weatherInfo)
        }
    }

    fun getCachedWeatherInfo(): Weather? {
        val weatherInfo =
            PreferenceManager.getDefaultSharedPreferences(CoolWeatherApp.context).getString("weather", null)
        if (weatherInfo != null) {
            return Gson().fromJson(weatherInfo, Weather::class.java)
        }
        return null
    }

    fun cacheBingPic(bingPic: String?) {
        if (bingPic == null) return
        PreferenceManager.getDefaultSharedPreferences(CoolWeatherApp.context)
            .edit {
                putString("bing_pic", bingPic)
            }
    }

    fun getCachedBingPic(): String? =
        PreferenceManager.getDefaultSharedPreferences(CoolWeatherApp.context).getString("bing_pic", null)

    private fun SharedPreferences.edit(action: SharedPreferences.Editor.() -> Unit) {
        val editor = edit()
        action(editor)
        editor.apply()
    }

}