package com.example.cryptomarket.data

import com.example.cryptomarket.data.coinsapi.CoinsApi
import com.example.cryptomarket.data.coinsapi.ticker.HistoricalTicker

class Repository {

    suspend fun getHistoricalTickers(startTime: String, interval: String): List<HistoricalTicker> {
        return CoinsApi.coinsApiService.getHistoricalTickers(startTime, interval)
    }
}