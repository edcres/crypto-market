package com.example.cryptomarket.data.coinsapi.ticker

import com.squareup.moshi.Json

// Used in the Ticker data class.
data class PriceData(
    @Json(name = "price")
    val price: Double,
    @Json(name = "volume_24h")
    val volume24h: Double,
    @Json(name = "volume_24h_change_24h")
    val volume24hChange24h: Double,
    @Json(name = "market_cap")
    val marketCap: Double,
    @Json(name = "market_cap_change_24h")
    val marketCapChange24h: Double,
    @Json(name = "percent_change_15m")
    val percentChange15m: Double,
    @Json(name = "percent_change_30m")
    val percentChange30m: Double,
    @Json(name = "percent_change_1h")
    val percentChange1h: Double,
    @Json(name = "percent_change_6h")
    val percentChange6h: Double,
    @Json(name = "percent_change_12h")
    val percentChange12h: Double,
    @Json(name = "percent_change_24h")
    val percentChange24h: Double,
    @Json(name = "percent_change_7d")
    val percentChange7d: Double,
    @Json(name = "percent_change_30d")
    val percentChange30d: Double,
    @Json(name = "percent_change_1y")
    val percentChange1y: Double,
    @Json(name = "ath_price")
    val athPrice: Double?,
    @Json(name = "ath_date")
    val athDate: String?,
    @Json(name = "percent_from_price_ath")
    val percentFromPriceAth: Double?
)