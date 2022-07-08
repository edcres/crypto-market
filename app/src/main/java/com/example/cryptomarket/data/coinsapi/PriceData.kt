package com.example.cryptomarket.data.coinsapi

import com.squareup.moshi.Json

data class PriceData(
    @Json(name = "price")
    val price: Double,
    @Json(name = "volume_24h")
    val volume_24h: Double,
    @Json(name = "volume_24h_change_24h")
    val volume_24h_change_24h: Double,
    @Json(name = "market_cap")
    val market_cap: Double,
    @Json(name = "market_cap_change_24h")
    val market_cap_change_24h: Double,
    @Json(name = "percent_change_15m")
    val percent_change_15m: Double,
    @Json(name = "percent_change_30m")
    val percent_change_30m: Double,
    @Json(name = "percent_change_1h")
    val percent_change_1h: Double,
    @Json(name = "percent_change_6h")
    val percent_change_6h: Double,
    @Json(name = "percent_change_12h")
    val percent_change_12h: Double,
    @Json(name = "percent_change_24h")
    val percent_change_24h: Double,
    @Json(name = "percent_change_7d")
    val percent_change_7d: Double,
    @Json(name = "percent_change_30d")
    val percent_change_30d: Double,
    @Json(name = "percent_change_1y")
    val percent_change_1y: Double,
    @Json(name = "ath_price")
    val ath_price: Double,
    @Json(name = "ath_date")
    val ath_date: String,
    @Json(name = "percent_from_price_ath")
    val percent_from_price_ath: Double
)
