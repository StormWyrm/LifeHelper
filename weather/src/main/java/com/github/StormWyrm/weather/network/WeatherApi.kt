package com.github.StormWyrm.weather.network

import com.github.StormWyrm.weather.bean.place.City
import com.github.StormWyrm.weather.bean.place.County
import com.github.StormWyrm.weather.bean.place.HeWeather
import com.github.StormWyrm.weather.bean.place.Province
import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface WeatherApi {
    @GET("api/china")
    fun getProvinces(): Observable<List<Province>>

    @GET("api/china/{provinceId}")
    fun getCities(@Path("provinceId") provinceId: Int): Observable<List<City>>

    @GET("api/china/{provinceId}/{cityId}")
    fun getCounties(@Path("provinceId") provinceId: Int, @Path("cityId") cityId: Int): Observable<List<County>>

    @GET("api/weather")
    fun getWeather(@Query("cityid") weatherId: String, @Query("key") key: String): Observable<HeWeather>

    @GET("api/bing_pic")
    fun getBingPic(): Call<ResponseBody>

    companion object {
        const val BASE_URL = "http://guolin.tech/"
    }
}