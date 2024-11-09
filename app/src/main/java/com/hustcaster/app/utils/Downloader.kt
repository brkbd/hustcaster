package com.hustcaster.app.utils

import android.content.Context
import android.net.Uri
import androidx.annotation.OptIn
import androidx.media3.common.util.UnstableApi
import androidx.media3.database.StandaloneDatabaseProvider
import androidx.media3.datasource.DefaultHttpDataSource
import androidx.media3.datasource.cache.NoOpCacheEvictor
import androidx.media3.datasource.cache.SimpleCache
import androidx.media3.exoplayer.offline.DownloadManager
import androidx.media3.exoplayer.offline.DownloadRequest
import com.hustcaster.app.data.Episode
import com.hustcaster.app.data.EpisodeRepository
import java.io.File
import java.util.concurrent.Executors
import javax.inject.Singleton

@UnstableApi
@Singleton
class Downloader {
    companion object {
        @Volatile
        private var downloadManager: DownloadManager? = null

        @Synchronized
        @OptIn(UnstableApi::class)
        fun getDownloadManager(context: Context): DownloadManager {
            if (downloadManager != null) {
                return downloadManager as DownloadManager
            }
            val downloadDirectory = File(context.getExternalFilesDir(null), "exoplayer_downloads")
            val databaseProvider = StandaloneDatabaseProvider(context)
            val cache = SimpleCache(downloadDirectory, NoOpCacheEvictor(), databaseProvider)
            downloadManager = DownloadManager(
                context,
                databaseProvider,
                cache,
                DefaultHttpDataSource.Factory(),
                Executors.newFixedThreadPool(6)
            )
            downloadManager!!.addListener(
                object:DownloadManager.Listener{
                    override fun onDownloadsPausedChanged(
                        downloadManager: DownloadManager,
                        downloadsPaused: Boolean
                    ) {
                        super.onDownloadsPausedChanged(downloadManager, downloadsPaused)
                    }
                }
            )
            return downloadManager as DownloadManager
        }
    }


    @OptIn(UnstableApi::class)
    fun createDownloadRequest(episode: Episode): DownloadRequest {
        return DownloadRequest.Builder(episode.audioUrl, Uri.parse(episode.audioUrl)).build()
    }

    suspend fun downloadFeedItem(downloadManager: DownloadManager, episode: Episode) {
        val downloadRequest = createDownloadRequest(episode)
        downloadManager.addDownload(downloadRequest)
    }

    suspend fun updateFeedItem(
        episode: Episode,
        repository: EpisodeRepository
    ) {
        episode.isDownloaded = true
        repository.updateEpisode(episode)
    }


}

