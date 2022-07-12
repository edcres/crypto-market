package com.example.cryptomarket.data.coinsapi.ticker

import com.squareup.moshi.Json

// https://api.coinpaprika.com/#operation/getTickers
// https://api.coinpaprika.com/#operation/getTickersById
data class Ticker(
    @Json(name = "id")
    val id: String,
    @Json(name = "name")
    val name: String,
    @Json(name = "symbol")
    val symbol: String,
    @Json(name = "rank")
    val rank: Double,
    @Json(name = "circulating_supply")
    val circulatingSupply: Double,
    @Json(name = "total_supply")
    val totalSupply: Double,
    @Json(name = "max_supply")
    val maxSupply: Double,
    @Json(name = "beta_value")
    val betaValue: Double,
    @Json(name = "first_data_at")
    val firstDataAt: String,
    @Json(name = "last_updated")
    val last_updated: String,
    @Json(name = "quotes")
    val quotes: USDPriceData
)