package com.example.cryptomarket.data.newsapi

import com.squareup.moshi.Json

// Used in NewsCall data class.
data class NewsPost(
    @Json(name = "id")
    val id: Int,
    @Json(name = "kind")
    val kind: String,
    @Json(name = "domain")
    val domain: String,
    @Json(name = "title")
    val title: String,
    @Json(name = "published_at")
    val publishedAt: String,
    @Json(name = "slug")
    val slug: String,
    @Json(name = "url")
    val url: String,
    @Json(name = "currencies")
    val currencies: List<NewsCurrency>
)
