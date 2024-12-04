package com.hustcaster.app.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hustcaster.app.compose.common.NavigationGraph.PODCAST_ID
import com.hustcaster.app.data.model.PodcastAndEpisodes
import com.hustcaster.app.data.repository.PodcastRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class PodcastViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val podcastRepository: PodcastRepository
) : ViewModel() {
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
            _podcastAndEpisodes.postValue(result)
        }
    }

}