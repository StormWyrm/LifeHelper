package com.github.StormWyrm.picture.ui

import com.github.StormWyrm.library.rx.observer.BaseObserver
import com.github.StormWyrm.picture.bean.GankFilterResult

class PicturePresenter : PictureContract.Presenter() {
    override fun getImages(pageCount: Int, pageNum: Int) {
        val observer = mModel?.getImages(pageCount, pageNum)
            ?.subscribeWith(object : BaseObserver<List<GankFilterResult>>() {
                override fun onSuccess(t: List<GankFilterResult>?) {
                    if (t != null)
                        mView?.onLoadImagesSuccess(t)
                    else {
                        if (pageNum == 0) {
                            mView?.noData()
                        } else {
                            mView?.noMoreData()
                        }
                    }
                }

                override fun onFailure(t: Throwable?, msg: String?) {
                    if (pageNum == 0) {
                        mView?.loadDataError()
                    } else {
                        mView?.loadMoreError()
                    }
                }
            })

        mModel.addDispose(
            observer
        )
    }

    override fun initModel(): PictureContract.Model {
        return PictureModel()
    }
}