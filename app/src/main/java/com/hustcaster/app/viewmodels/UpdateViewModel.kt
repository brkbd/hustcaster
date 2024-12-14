package com.hustcaster.app.viewmodels

import androidx.annotation.OptIn
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.util.UnstableApi
import com.hustcaster.app.App
import com.hustcaster.app.data.model.Episode
import com.hustcaster.app.data.model.Record
import com.hustcaster.app.data.repository.EpisodeRepository
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
class UpdateViewModel @Inject constructor(
    private val episodeRepository: EpisodeRepository,
    private val recordRepository: RecordRepository
) : ViewModel() {
    private val exoPlayer = ExoPlayerHolder.get(App.context)
    val updateEpisodes: StateFlow<List<Episode>> =
        episodeRepository.getUpdateEpisode().stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )

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

    suspend fun onDeleteClick(){
        viewModelScope.launch(Dispatchers.IO) {
            updateEpisodes.value.forEach {
                it.isUpdated=false
            }
            updateEpisodes.value.forEach{
                episodeRepository.updateEpisode(it)
            }
        }
    }
}