package com.example.cryptomarket.data.coinsapi

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

// Update interval: Every 5 minute.
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
//    @GET("$API_V/images/search?mime_types=jpg,png&limit=100")
//    suspend fun getAllCoins(): List<CatPhoto>

    @GET("global")
    suspend fun getGlobalData(): List<GlobalData>
}

object CoinsApi {
    val coinsApiService : CoinsApiService by lazy {
        retrofit.create(CoinsApiService::class.java)
    }
}