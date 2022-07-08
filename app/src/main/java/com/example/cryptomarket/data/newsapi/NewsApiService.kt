package com.example.cryptomarket.data.newsapi

import com.example.cryptomarket.data.coinsapi.CoinsApiService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

private const val API_VERSION = "v1"
private const val BASE_URL = "https://cryptopanic.com/api/$API_VERSION/"
// https://cryptopanic.com/api/v1/posts/?auth_token=03cdeeb873984d7cf7a0723c49cb22676fcd56f6&kind=news

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()
private val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .build()

interface NewsApiService {
    @GET("posts/?auth_token={token}=news")
    suspend fun getNewsPosts(
        @Path("token") id: String,
    ): List<NewsCall>
}

object NewsApi {
    val newsApiService : NewsApiService by lazy {
        retrofit.create(NewsApiService::class.java)
    }
}