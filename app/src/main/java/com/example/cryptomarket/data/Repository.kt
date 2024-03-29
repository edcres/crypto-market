package com.example.cryptomarket.data

import android.util.Log
import com.example.cryptomarket.data.coinsapi.CoinsApi
import com.example.cryptomarket.data.coinsapi.GlobalData
import com.example.cryptomarket.data.coinsapi.coin.CoinData
import com.example.cryptomarket.data.coinsapi.ticker.Ticker
import com.example.cryptomarket.data.newsapi.NewsApi
import com.example.cryptomarket.data.newsapi.NewsCall

const val TAG = "Repo__TAG"

class Repository {
    suspend fun getTickers(): List<Ticker> = CoinsApi.coinsApiService.getTickers()

    suspend fun getGlobalData(): GlobalData = CoinsApi.coinsApiService.getGlobalData()

    suspend fun getNewsPosts(): NewsCall = NewsApi.newsApiService.getNewsPosts()

    suspend fun getCoinData(coinID: String): CoinData = CoinsApi.coinsApiService.getCoin(coinID)

    suspend fun getHistoricalTickers(tickerId: String, startTime: String, interval: String) =
        CoinsApi.coinsApiService.getHistoricalTickers(tickerId, startTime, interval)
}