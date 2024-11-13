package com.hustcaster.app.compose.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hustcaster.app.data.model.EpisodeAndRecord
import com.hustcaster.app.data.model.Podcast
import com.hustcaster.app.data.repository.PodcastRepository
import com.hustcaster.app.data.repository.RecordRepository
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

    val podcasts: StateFlow<List<Podcast>> =
        podcastRepository.getAllPodcasts()
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5000),
                emptyList()
            )

    val imageUrls: StateFlow<List<String>> =
        recordRepository.getImageUrlsOfRecords(records.value)
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5000),
                emptyList()
            )

}