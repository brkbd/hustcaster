package com.hustcaster.app.network

import okhttp3.OkHttpClient
import okhttp3.Request

val client = OkHttpClient()

fun fetchRssData(url: String): String? {
    val request = Request.Builder()
        .url(url)
        .build()

    val response = client.newCall(request).execute()

    return response.body?.string()
}