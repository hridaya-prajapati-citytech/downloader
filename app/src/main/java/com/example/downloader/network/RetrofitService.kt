package com.example.downloader.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

interface RetrofitImpl {
    val githubRetrofitService: GithubApiService
}

class RetrofitService : RetrofitImpl {

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val client = OkHttpClient.Builder().addInterceptor(loggingInterceptor).build()

    private val retrofit: Retrofit =
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .client(client)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

    override val githubRetrofitService: GithubApiService by lazy {
        retrofit.create(GithubApiService::class.java)
    }

    companion object {
        const val TAG = "RetrofitService"
        const val ORGANIZATION_NAME = "PixelOS-AOSP"
        const val BASE_URL = "https://raw.githubusercontent.com/$ORGANIZATION_NAME/"
    }
}