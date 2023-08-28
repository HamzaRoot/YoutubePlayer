package com.example.gracetechnologiestest.api

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiInterface {

    @Headers("Content-Type: application/json")
    @GET("api/exercises/testing/unique")
    fun getTestingUnique(): Call<String>


    @Headers("Content-Type: application/json")
    @POST("registerUser")
    fun registerUser(@Body body: String): Call<String>



}