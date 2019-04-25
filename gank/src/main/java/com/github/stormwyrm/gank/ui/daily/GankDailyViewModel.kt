package com.github.stormwyrm.gank.ui.daily

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.github.stormwyrm.library.mvvm.AbsViewModel

class GankDailyViewModel : AbsViewModel<GankDailyRepository>(){
    private val _refreshing = MutableLiveData<Boolean>()
    val refreshing : LiveData<Boolean>
        get() = _refreshing

}