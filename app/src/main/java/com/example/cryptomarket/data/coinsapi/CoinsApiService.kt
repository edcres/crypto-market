package com.example.cryptomarket.data.coinsapi

private const val BASE_URL = "https://coinpaprika.com"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()
private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
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