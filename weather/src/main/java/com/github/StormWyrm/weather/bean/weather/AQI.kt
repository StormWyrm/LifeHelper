package com.github.StormWyrm.weather.bean.place

class AQI {
    lateinit var city: AQICity

    inner class AQICity {
        var aqi = ""
        var pm25  = ""
    }
}