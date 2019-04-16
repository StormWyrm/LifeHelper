package com.github.StormWyrm.weather.ui.weather

import com.github.StormWyrm.library.livedata.LiveDataBus
import com.github.StormWyrm.library.rx.convert.ExceptionConvert
import com.github.StormWyrm.library.rx.observer.BaseObserver
import com.github.StormWyrm.library.rx.scheduler.RxScheduler
import com.github.StormWyrm.library.state.IStateView
import com.github.StormWyrm.weather.base.BaseRepository
import com.github.StormWyrm.weather.bean.place.HeWeather
import com.github.StormWyrm.weather.config.Constants
import com.github.StormWyrm.weather.db.WeatherDao
import com.github.StormWyrm.weather.util.CoolWeatherExecutors
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WeatherRepository : BaseRepository() {
    private val weatherDao: WeatherDao by lazy {
        WeatherDao()
    }

    fun getWeather(weatherId: String, key: String) {
        LiveDataBus.getDefault().postEvent(Constants.EVENT_KEY_WEATHER_STATE, IStateView.STATE_LOADING)
        CoolWeatherExecutors.diskIO.execute {
            val weather = weatherDao.getCachedWeatherInfo()
            if (weather == null) {
                requestWeather(weatherId, key)
            } else {
                LiveDataBus.getDefault().postEvent(Constants.EVENT_KEY_WEATHER_STATE, IStateView.STATE_SUCCESS)
                LiveDataBus.getDefault().postEvent(Constants.EVENT_KEY_WEATHER_DATA, weather)
            }
        }
    }

    fun refreshWeather(weatherId: String, key: String) {
        requestWeather(weatherId, key)
    }

    fun getBingPic(){
        CoolWeatherExecutors.diskIO.execute {
            val bingPic = weatherDao.getCachedBingPic()
            if (bingPic.isNullOrEmpty()) {
                requestBingPic()
            } else {
                LiveDataBus.getDefault().postEvent(Constants.EVENT_KEY_WEATHER_BIND_IMG, bingPic)
            }
        }
    }

    fun isWeatherCached() = weatherDao.getCachedWeatherInfo() != null

    fun getCachedWeather() = weatherDao.getCachedWeatherInfo()

    private fun requestWeather(weatherId: String, key: String) {
        addDisposable(
            network.getWeather(weatherId, key)
                .onErrorResumeNext(ExceptionConvert())
                .compose(RxScheduler.ioToMain())
                .subscribeWith(object : BaseObserver<HeWeather>() {
                    override fun onSuccess(t: HeWeather?) {
                        t?.let {
                            val weather = t.weather?.get(0)
                            weatherDao.cacheWeatherInfo(weather)
                            LiveDataBus.getDefault().postEvent(Constants.EVENT_KEY_WEATHER_DATA, weather)
                            LiveDataBus.getDefault()
                                .postEvent(Constants.EVENT_KEY_WEATHER_STATE, IStateView.STATE_SUCCESS)
                        } ?: LiveDataBus.getDefault().postEvent(
                            Constants.EVENT_KEY_WEATHER_STATE,
                            IStateView.STATE_EMPTY
                        )
                    }

                    override fun onFailure(t: Throwable?, msg: String?) {
                        LiveDataBus.getDefault().postEvent(Constants.EVENT_KEY_WEATHER_STATE, IStateView.STATE_ERROR)
                    }
                })
        )
    }

    private fun requestBingPic() {
        network.getBingPic()
            .enqueue(object : Callback<ResponseBody> {
                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                }

                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    if(response.isSuccessful){
                        val bingPic = response.body()?.string()
                        LiveDataBus.getDefault().postEvent(Constants.EVENT_KEY_WEATHER_BIND_IMG, bingPic)
                    }
                }
            })
    }
}
