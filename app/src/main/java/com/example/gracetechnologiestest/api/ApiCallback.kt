package com.example.gracetechnologiestest.api

interface ApiCallback {
    fun onResponce(resp: String, isSuccess: Boolean)
}