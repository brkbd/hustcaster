package com.hustcaster.app.utils

import android.content.Context
import androidx.annotation.OptIn
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.offline.Download
import androidx.media3.exoplayer.offline.DownloadManager
import com.hustcaster.app.data.FeedItem

@OptIn(UnstableApi::class)
class Player(
    private val downloadManager: DownloadManager
) {

    fun getMediaItem(feedItem: FeedItem): MediaItem {
        if (!feedItem.isDownloaded) {
            return MediaItem.fromUri(feedItem.audioUrl)
        }
        val downloadList = downloadManager.currentDownloads
        for (download in downloadList) {
            if (download.request.uri.toString() == feedItem.audioUrl
                && download.state == Download.STATE_COMPLETED
            ) {
                return download.request.toMediaItem()
            }
        }
        return MediaItem.fromUri(feedItem.audioUrl)
    }

}