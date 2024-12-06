package com.hustcaster.app.utils

import android.net.Uri
import androidx.annotation.OptIn
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.source.MediaSource
import androidx.media3.exoplayer.source.ProgressiveMediaSource
import com.hustcaster.app.App.Companion.context
import com.hustcaster.app.data.model.Episode
import com.hustcaster.app.data.repository.EpisodeRepository
import com.hustcaster.app.player.cache.DataSourceHolder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object MediaUtil {
    private lateinit var episodeRepository: EpisodeRepository

    @OptIn(UnstableApi::class)
    fun MediaItem.toMediaSource(): MediaSource {
        val dataSourceFactory = DataSourceHolder.getCacheFactory(context)
        return ProgressiveMediaSource.Factory(dataSourceFactory).createMediaSource(this)
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
        return toMediaItem().toMediaSource()
    }

    fun setEpisodeRepository(episodeRepository: EpisodeRepository) {
        this.episodeRepository = episodeRepository
    }


    @OptIn(UnstableApi::class)
    suspend fun getMediaSourceByEpisodeId(episodeId: Long): MediaSource =
        withContext(Dispatchers.IO) {
            val episode = episodeRepository.getEpisodeById(episodeId)
            val mediaMetadata = with(episode) {
                MediaMetadata.Builder().setTitle(title).setArtist(author)
                    .setArtworkUri(Uri.parse(imageUrl)).build()
            }
            val mediaItem =
                MediaItem.Builder().setMediaId(episodeId.toString()).setMediaMetadata(mediaMetadata)
                    .setUri(episode.audioUrl).build()
            val dataSourceFactory = DataSourceHolder.getCacheFactory(context)
            return@withContext ProgressiveMediaSource.Factory(dataSourceFactory)
                .createMediaSource(mediaItem)
        }
}