package com.hustcaster.app.workers

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.hustcaster.app.data.AppDatabase
import com.hustcaster.app.data.repository.EpisodeRepository
import com.hustcaster.app.data.repository.PodcastRepository
import com.hustcaster.app.data.repository.UpdateRepository
import com.hustcaster.app.network.parser.MainParser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

const val UPDATE_WORK_TAG = "update_work"

class UpdateWorker(context: Context, workerParameters: WorkerParameters) :
    CoroutineWorker(context, workerParameters) {
    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        try {
            val appDatabase = AppDatabase.getDatabase(applicationContext)
            val podcastRepository = PodcastRepository.getInstance(appDatabase.podcastDao())
            val episodeRepository = EpisodeRepository.getInstance(appDatabase.episodeDao())
            val updateRepository=UpdateRepository.getInstance(appDatabase.updateDao())
            val podcastList = podcastRepository.getAllPodcasts()

            podcastList.forEach {  podcast ->
                MainParser.checkUpdates(podcast, episodeRepository, podcastRepository,updateRepository)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure()
        }
        Result.success()
    }
}

