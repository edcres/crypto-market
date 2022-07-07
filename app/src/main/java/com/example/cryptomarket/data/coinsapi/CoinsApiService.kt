package com.example.cryptomarket.data.coinsapi

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

// All endpoints return either a JSON object or array
// All timestamp related fields are in seconds
// API errors are formatted as JSON: {"error": "<error message>"}

private const val API_VERSION = "v1"
private const val BASE_URL = "https://api.coinpaprika.com/$API_VERSION/"

// https://api.coinpaprika.com/v1/global

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()
private val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .build()

interface CoinsApiService {
    @GET("global")
    suspend fun getGlobalData(): List<GlobalData>

    @GET("coins")
    suspend fun getCoins(): List<CoinFromList>

    @GET("coins")
    suspend fun getCoin(
        @Query("id") id: String
    ): List<CoinData>
}

object CoinsApi {
    val coinsApiService : CoinsApiService by lazy {
        retrofit.create(CoinsApiService::class.java)
    }
}