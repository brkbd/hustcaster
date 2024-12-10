package com.hustcaster.app

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.content.Intent
import com.hustcaster.app.player.PlayerUtilManager
import com.hustcaster.app.player.PodcastService
import com.hustcaster.app.player.PodcastServiceConnection
import com.hustcaster.app.utils.SafeCoroutineScope
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import javax.inject.Inject

@HiltAndroidApp
class App : Application() {
    @Inject lateinit var playerUtilManager: PlayerUtilManager
    private val podcastServiceConnection = PodcastServiceConnection()
    override fun onCreate() {
        super.onCreate()
        applicationScope = SafeCoroutineScope(SupervisorJob())
        context = this.applicationContext

    }

    private fun startPodcastService() {
        val intent = Intent(this, PodcastService::class.java)
        startService(intent)
        bindService(intent, podcastServiceConnection, BIND_AUTO_CREATE)
    }

    companion object {
        lateinit var applicationScope: CoroutineScope

        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
    }
}