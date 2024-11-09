package com.hustcaster.app.utils

import androidx.annotation.OptIn
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.offline.Download
import androidx.media3.exoplayer.offline.DownloadManager
import com.hustcaster.app.data.Episode

@OptIn(UnstableApi::class)
class Player(
    private val downloadManager: DownloadManager
) {

    fun getMediaItem(episode: Episode): MediaItem {
        if (!episode.isDownloaded) {
            return MediaItem.fromUri(episode.audioUrl)
        }
        val downloadList = downloadManager.currentDownloads
        for (download in downloadList) {
            if (download.request.uri.toString() == episode.audioUrl
                && download.state == Download.STATE_COMPLETED
            ) {
                return download.request.toMediaItem()
            }
        }
        return MediaItem.fromUri(episode.audioUrl)
    }

}