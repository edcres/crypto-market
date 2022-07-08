package com.example.cryptomarket.data.newsapi

import com.squareup.moshi.Json

data class NewsCall (
    @Json(name = "count")
    val count: Int,
    @Json(name = "next")
    val next: String,
    @Json(name = "previous")
    val previous: String?,
    @Json(name = "results")
    val results: List<skdk>
)
