package com.hustcaster.app.utils

import android.content.Context
import android.net.Uri
import androidx.annotation.OptIn
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.common.util.UriUtil
import androidx.media3.database.StandaloneDatabaseProvider
import androidx.media3.datasource.DefaultHttpDataSource
import androidx.media3.datasource.cache.NoOpCacheEvictor
import androidx.media3.datasource.cache.SimpleCache
import androidx.media3.exoplayer.offline.DefaultDownloaderFactory
import androidx.media3.exoplayer.offline.DownloadManager
import androidx.media3.exoplayer.offline.DownloadRequest
import androidx.media3.exoplayer.offline.Downloader
import com.hustcaster.app.data.FeedItem
import com.hustcaster.app.data.FeedItemRepository
import java.io.File
import java.net.URI
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
            val downloadDirectory = File(context.getExternalFilesDir(null), "exoplayer_downloads")
            val databaseProvider = StandaloneDatabaseProvider(context)
            val cache = SimpleCache(downloadDirectory, NoOpCacheEvictor(), databaseProvider)
            return DownloadManager(
                context,
                databaseProvider,
                cache,
                DefaultHttpDataSource.Factory(),
                Executors.newFixedThreadPool(6)
            ).apply { downloadManager = this }
        }
    }


    @OptIn(UnstableApi::class)
    fun createDownloadRequest(feedItem: FeedItem): DownloadRequest {
        return DownloadRequest.Builder(feedItem.audioUrl, Uri.parse(feedItem.audioUrl)).build()
    }

    suspend fun downloadEpisode(downloadManager: DownloadManager, feedItem: FeedItem) {
        val downloadRequest = createDownloadRequest(feedItem)
        downloadManager.addDownload(downloadRequest)
    }

    suspend fun updateFeedItem(
        downloadManager: DownloadManager,
        feedItem: FeedItem,
        repository: FeedItemRepository
    ) {
        feedItem.isDownloaded = true
//        feedItem.downloadUrl = getDownloadFilePath(downloadManager, feedItem)
        repository.updateFeedItem(feedItem)
    }

//    private fun getDownloadFilePath(downloadManager: DownloadManager, feedItem: FeedItem): String {
//        val downloads = downloadManager.currentDownloads
//        for (episode in downloads) {
//            if (episode.request.uri.toString() == feedItem.audioUrl) {
//                return episode.
//            }
//        }
//    }
}

