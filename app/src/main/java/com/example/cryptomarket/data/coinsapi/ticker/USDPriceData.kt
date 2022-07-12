package com.example.cryptomarket.data.coinsapi.ticker

import com.squareup.moshi.Json

// Used in the Ticker data class.
data class USDPriceData(
    @Json(name = "USD")
    val usd: PriceData
)
