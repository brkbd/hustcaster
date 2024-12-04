package com.hustcaster.app.network

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import okio.IOException

val client = OkHttpClient()

suspend fun fetchRssData(url: String): String? {
    return try {
        withContext(Dispatchers.IO){
            val request = Request.Builder()
                .url(url)
                .build()

            val response = client.newCall(request).execute()

            if(response.isSuccessful){
                return@withContext response.body?.string()
            }else{
                null
            }
        }
    }catch (e:IOException){
        null
    }

}