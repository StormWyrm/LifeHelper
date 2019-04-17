package com.github.StormWyrm.picture.ui

import com.github.StormWyrm.library.mvp.IListView
import com.github.StormWyrm.library.mvp.IModel
import com.github.StormWyrm.library.mvp.IPresenter
import com.github.StormWyrm.picture.bean.GankFilterResult
import io.reactivex.Observable

interface PictureContract {
    abstract class Model : IModel() {
        abstract fun getImages(pageCount: Int, pageNum: Int) : Observable<List<GankFilterResult>>
    }

    abstract class Presenter : IPresenter<View, Model>() {
        abstract fun getImages(pageCount: Int, pageNum: Int)
    }

    interface View : IListView<Presenter> {
        fun onLoadImagesSuccess(results: List<GankFilterResult>)
    }
}
