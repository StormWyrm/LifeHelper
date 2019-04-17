package com.github.StormWyrm.weather.ui.weather

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.core.view.GravityCompat
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.github.StormWyrm.library.livedata.LiveDataBus
import com.github.StormWyrm.library.mvvm.BaseMvvmLoadActivity
import com.github.StormWyrm.weather.R
import com.github.StormWyrm.weather.bean.place.Weather
import com.github.StormWyrm.weather.config.Constants
import com.github.StormWyrm.weather.ui.MainActivity
import kotlinx.android.synthetic.main.activity_weather.*
import kotlinx.android.synthetic.main.layout_weather_api.*
import kotlinx.android.synthetic.main.layout_weather_forecast.*
import kotlinx.android.synthetic.main.layout_weather_now.*
import kotlinx.android.synthetic.main.layout_weather_suggestion.*
import kotlinx.android.synthetic.main.layout_weather_title.*

class WeatherActivity : BaseMvvmLoadActivity<WeatherViewModel>() {

    override fun initStatusBar() {
        super.initStatusBar()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val decorView = window.decorView
            decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            window.statusBarColor = Color.TRANSPARENT
        }
    }

    override fun getChildLayoutId(): Int {
        return R.layout.activity_weather
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        val weatherCached = mViewModel.isWeatherCached()

        if (weatherCached) {
            mViewModel.weatherId = mViewModel.getWeatherCached().basic.weatherId
            showWeatherInfo(mViewModel.getWeatherCached())
        } else {
            mViewModel.weatherId = intent.getStringExtra("weather_id")
            weatherLayout.visibility = View.INVISIBLE
            mViewModel.getWeather(mViewModel.weatherId!!, MainActivity.KEY)
        }
        mViewModel.getBingPic()

        swipeRefresh.setColorSchemeResources(R.color.colorPrimary)
        swipeRefresh.setOnRefreshListener {
            refreshWeather(mViewModel.weatherId!!)
            mViewModel.getBingPic()
        }
        btnNav.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }
    }

    override fun dataObserver() {
        super.dataObserver()
        LiveDataBus.getDefault().subscribe<Int>(Constants.EVENT_KEY_WEATHER_STATE)
            .observe(this, observer)

        LiveDataBus.getDefault()
            .subscribe<Weather>(Constants.EVENT_KEY_WEATHER_DATA)
            .observe(this, Observer {
                showWeatherInfo(it)
                if (swipeRefresh.isRefreshing) {
                    swipeRefresh.isRefreshing = false
                }
            })

        LiveDataBus.getDefault()
            .subscribe<String>(Constants.EVENT_KEY_WEATHER_BIND_IMG)
            .observe(this, Observer<String> {
                if (!it.isNullOrEmpty()) {
                    Glide.with(this).load(it).into(bindPicImg)
                    mViewModel.bingPicUrl = it
                }
            })

    }

    fun refreshWeather(weatherId : String){
        mViewModel.refreshWeather(weatherId, MainActivity.KEY)
    }

    private fun showWeatherInfo(weather: Weather) {
        mViewModel.weatherId = weather.basic.weatherId
        mViewModel.weather = weather

        val cityName = weather.basic.cityName
        val updateTime = weather.basic.update.updateTime.split(" ")[1]
        val degree = weather.now.temperature + "℃"
        val weatherInfo = weather.now.more.info
        tvCity.text = cityName
        tvUpdateTime.text = updateTime
        tvDegreeText.text = degree
        tvWeatherInfoText.text = weatherInfo
        llForecast.removeAllViews()
        for (forecast in weather.forecastList) {
            val view = LayoutInflater.from(this).inflate(R.layout.forecast_item, llForecast, false)
            val dateText = view.findViewById(R.id.tvDateText) as TextView
            val infoText = view.findViewById(R.id.tvInfoText) as TextView
            val maxText = view.findViewById(R.id.tvMaxText) as TextView
            val minText = view.findViewById(R.id.tvMinText) as TextView
            dateText.text = forecast.date
            infoText.text = forecast.more.info
            maxText.text = forecast.temperature.max
            minText.text = forecast.temperature.min
            llForecast.addView(view)
        }
        tvAqi.text = weather.aqi.city.aqi
        tvPm25.text = weather.aqi.city.pm25
        val comfort = "舒适度：" + weather.suggestion.comfort.info
        val carWash = "洗车指数：" + weather.suggestion.carWash.info
        val sport = "运动建议：" + weather.suggestion.sport.info
        tvComfortText.text = comfort
        tvCarWashText.text = carWash
        tvSportText.text = sport
        weatherLayout.visibility = View.VISIBLE
    }
}