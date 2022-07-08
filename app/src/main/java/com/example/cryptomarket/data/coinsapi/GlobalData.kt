package com.example.cryptomarket.data.coinsapi

import com.squareup.moshi.Json

// https://api.coinpaprika.com/#tag/Global
data class GlobalData (
    @Json(name = "market_cap_usd")
    val marketCapUsd: Double,
    @Json(name = "volume_24h_usd")
    val volume24hUsd: Double,
    @Json(name = "bitcoin_dominance_percentage")
    val bitcoinDominancePercentage: Double,
    @Json(name = "cryptocurrencies_number")
    val cryptocurrenciesNumber: Int,
    @Json(name = "market_cap_ath_value")
    val marketCapAthValue: Double,
    @Json(name = "market_cap_ath_date")
    val marketCapAthDate: String,
    @Json(name = "volume_24h_ath_value")
    val volume24hAthValue: Double,
    @Json(name = "volume_24h_ath_date")
    val volume24hAthDate: String,
    @Json(name = "volume_24h_percent_from_ath")
    val volume24hPercentFromAth: Double,
    @Json(name = "volume_24h_percent_to_ath")
    val volume24hPercentToAth: Double,
    @Json(name = "market_cap_change_24h")
    val marketCapChange24h: Double,
    @Json(name = "volume_24h_change_24h")
    val volume24hChange24h: Double,
    @Json(name = "last_updated")
    val lastUpdated: Int
)