package com.github.StormWyrm.weather.ui.area

import android.app.Application
import com.github.StormWyrm.library.mvvm.AbsViewModel
import com.github.StormWyrm.weather.bean.place.City
import com.github.StormWyrm.weather.bean.place.County
import com.github.StormWyrm.weather.bean.place.Province

class ChooseAreaViewModel(application: Application) : AbsViewModel<ChooseAreaRepository>(application) {
    var currentLevel: Int = 0

    var selectedProvince: Province? = null

    var selectedCity: City? = null

    var provinceList: List<Province>? = null

    var cityList: List<City>? = null

    var countyList: List<County>? = null

    var dataList = ArrayList<String>()

    fun getProvinceList() = mRepository.getProvinceList()

    fun getCityList(provinceId: Int) = mRepository.getCityList(provinceId)

    fun getCountyList(provinceId: Int, cityId: Int) = mRepository.getCountyList(provinceId, cityId)
}