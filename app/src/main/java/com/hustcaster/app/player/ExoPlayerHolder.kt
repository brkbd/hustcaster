package com.hustcaster.app.player

import android.content.Context
import androidx.annotation.OptIn
import androidx.media3.common.AudioAttributes
import androidx.media3.common.C
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import com.hustcaster.app.App.Companion.context
import com.hustcaster.app.data.model.Episode
import com.hustcaster.app.data.model.Podcast
import com.hustcaster.app.data.repository.EpisodeRepository
import com.hustcaster.app.data.repository.PodcastRepository
import com.hustcaster.app.data.repository.RecordRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import javax.inject.Singleton

private const val TAG = "ExoPlayerHolder"
private const val FORWARD_INCREMENT_MS = 30000L
private const val BACKWARD_INCREMENT_MS = 30000L

@Singleton
class ExoPlayerHolder @Inject constructor(
    private val recordRepository: RecordRepository,
    private val podcastRepository: PodcastRepository,
    private val episodeRepository: EpisodeRepository
) {

    private val exoPlayer: ExoPlayer by lazy { createExoPlayer(context) }


    data class PlayerState(
        val currentMediaItem: MediaItem = MediaItem.EMPTY,
        val currentEpisode: Episode = Episode(),
        val currentPodcast: Podcast = Podcast(),
        val currentProgress: Float = 0F,
        val isPlayerAvailable: Boolean = false,
        val isPlaying: Boolean = false,
        val isBuffering: Boolean = false
    )

    private val mutableStateFlow = MutableStateFlow(PlayerState())
    val playerStateFlow = mutableStateFlow.asStateFlow()

    @Synchronized
    fun get(context: Context): ExoPlayer {
        if (!mutableStateFlow.value.isPlayerAvailable) {

        }
    }

    private suspend fun loadEpisodeFromDatabase() {
        recordRepository.getLatestRecord().filterNotNull()
            .distinctUntilChangedBy { it.episode.episodeId }
            .collect {
                with(it) {
                    val podcast = podcastRepository.getPodcastById(it.episode.podcastId)
                    val mediaSource=episode.
                }
            }
    }

    @OptIn(UnstableApi::class)
    @Synchronized
    private fun createExoPlayer(context: Context): ExoPlayer {
        return ExoPlayer.Builder(context)
            .setAudioAttributes(AudioAttributes.DEFAULT, true)
            .setHandleAudioBecomingNoisy(true)
            .setWakeMode(C.WAKE_MODE_LOCAL)
            .setSeekForwardIncrementMs(FORWARD_INCREMENT_MS)
            .setSeekBackIncrementMs(BACKWARD_INCREMENT_MS)
            .build().apply {
                repeatMode = Player.REPEAT_MODE_ONE
                addListener(PlayerListener(mutableStateFlow, playerStateFlow))
            }
    }


    private class PlayerListener(
        private val mutableStateFlow: MutableStateFlow<PlayerState>,
        private val playerStateFlow: StateFlow<PlayerState>
    ) : Player.Listener {
        override fun onMediaItemTransition(mediaItem: MediaItem?, reason: Int) {
            mutableStateFlow.update { it.copy(currentMediaItem = mediaItem ?: MediaItem.EMPTY) }
        }

        override fun onIsPlayingChanged(isPlaying: Boolean) {
            super.onIsPlayingChanged(isPlaying)
            if (!playerStateFlow.value.isBuffering)
                mutableStateFlow.update {
                    it.copy(isPlaying = isPlaying)
                }
        }

        override fun onPlaybackStateChanged(playbackState: Int) {
            super.onPlaybackStateChanged(playbackState)
            mutableStateFlow.update {
                it.copy(
                    isBuffering = playbackState == ExoPlayer.STATE_BUFFERING
                )
            }
        }
    }
}