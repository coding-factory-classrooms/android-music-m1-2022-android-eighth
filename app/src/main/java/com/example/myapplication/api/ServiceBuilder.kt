package com.example.myapplication.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create

object ServiceBuilder {

    private const val Url = "https://music.gryt.tech/"


    val retrofit: Retrofit

    init {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

        retrofit = Retrofit.Builder()
            .baseUrl("https://music.gryt.tech/")
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }

    fun<T> buildService(serviceType: Class<T>) : T{
        return retrofit.create(serviceType)
    }

}
