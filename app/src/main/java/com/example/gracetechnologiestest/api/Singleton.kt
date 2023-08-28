package com.example.gracetechnologiestest.api

import android.content.Context
import com.example.gracetechnologiestest.Constants

object Singleton {
    lateinit var apiInterface: ApiInterface

    fun getApiCall(context: Context): ApiInterface {
        if (!(this::apiInterface.isInitialized) || apiInterface==null) {
            apiInterface =
                ApiClient.getRetrofit(context, Constants.BASE_URL, Constants.API_KEY)
                    .create(ApiInterface::class.java)
        }
        return apiInterface
    }
}