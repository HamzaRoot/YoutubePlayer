package com.example.gracetechnologiestest.api

import com.example.gracetechnologiestest.simpleclasses.Helper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object RetrofitRequest {

    fun JsonPostRequest(
        params: String,
        callRes: Call<String>,
        callback: ApiCallback
    ) {
        Helper.printLogs( "URL: " + callRes.request().url())
        Helper.printLogs( "Params: $params")
        callRes.enqueue(object : Callback<String?> {
            override fun onResponse(
                call: Call<String?>,
                response: Response<String?>
            ) {
                val resp = response.body()
                Helper.printLogs( "Response: $resp")
                callback.onResponce("${resp}", true)
            }

            override fun onFailure(call: Call<String?>, t: Throwable) {
                Helper.printLogs( "Failure: $t")
                callback.onResponce("" + t, false)
            }
        })
    }
}