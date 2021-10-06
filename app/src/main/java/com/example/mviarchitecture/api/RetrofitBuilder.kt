package com.example.mviarchitecture.api

import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object RetrofitBuilder {

    //    private const val BASE_URL = "https://test.mockapi.io"
    private const val BASE_URL = "https://jsonplaceholder.typicode.com/"
    private fun getRetrofit() = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create())
//        .client(okHttpClient().build())
        .build()

    val apiService: ApiService =
        getRetrofit().create(ApiService::class.java)


/*
    private fun okHttpClient():OkHttpClient.Builder{
        val oktHttpClientBuilder = OkHttpClient.Builder()
        var logginInterception=HttpLoggingInterceptor()
        logginInterception.setLevel(HttpLoggingInterceptor.Level.BODY)
        oktHttpClientBuilder.addInterceptor(logginInterception)
        return oktHttpClientBuilder
    }
*/

}