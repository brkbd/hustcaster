package com.hustcaster.app.compose.record

import android.view.View
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hustcaster.app.data.model.EpisodeAndRecord
import com.hustcaster.app.data.repository.RecordRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecordListViewModel @Inject constructor(
    private val recordRepository: RecordRepository
) : ViewModel() {
    val records: StateFlow<List<EpisodeAndRecord>> =
        recordRepository.getEpisodeAndRecordListFlow().stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )


    fun getRecordImageUrl(id: Long) = recordRepository.getImageUrlOfRecord(id)
}