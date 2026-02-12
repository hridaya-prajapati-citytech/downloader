package com.example.downloader.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface RetrofitImpl {
    val githubRetrofitService: GithubApiService
}

class RetrofitService : RetrofitImpl {
    private val retrofit: Retrofit =
        Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl(BASE_URL)
            .build()

    override val githubRetrofitService: GithubApiService by lazy {
        retrofit.create(GithubApiService::class.java)
    }

    companion object {
        const val TAG = "RetrofitService"
        const val ORGANIZATION_NAME = "PixelOS-AOSP"
        const val BASE_URL = "https://raw.githubusercontent.com/$ORGANIZATION_NAME"
    }
}