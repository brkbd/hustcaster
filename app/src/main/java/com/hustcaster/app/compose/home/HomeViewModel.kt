package com.hustcaster.app.compose.home

import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hustcaster.app.data.model.EpisodeAndRecord
import com.hustcaster.app.data.model.Podcast
import com.hustcaster.app.data.model.Record
import com.hustcaster.app.data.repository.PodcastRepository
import com.hustcaster.app.data.repository.RecordRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val recordRepository: RecordRepository,
    private val podcastRepository: PodcastRepository
) : ViewModel() {
    private val _records = MutableStateFlow<List<EpisodeAndRecord>>(emptyList())
    val records = _records.asStateFlow()

    private val _podcasts = MutableStateFlow<List<Podcast>>(emptyList())
    val podcasts = _podcasts.asStateFlow()

    private val _imageUrls = MutableStateFlow<List<String>>(emptyList())
    val imageUrls = _imageUrls.asStateFlow()

    init {
        refreshData()
    }

    private fun refreshData() {
        viewModelScope.launch {
            _records.value = recordRepository.getThreeLatestRecords()
            _podcasts.value = podcastRepository.getAllPodcasts()
            _imageUrls.value = recordRepository.getImageUrlsOfRecords(_records.value)
        }
    }
}