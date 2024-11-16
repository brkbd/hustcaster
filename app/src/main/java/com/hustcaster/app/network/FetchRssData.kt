package com.hustcaster.app.network

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request

val client = OkHttpClient()

suspend fun fetchRssData(url: String): String? {
    return withContext(Dispatchers.IO){
        val request = Request.Builder()
            .url(url)
            .build()

        val response = client.newCall(request).execute()

        return@withContext response.body?.string()
    }

}