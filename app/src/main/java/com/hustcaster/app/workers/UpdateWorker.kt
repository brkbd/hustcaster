package com.hustcaster.app.workers

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.work.Constraints
import androidx.work.CoroutineWorker
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import com.hustcaster.app.data.AppDatabase
import com.hustcaster.app.data.repository.EpisodeRepository
import com.hustcaster.app.data.repository.PodcastRepository
import com.hustcaster.app.data.parser.MainParser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.util.concurrent.TimeUnit

const val UPDATE_WORK_TAG = "update_work"

class UpdateWorker(context: Context, workerParameters: WorkerParameters) :
    CoroutineWorker(context, workerParameters) {
    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        try {
            val appDatabase = AppDatabase.getDatabase(applicationContext)
            val podcastRepository = PodcastRepository.getInstance(appDatabase.podcastDao())
            val episodeRepository = EpisodeRepository.getInstance(appDatabase.episodeDao())
            val feedAndFeedItemsList = episodeRepository.getPodcastAndEpisodes()
            runBlocking {
                launch {
                    feedAndFeedItemsList.collect { items ->
                        items.forEach { item ->
                            MainParser.checkUpdates(item, episodeRepository, podcastRepository)
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

fun startUpdatesWork(context: Context) {
    val constraints = Constraints.Builder()
        .setRequiredNetworkType(NetworkType.CONNECTED)
        .build()

    val request = PeriodicWorkRequestBuilder<UpdateWorker>(1, TimeUnit.DAYS)
        .setConstraints(constraints)
        .build()


    WorkManager.getInstance(context)
        .enqueueUniquePeriodicWork(UPDATE_WORK_TAG, ExistingPeriodicWorkPolicy.KEEP, request)
}