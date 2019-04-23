package com.github.stormwyrm.picture.bean

/**
 * _id : 5c6a4ae99d212226776d3256
 * createdAt : 2019-02-18T06:04:25.571Z
 * desc : 2019-02-18
 * publishedAt : 2019-02-18T06:05:41.975Z
 * source : web
 * type : 福利
 * url : https://ws1.sinaimg.cn/large/0065oQSqly1g0ajj4h6ndj30sg11xdmj.jpg
 * used : true
 * who : lijinshanmx
 */
data class GankFilterResult(
    var _id: String? = null,
    var createdAt: String? = null,
    var desc: String? = null,
    var publishedAt: String? = null,
    var source: String? = null,
    var type: String? = null,
    var url: String? = null,
    var isUsed: Boolean = false,
    var who: String? = null
)