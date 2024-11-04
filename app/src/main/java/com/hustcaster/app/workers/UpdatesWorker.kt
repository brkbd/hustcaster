package com.hustcaster.app.workers

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.work.CoroutineWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.hustcaster.app.data.AppDatabase
import com.hustcaster.app.data.FeedItemRepository
import com.hustcaster.app.data.FeedRepository
import com.hustcaster.app.data.MainParser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class UpdatesWorker(context: Context, workerParameters: WorkerParameters) :
    CoroutineWorker(context, workerParameters) {
    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        try {
            val appDatabase = AppDatabase.getDatabase(applicationContext)
            val feedRepository = FeedRepository.getInstance(appDatabase.feedDao())
            val feedItemRepository = FeedItemRepository.getInstance(appDatabase.feedItemDao())
            val feedAndFeedItemsList = feedItemRepository.getFeedAndFeedItems()
            //get updateRepository
            runBlocking {
                launch {
                    feedAndFeedItemsList.collect{items->
                        items.forEach{item->
                            MainParser.checkUpdates(item,feedItemRepository,feedRepository)
                        }
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure()
        }
        Result.success()
    }
}