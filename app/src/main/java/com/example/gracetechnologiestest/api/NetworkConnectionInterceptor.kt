package com.example.gracetechnologiestest.api

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import com.example.gracetechnologiestest.simpleclasses.Helper
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class NetworkConnectionInterceptor(val mContext: Context,val apiKey: String): Interceptor {


    override fun intercept(chain: Interceptor.Chain): Response? {
        if (!isConnected()) {
//            Functions.logDMsg("no internet Found");
            throw NoConnectivityException()
            // Throwing our custom exception 'NoConnectivityException'
        }
        val builder = chain.request().newBuilder()
        builder.header("Content-Type", "application/json")
        builder.header("Api-Key", apiKey)
        Helper.printLogs( "ApiKey: $apiKey")
        return chain.proceed(builder.build())
    }

    @SuppressLint("MissingPermission")
    fun isConnected(): Boolean {
        val connectivityManager =
            mContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val netInfo = connectivityManager.activeNetworkInfo
        return netInfo != null && netInfo.isConnected
    }

    class NoConnectivityException : IOException() {
        // You can send any message whatever you want from here.
        override val message: String
            get() {
                Helper.printLogs( "No Internet Connection")
                return "No Internet Connection"
                // You can send any message whatever you want from here.
            }
    }
}