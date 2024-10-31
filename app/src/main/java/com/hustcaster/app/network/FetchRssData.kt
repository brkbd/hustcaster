package com.hustcaster.app.network

import com.hustcaster.app.data.MainParser
import okhttp3.Call
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

val client = OkHttpClient()

fun fetchRssData(url: String) {
    val request = Request.Builder()
        .url(url)
        .build()

    client.newCall(request).enqueue(object : okhttp3.Callback {
        override fun onFailure(call: Call, e: IOException) {
            e.printStackTrace()
        }

        override fun onResponse(call: Call, response: Response) {
            if (response.isSuccessful) {
                response.body?.string()?.let { xmlData ->
                    MainParser.parse(xmlData)
                }
            }
        }
    })
}