package com.example.gracetechnologiestest.api

import android.content.Context
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

object ApiClient {

    lateinit var retrofit: Retrofit
    fun getRetrofit(mContext: Context, baseURL: String, apiKey: String): Retrofit {
        val httpClient =
            OkHttpClient.Builder().addInterceptor(NetworkConnectionInterceptor(mContext,apiKey))
        httpClient.connectTimeout(1, TimeUnit.MINUTES)
            .readTimeout(1, TimeUnit.MINUTES)
            .writeTimeout(1, TimeUnit.MINUTES)
            .build()
        if (!(this::retrofit.isInitialized) || retrofit==null ) {
            val gson = GsonBuilder()
                .setLenient()
                .create()
            retrofit = Retrofit.Builder().baseUrl(baseURL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(httpClient.build())
                .build()
        }
        return retrofit
    }


}