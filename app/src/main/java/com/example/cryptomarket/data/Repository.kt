package com.example.cryptomarket.data

import com.example.cryptomarket.data.coinsapi.CoinsApi
import retrofit2.Response

class Repository {

    suspend fun getHistoricalTickers(interval: String, startTime: String): Response<String> {
        return CoinsApi.coinsApiService.getHistoricalTickers(interval, startTime)
    }
}