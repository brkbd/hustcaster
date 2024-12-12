package com.hustcaster.app.viewmodels

import androidx.annotation.OptIn
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.util.UnstableApi
import com.hustcaster.app.App
import com.hustcaster.app.data.model.EpisodeAndRecord
import com.hustcaster.app.data.model.Record
import com.hustcaster.app.data.repository.RecordRepository
import com.hustcaster.app.player.ExoPlayerHolder
import com.hustcaster.app.utils.MediaUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class RecordListViewModel @Inject constructor(
    private val recordRepository: RecordRepository
) : ViewModel() {
    private val exoPlayer = ExoPlayerHolder.get(App.context)
    val records: StateFlow<List<EpisodeAndRecord>> =
        recordRepository.getEpisodeAndRecordListFlow().stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )

    @OptIn(UnstableApi::class)
    suspend fun onRecordClick(episodeId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            val record = recordRepository.getRecordByEpisodeId(episodeId)
            record.forEach {
                recordRepository.deleteRecord(it)
            }
            recordRepository.insertRecord(Record(episodeId = episodeId))
        }
        viewModelScope.launch(Dispatchers.IO) {
            val mediaSource = MediaUtil.getMediaSourceByEpisodeId(episodeId)
            withContext(Dispatchers.Main) {
                exoPlayer.stop()
                exoPlayer.setMediaSource(mediaSource)
                exoPlayer.prepare()
                exoPlayer.play()
            }
        }
    }

}