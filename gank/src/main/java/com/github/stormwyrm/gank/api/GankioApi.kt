package com.github.stormwyrm.gank.api

import com.github.stormwyrm.gank.bean.GankDailyResult
import com.github.stormwyrm.picture.bean.BaseRepsonse
import com.github.stormwyrm.picture.bean.GankFilterResult
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface GankioApi {

    @GET("today")
    fun gankDaily(): Observable<GankDailyResult>

    @GET("data/{filterType}/{count}/{page}")
    fun gankFilter(
            @Path("filterType") filterType: String,
            @Path("count") count: Int,
            @Path("page") page: Int
    ): Observable<BaseRepsonse<List<GankFilterResult>>>

    @GET("search/query/{queryText}/category/all/count/{count}/page/{page}")
    fun search(
            @Path("queryText") queryText: String,
            @Path("count") count: Int,
            @Path("page") page: Int
    ): Observable<BaseRepsonse<List<GankFilterResult>>>

    companion object {
        val BASE_URL = "http://gank.io/api/"
    }
}
