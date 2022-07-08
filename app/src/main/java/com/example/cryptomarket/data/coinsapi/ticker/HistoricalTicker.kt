package com.example.cryptomarket.data.coinsapi.ticker

import com.squareup.moshi.Json

// https://api.coinpaprika.com/#operation/getTickersHistoricalById
data class HistoricalTicker(
    @Json(name = "timestamp")
    val timestamp: String,
    @Json(name = "price")
    val price: Double,
    @Json(name = "volume_24h")
    val volume24h: Int,
    @Json(name = "market_cap")
    val marketCap: Int
)