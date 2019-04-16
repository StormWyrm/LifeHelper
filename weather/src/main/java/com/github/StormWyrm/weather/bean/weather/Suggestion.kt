package com.github.StormWyrm.weather.bean.place

import com.google.gson.annotations.SerializedName

class Suggestion {
    @SerializedName("comf")
    lateinit var comfort: Comfort
    @SerializedName("cw")
    lateinit var carWash: CarWash
    lateinit var sport: Sport

    inner class Comfort {
        @SerializedName("txt")
        var info = ""
    }

    inner class CarWash {
        @SerializedName("txt")
        var info = ""
    }

    inner class Sport {
        @SerializedName("txt")
        var info = ""
    }
}