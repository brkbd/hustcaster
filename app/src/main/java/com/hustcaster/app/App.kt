package com.hustcaster.app

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.content.Intent
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.hustcaster.app.player.PlayerUtilManager
import com.hustcaster.app.player.PodcastService
import com.hustcaster.app.player.PodcastServiceConnection
import com.hustcaster.app.utils.SafeCoroutineScope
import com.hustcaster.app.workers.UPDATE_WORK_TAG
import com.hustcaster.app.workers.UpdateWorker
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltAndroidApp
class App : Application() {
    @Inject lateinit var playerUtilManager: PlayerUtilManager
    private val podcastServiceConnection = PodcastServiceConnection()
    private lateinit var mWorkManager: WorkManager
    override fun onCreate() {
        super.onCreate()
        applicationScope = SafeCoroutineScope(SupervisorJob())
        context = this.applicationContext
        mWorkManager=WorkManager.getInstance(this)
        startUpdatesWork(this)
    }

    private fun startPodcastService() {
        val intent = Intent(this, PodcastService::class.java)
        startService(intent)
        bindService(intent, podcastServiceConnection, BIND_AUTO_CREATE)
    }

    private fun startUpdatesWork(context: Context) {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

//        val request = PeriodicWorkRequestBuilder<UpdateWorker>(30, TimeUnit.SECONDS)
//            .setConstraints(constraints)
//            .build()

        val request= OneTimeWorkRequestBuilder<UpdateWorker>().build()
//        mWorkManager.enqueueUniquePeriodicWork(UPDATE_WORK_TAG, ExistingPeriodicWorkPolicy.KEEP, request)
        mWorkManager.enqueue(request)
    }

    companion object {
        lateinit var applicationScope: CoroutineScope

        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
    }
}