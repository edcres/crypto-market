package com.example.cryptomarket.data.newsapi

import com.example.cryptomarket.BuildConfig
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

private const val API_TOKEN = BuildConfig.NEWS_API_AUTH_TOKEN
private const val API_VERSION = "v1"
private const val BASE_URL = "https://cryptopanic.com/api/$API_VERSION/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()
private val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .build()

interface NewsApiService {
    @GET("posts/?auth_token=$API_TOKEN&kind=news")
    suspend fun getNewsPosts(): NewsCall
}

object NewsApi {
    val newsApiService: NewsApiService by lazy {
        retrofit.create(NewsApiService::class.java)
    }
}