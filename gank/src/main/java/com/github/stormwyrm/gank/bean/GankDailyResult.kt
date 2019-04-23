package com.github.stormwyrm.gank.bean

import com.google.gson.annotations.SerializedName

data class GankDailyResult(
        val category: List<String>,
        val error: Boolean,
        val results: Results
)

data class Results(
        @SerializedName("Android") val android: List<Gank>,
        @SerializedName("福利") val app: List<Gank>,
        @SerializedName("iOS") val ios: List<Gank>,
        @SerializedName("休息视频")val breakVideo: List<Gank>,
        @SerializedName("前端") val web: List<Gank>,
        @SerializedName("拓展资源") val extraResources: List<Gank>,
        @SerializedName("瞎推荐") val recommended: List<Gank>,
        @SerializedName("福利") val welfare: List<Gank>
)

data class Gank(
        val _id: String,
        val createdAt: String,
        val desc: String,
        val images: List<String>,
        val publishedAt: String,
        val source: String,
        val type: String,
        val url: String,
        val used: Boolean,
        val who: String
)