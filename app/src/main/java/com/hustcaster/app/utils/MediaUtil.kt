package com.hustcaster.app.utils

import android.net.Uri
import android.provider.MediaStore.Audio.Media
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.exoplayer.source.MediaSource
import com.hustcaster.app.data.model.Episode
import com.hustcaster.app.utils.MediaUtil.toMediaSource

object MediaUtil {

    fun MediaItem.toMediaSource():MediaSource{
        val dataSourceFactory
    }

    fun Episode.toMediaItem(): MediaItem {
        val mediaMetaData = MediaMetadata.Builder()
            .setTitle(title)
            .setArtist(author)
            .setArtworkUri(Uri.parse(imageUrl))
            .build()
        return MediaItem.Builder()
            .setMediaId(episodeId.toString())
            .setMediaMetadata(mediaMetaData)
            .setUri(audioUrl)
            .build()
    }

    fun Episode.toMediaSource(): MediaSource {
        return toMediaItem()
    }
}