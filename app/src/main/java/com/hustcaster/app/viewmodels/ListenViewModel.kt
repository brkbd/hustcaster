package com.hustcaster.app.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hustcaster.app.App
import com.hustcaster.app.player.ExoPlayerHolder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListenViewModel @Inject constructor() : ViewModel() {
    private val exoPlayer = ExoPlayerHolder.get(App.context)
    private val mutableStateFlow = MutableStateFlow(ViewState())
    val stateFlow = mutableStateFlow.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            ExoPlayerHolder.playerStateFlow.collect { playerState ->
                with(playerState) {
                    mutableStateFlow.update {
                        it.copy(
                            episodeId = currentEpisode.episodeId,
                            episodeTitle = currentEpisode.title,
                            podcastId = currentPodcast.id,
                            podcastTitle = currentPodcast.title,
                            imageUrl = currentEpisode.imageUrl,
                            duration = currentEpisode.duration,
                            progress = currentProgress,
                            isPlaying = isPlaying,
                            isBuffering = isBuffering
                        )
                    }
                }
            }
        }
    }

    data class ViewState(
        val episodeId: Long = 0,
        val episodeTitle: String = "",
        val podcastTitle: String = "",
        val podcastId: Long = 0,
        val imageUrl: String = "",
        val duration: Long = 0,
        val progress: Float = 0F,
        val isPlaying: Boolean = false,
        val isBuffering: Boolean = false
    )

    fun forward() = exoPlayer.seekForward()

    fun replay() = exoPlayer.seekBack()

    fun setPlayBackSpeed(speed: Float) = exoPlayer.setPlaybackSpeed(speed)

    fun seekToProgress(progress: Float) = exoPlayer.seekTo((exoPlayer.duration * progress).toLong())

    fun playOrPause() {
        if (exoPlayer.isPlaying) {
            exoPlayer.pause()
        } else {
            exoPlayer.prepare()
            exoPlayer.play()
        }
    }

}