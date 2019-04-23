package com.github.stormwyrm.weather.ui.area

import com.github.stormwyrm.library.mvvm.AbsViewModel
import com.github.stormwyrm.weather.bean.place.City
import com.github.stormwyrm.weather.bean.place.County
import com.github.stormwyrm.weather.bean.place.Province

class ChooseAreaViewModel() : AbsViewModel<ChooseAreaRepository>() {
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