package com.hustcaster.app.workers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.hustcaster.app.data.AppDatabase
import com.hustcaster.app.data.FeedItemRepository
import com.hustcaster.app.data.FeedRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UpdatesWorker(context: Context, workerParameters: WorkerParameters) :
    CoroutineWorker(context, workerParameters) {
    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        try {
            val appDatabase = AppDatabase.getDatabase(applicationContext)
            val feedRepository = FeedRepository.getInstance(appDatabase.feedDao())


        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure()
        }
        Result.success()
    }
}