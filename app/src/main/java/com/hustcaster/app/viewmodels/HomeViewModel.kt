package com.hustcaster.app.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hustcaster.app.App
import com.hustcaster.app.data.model.EpisodeAndRecord
import com.hustcaster.app.data.model.Podcast
import com.hustcaster.app.data.model.PodcastAndEpisodes
import com.hustcaster.app.data.repository.PodcastRepository
import com.hustcaster.app.data.repository.RecordRepository
import com.hustcaster.app.player.ExoPlayerHolder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val recordRepository: RecordRepository,
    private val podcastRepository: PodcastRepository
) : ViewModel() {
    val records: StateFlow<List<EpisodeAndRecord>> =
        recordRepository.getThreeLatestRecords()
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5000),
                emptyList()
            )

    val podcasts: StateFlow<List<PodcastAndEpisodes>> =
        podcastRepository.getAllPodcasts()
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5000),
                emptyList()
            )
    private val exoPlayer = ExoPlayerHolder.get(App.context)
    fun playOrPause() {
        if (exoPlayer.isPlaying) {
            exoPlayer.pause()
        } else {
            exoPlayer.prepare()
            exoPlayer.play()
        }
    }
}