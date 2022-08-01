package com.example.cryptomarket.data.coinsapi

import com.example.cryptomarket.data.coinsapi.coin.CoinData
import com.example.cryptomarket.data.coinsapi.ticker.HistoricalTicker
import com.example.cryptomarket.data.coinsapi.ticker.Ticker
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

// All endpoints return either a JSON object or array
// All timestamp related fields are in seconds
// API errors are formatted as JSON: {"error": "<error message>"}

private const val API_VERSION = "v1"
private const val BASE_URL = "https://api.coinpaprika.com/$API_VERSION/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()
private val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .build()

interface CoinsApiService {
    @GET("tickers")
    suspend fun getTickers(): List<Ticker>

    @GET("global")
    suspend fun getGlobalData(): GlobalData

    // id = btc-bitcoin
    @GET("coins/{id}")
    suspend fun getCoin(
        @Path("id") id: String
    ): CoinData

    // tickers/btc-bitcoin/historical?start=2022-01-01&interval=1d
    @GET("tickers/{id}/historical")
    suspend fun getHistoricalTickers(
        @Path("id") tickerID: String,
        @Query("start") startTime: String,
        @Query("interval") interval: String
    ): List<HistoricalTicker>
}

object CoinsApi {
    val coinsApiService: CoinsApiService by lazy {
        retrofit.create(CoinsApiService::class.java)
    }
}