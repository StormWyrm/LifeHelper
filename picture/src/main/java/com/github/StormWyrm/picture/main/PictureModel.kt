package com.github.StormWyrm.picture.main

import com.github.StormWyrm.library.network.RetrofitUtils
import com.github.StormWyrm.library.rx.convert.ExceptionConvert
import com.github.StormWyrm.library.rx.scheduler.RxScheduler
import com.github.StormWyrm.picture.api.GankioApi
import com.github.StormWyrm.picture.bean.GankFilterResult
import com.github.StormWyrm.picture.convert.GankioReponseConvert
import io.reactivex.Observable

class PictureModel : PictureContract.Model() {
    private val gankioApi: GankioApi by lazy {
        RetrofitUtils.getInstance().createClass(GankioApi::class.java, GankioApi.BASE_URL)
    }

    override fun getImages(pageCount: Int, pageNum: Int): Observable<List<GankFilterResult>> {
        return gankioApi.gankFilter("福利", pageCount, pageNum)
            .map(GankioReponseConvert())
            .onErrorResumeNext(ExceptionConvert())
            .compose(RxScheduler.ioToMain())
    }
}
