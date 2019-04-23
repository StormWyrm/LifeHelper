package com.github.stormwyrm.weather.db

import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.github.stormwyrm.library.util.LibApplication
import com.github.stormwyrm.weather.bean.place.Weather
import com.google.gson.Gson

class WeatherDao {

    fun cacheWeatherInfo(weather: Weather?) {
        if (weather == null) return
        PreferenceManager.getDefaultSharedPreferences(LibApplication.getContext()).edit {
            val weatherInfo = Gson().toJson(weather)
            putString("weather", weatherInfo)
        }
    }

    fun getCachedWeatherInfo(): Weather? {
        val weatherInfo =
                PreferenceManager.getDefaultSharedPreferences(LibApplication.getContext()).getString("weather", null)
        if (weatherInfo != null) {
            return Gson().fromJson(weatherInfo, Weather::class.java)
        }
        return null
    }

    fun cacheBingPic(bingPic: String?) {
        if (bingPic == null) return
        PreferenceManager.getDefaultSharedPreferences(LibApplication.getContext())
                .edit {
                    putString("bing_pic", bingPic)
                }
    }

    fun getCachedBingPic(): String? =
            PreferenceManager.getDefaultSharedPreferences(LibApplication.getContext()).getString("bing_pic", null)

    private fun SharedPreferences.edit(action: SharedPreferences.Editor.() -> Unit) {
        val editor = edit()
        action(editor)
        editor.apply()
    }

}