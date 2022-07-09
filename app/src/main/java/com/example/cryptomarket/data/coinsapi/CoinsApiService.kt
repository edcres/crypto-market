package com.example.cryptomarket.data.coinsapi

import com.example.cryptomarket.data.coinsapi.coin.CoinData
import com.example.cryptomarket.data.coinsapi.coin.CoinFromList
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

    // todo: maybe delete this
    @GET("coins")
    suspend fun getCoins(): List<CoinFromList>

    @GET("global")
    suspend fun getGlobalData(): List<GlobalData>

    // todo: maybe call this when clicking the recycler item and the bottom sheet pops up
    @GET("coins")
    suspend fun getCoin(
        @Query("id") id: String
    ): List<CoinData>

    // https://api.coinpaprika.com/v1/tickers/btc-bitcoin/historical?start=2022-01-01&interval=1d
    @GET("tickers/btc-bitcoin/historical?start={startTime}&interval={interval}")
    suspend fun getHistoricalTickers(
        @Path("startTime") startTime: String,
        @Path("interval") interval: String
    ): List<HistoricalTicker>

    // todo: news
}

object CoinsApi {
    val coinsApiService : CoinsApiService by lazy {
        retrofit.create(CoinsApiService::class.java)
    }
}