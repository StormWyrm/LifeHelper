package com.github.stormwyrm.weather.bean.place

import com.google.gson.annotations.SerializedName

class HeWeather {

    @SerializedName("HeWeather")
    var weather: List<Weather>? = null

}