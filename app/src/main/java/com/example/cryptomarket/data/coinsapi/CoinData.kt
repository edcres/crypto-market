package com.example.cryptomarket.data.coinsapi

import com.squareup.moshi.Json

data class CoinData (
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
    val type: String,
    @Json(name = "team")
    val team: List<TeamMember>,
    @Json(name = "description")
    val description: String,
    @Json(name = "open_source")
    val openSource: Boolean,
    @Json(name = "started_at")
    val startedAt: String,
    @Json(name = "proof_type")
    val proofType: String,
    @Json(name = "org_structure")
    val orgStructure: String,
    @Json(name = "hash_algorithm")
    val hashAlgorithm: String
)