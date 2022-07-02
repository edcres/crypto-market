package com.example.cryptomarket.data.coinsapi

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

private const val BASE_URL = "https://coinpaprika.com"  // todo: maybe change this

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
}

object CoinsApi {
    val coinsApiService : CoinsApiService by lazy {
        retrofit.create(CoinsApiService::class.java)
    }
}