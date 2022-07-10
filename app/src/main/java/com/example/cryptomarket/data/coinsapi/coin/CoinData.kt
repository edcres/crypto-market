package com.example.cryptomarket.data.coinsapi.coin

import com.example.cryptomarket.data.coinsapi.TeamMember
import com.squareup.moshi.Json

// https://api.coinpaprika.com/#operation/getCoinById
data class CoinData (
    @Json(name = "id")
    val id: String,
    @Json(name = "name")
    val name: String,
    @Json(name = "symbol")
    val symbol: String,              // todo
    @Json(name = "rank")
    val rank: Int,              // todo
    @Json(name = "is_new")
    val isNew: Boolean,
    @Json(name = "is_active")
    val isActive: Boolean,
    @Json(name = "type")
    val type: String,              // todo
    @Json(name = "team")
    val team: List<TeamMember>,              // todo
    @Json(name = "description")
    val description: String,              // todo
    @Json(name = "open_source")
    val openSource: Boolean,              // todo
    @Json(name = "started_at")
    val startedAt: String,              // todo
    @Json(name = "proof_type")
    val proofType: String,              // todo
    @Json(name = "org_structure")
    val orgStructure: String,              // todo
    @Json(name = "hash_algorithm")
    val hashAlgorithm: String              // todo
)