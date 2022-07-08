package com.example.cryptomarket.data.newsapi

import com.squareup.moshi.Json

// Used in NewsPost data class.
data class NewsCurrency(
    @Json(name = "code")
    val code: String,
    @Json(name = "title")
    val title: String,
    @Json(name = "slug")
    val slug: String,
    @Json(name = "url")
    val url: String,
)
