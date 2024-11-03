package com.hustcaster.app.network

import com.hustcaster.app.data.MainParser
import okhttp3.Call
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

val client = OkHttpClient()

fun fetchRssData(url: String): String? {
    val request = Request.Builder()
        .url(url)
        .build()

    val response = client.newCall(request).execute()

    return response.body?.string()
}