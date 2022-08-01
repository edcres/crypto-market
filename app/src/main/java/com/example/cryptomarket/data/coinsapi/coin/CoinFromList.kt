package com.example.cryptomarket.data.coinsapi.coin

import com.squareup.moshi.Json

// https://api.coinpaprika.com/#tag/Coins
data class CoinFromList(
    @Json(name = "id")
    val id: String,
    @Json(name = "name")
    val name: String,
    @Json(name = "symbol")
    val symbol: String,
    @Json(name = "rank")
    val rank: Int,
    @Json(name = "is_new")
    val isNew: Boolean,
    @Json(name = "is_active")
    val isActive: Boolean,
    @Json(name = "type")
    val type: String
)