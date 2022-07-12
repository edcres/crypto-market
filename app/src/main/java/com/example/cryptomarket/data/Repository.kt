package com.example.cryptomarket.data

import android.util.Log
import com.example.cryptomarket.data.coinsapi.CoinsApi
import com.example.cryptomarket.data.coinsapi.GlobalData
import com.example.cryptomarket.data.coinsapi.coin.CoinData
import com.example.cryptomarket.data.coinsapi.ticker.HistoricalTicker
import com.example.cryptomarket.data.coinsapi.ticker.Ticker
import com.example.cryptomarket.data.newsapi.NewsApi
import com.example.cryptomarket.data.newsapi.NewsCall
import com.example.cryptomarket.data.newsapi.NewsPost

const val TAG = "Repo__TAG"

class Repository {
//    suspend fun getTickers() = CoinsApi.coinsApiService.getTickers()
    suspend fun getTickers(): List<Ticker> {
        Log.d(TAG, "getTickers: called")
        val asdfg = CoinsApi.coinsApiService.getTickers()
        return asdfg
    }

    suspend fun getGlobalData(): GlobalData {
        Log.d(TAG, "getGlobalData: called")
        return CoinsApi.coinsApiService.getGlobalData()
    }

    suspend fun getNewsPosts(): NewsCall {
        Log.d(TAG, "getNewsPosts: called")
        return NewsApi.newsApiService.getNewsPosts()
    }

    suspend fun getCoinData(coinID: String): List<CoinData> {
        Log.d(TAG, "getCoinData: called")
        return CoinsApi.coinsApiService.getCoin(coinID)
    }

    suspend fun getHistoricalTickers(startTime: String, interval: String): List<HistoricalTicker> {
        Log.d(TAG, "getHistoricalTickers: called")
        Log.d(TAG, "getHistoricalTickers: \nStart=$startTime\t-\tinterval=$interval")
        val response = CoinsApi.coinsApiService.getHistoricalTickers(startTime, interval)
        Log.d(TAG, "getHistoricalTickers3: $response")
        return response
    }
}