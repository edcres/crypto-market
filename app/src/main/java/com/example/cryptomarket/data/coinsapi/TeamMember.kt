package com.example.cryptomarket.data.coinsapi

import com.squareup.moshi.Json

// Used in CoinData data class.
data class TeamMember (
    @Json(name = "id")
    val id: String,
    @Json(name = "name")
    val name: String,
    @Json(name = "position")
    val position: String?,
)