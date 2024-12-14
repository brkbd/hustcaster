package com.hustcaster.app.viewmodels

import androidx.annotation.OptIn
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.util.UnstableApi
import com.hustcaster.app.App
import com.hustcaster.app.compose.common.NavigationGraph.PODCAST_ID
import com.hustcaster.app.data.model.Episode
import com.hustcaster.app.data.model.PodcastAndEpisodes
import com.hustcaster.app.data.model.Record
import com.hustcaster.app.data.repository.PodcastRepository
import com.hustcaster.app.data.repository.RecordRepository
import com.hustcaster.app.player.ExoPlayerHolder
import com.hustcaster.app.utils.MediaUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class PodcastViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val podcastRepository: PodcastRepository,
    private val recordRepository: RecordRepository
) : ViewModel() {
    private val exoPlayer = ExoPlayerHolder.get(App.context)
    private val podcastId: Long = savedStateHandle[PODCAST_ID] ?: 0
    private val _podcastAndEpisodes = MutableLiveData<PodcastAndEpisodes?>()
    val podcastAndEpisodes: LiveData<PodcastAndEpisodes?> get() = _podcastAndEpisodes

    init {
        getPodcastData(podcastId)
    }


    private fun getPodcastData(podcastId: Long) {
        viewModelScope.launch {
            val result = withContext(Dispatchers.IO) {
                podcastRepository.getPodcastAndEpisodesById(podcastId)
            }
            result.sortedEpisodesByDate()
            _podcastAndEpisodes.postValue(result)
        }
    }

    fun playOrPause() {
        if (exoPlayer.isPlaying) {
            exoPlayer.pause()
        } else {
            exoPlayer.prepare()
            exoPlayer.play()
        }
    }

    @OptIn(UnstableApi::class)
    suspend fun onEpisodeClick(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            val record = recordRepository.getRecordByEpisodeId(id)
            record.forEach {
                recordRepository.deleteRecord(it)
            }
            recordRepository.insertRecord(Record(episodeId = id))
        }
        viewModelScope.launch(Dispatchers.IO) {
            val mediaSource = MediaUtil.getMediaSourceByEpisodeId(id)
            withContext(Dispatchers.Main) {
                exoPlayer.stop()
                exoPlayer.setMediaSource(mediaSource)
                exoPlayer.prepare()
                exoPlayer.play()
            }
        }
    }
}