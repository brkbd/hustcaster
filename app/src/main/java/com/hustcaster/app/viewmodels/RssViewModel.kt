package com.hustcaster.app.viewmodels

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hustcaster.app.compose.rss.RssScreenEvent
import com.hustcaster.app.network.parser.MainParser
import com.hustcaster.app.data.repository.EpisodeRepository
import com.hustcaster.app.data.repository.PodcastRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class RssViewModel @Inject constructor(
    private val podcastRepository: PodcastRepository,
    private val episodeRepository: EpisodeRepository
) : ViewModel() {
    private val _rssUrlInput = MutableStateFlow("")
    val rssUrlInput = _rssUrlInput.asStateFlow()

    private val _sharedFlow= MutableSharedFlow<RssScreenEvent>()
    val sharedFlow=_sharedFlow

    private val _isImporting=MutableStateFlow<Boolean>(false)
    val isImporting=_isImporting.asStateFlow()

    fun onValueChange(newString: String) {
        _rssUrlInput.value = newString
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun onImportClick(){
        viewModelScope.launch {
            if (isPodcastExists()){
                _sharedFlow.emit(RssScreenEvent.PODCAST_ALREADY_EXISTS)
            }else{
                _sharedFlow.emit(RssScreenEvent.IS_GETTING_PODCAST)
                _isImporting.value=true
                importPodcast()
                _isImporting.value=false
                _sharedFlow.emit(RssScreenEvent.FINISH_GETTING_PODCAST)
            }
        }
    }

    private suspend fun isPodcastExists(): Boolean {
        return withContext(Dispatchers.IO) {
            podcastRepository.getPodcastIdByRssUrl(_rssUrlInput.value)!= 0L
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun importPodcast() {
        viewModelScope.launch {
            MainParser.parse(_rssUrlInput.value,episodeRepository,podcastRepository)
        }
    }
}